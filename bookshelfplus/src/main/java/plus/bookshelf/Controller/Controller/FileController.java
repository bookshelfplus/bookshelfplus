package plus.bookshelf.Controller.Controller;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.qcloud.cos.http.HttpMethodName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import plus.bookshelf.Common.Enum.FileStorageMediumEnum;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.FileManager.QCloudCosUtils;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Config.QCloudCosConfig;
import plus.bookshelf.Controller.VO.FileObjectVO;
import plus.bookshelf.Controller.VO.FileVO;
import plus.bookshelf.Service.Impl.FileObjectServiceImpl;
import plus.bookshelf.Service.Impl.FileServiceImpl;
import plus.bookshelf.Service.Impl.UserServiceImpl;
import plus.bookshelf.Service.Model.FileModel;
import plus.bookshelf.Service.Model.FileObjectModel;
import plus.bookshelf.Service.Model.UserModel;
import plus.bookshelf.Service.Service.CosPresignedUrlGenerateLogService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "文件管理")
@Controller("file")
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    QCloudCosConfig qCloudCosConfig;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    CosPresignedUrlGenerateLogService cosPresignedUrlGenerateLogService;

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    FileObjectServiceImpl fileObjectService;

    // @Autowired
    // ScheduleTaskServiceImpl scheduleTaskService;

    @ApiOperation(value = "查询文件列表", notes = "查询文件列表")
    @RequestMapping(value = "list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType list(@RequestParam(value = "token", required = false) String token) throws InvocationTargetException, IllegalAccessException, BusinessException {
        List<FileModel> fileModels = fileService.list(token);
        List<FileVO> fileVOS = new ArrayList<>();
        for (FileModel fileModel : fileModels) {
            FileVO fileVO = convertFileVOFromModel(fileModel);
            fileVOS.add(fileVO);
        }
        return CommonReturnType.create(fileVOS);
    }

    private FileVO convertFileVOFromModel(FileModel fileModel) {
        FileVO fileVO = new FileVO();
        BeanUtils.copyProperties(fileModel, fileVO);
        fileVO.setFileCreateAt(fileModel.getFileCreateAt().getTime());
        fileVO.setFileModifiedAt(fileModel.getFileModifiedAt().getTime());
        return fileVO;
    }

    @ApiOperation(value = "查询文件对象列表", notes = "查询文件列表")
    @RequestMapping(value = "object/list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType objectList(@RequestParam(value = "token", required = false) String token) throws InvocationTargetException, IllegalAccessException, BusinessException {
        List<FileObjectModel> fileObjectModels = fileObjectService.list(token);
        List<FileObjectVO> fileObjectVOS = new ArrayList<>();
        for (FileObjectModel fileObjectModel : fileObjectModels) {
            FileObjectVO fileObjectVO = convertFileObjectVOFromModel(fileObjectModel);
            fileObjectVOS.add(fileObjectVO);
        }
        return CommonReturnType.create(fileObjectVOS);
    }

    private FileObjectVO convertFileObjectVOFromModel(FileObjectModel fileObjectModel) {
        FileObjectVO fileObjectVO = new FileObjectVO();
        BeanUtils.copyProperties(fileObjectModel, fileObjectVO);
        try {
            // 尝试将 FileStorageMedium 转为中文，如果没有成功，那么就保留英文
            fileObjectVO.setStorageMediumType(FileStorageMediumEnum.valueOf(fileObjectModel.getStorageMediumType()).getStorageMediumDisplayName());
        } catch (Exception e) {
        }
        return fileObjectVO;
    }

    /**
     * 创建文件操作预授权URL
     *
     * @param httpMethod   请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
     * @param token        当前登录用户的 token
     * @param fileName     文件名
     * @param expireMinute 过期时间（分钟）
     * @return
     * @throws BusinessException
     */
    @ApiOperation(value = "创建腾讯云 COS 预授权 URL", notes = "")
    @RequestMapping(value = "/cos/{httpMethod}", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType cos(@PathVariable(value = "httpMethod") String httpMethod,
                                @RequestParam(value = "token") String token,
                                @RequestParam(value = "fileName") String fileName,
                                @RequestParam(value = "expireMinute", required = false) Integer expireMinute) throws BusinessException {
        if (expireMinute == null) {
            expireMinute = 30;
        } else if (expireMinute > 60) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "expireMinute 参数不能大于 60");
        } else if (expireMinute < 1) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "expireMinute 参数不能小于 1");
        }

        // 已经在 getUserByToken 方法中判断了 token 为空、不合法；用户不存在情况，此处无需再判断
        UserModel userModel = userService.getUserByToken(redisTemplate, token);

        // 判断httpMethod 是否合法
        HttpMethodName httpMethodName;
        try {
            httpMethodName = HttpMethodName.valueOf(httpMethod.toUpperCase());
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "httpMethod 参数不合法");
        }

        QCloudCosUtils qCloudCosUtils = new QCloudCosUtils(qCloudCosConfig, cosPresignedUrlGenerateLogService);

        // 当次生成下载链接的全局唯一Id
        String urlGUID = NanoIdUtils.randomNanoId();
        String bookSaveFolder = QCloudCosUtils.BOOK_SAVE_FOLDER;

        // 判断对象是否存在
        Boolean isExist = qCloudCosUtils.doesObjectExist(bookSaveFolder, fileName);
        switch (httpMethodName) {
            case PUT:
                if (isExist) throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "文件已存在");
                // 添加一个scheduleTask，用于检测用户是否上传了文件，然后更新数据库中信息
                // TODO
                // fileService.addScheduleTask(expireMinute, bookSaveFolder, urlGUID, userModel.getId());
                break;
            case GET:
                if (!isExist) throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "文件不存在");
                break;
            case DELETE:
                if (!isExist) throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "文件不存在");
                break;
            default:
                throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "httpMethod 参数暂不支持");
        }

        String url = qCloudCosUtils.generatePresignedUrl(userModel.getId(), httpMethodName, bookSaveFolder, fileName, 30, urlGUID);

        Map map = new HashMap();
        map.put("url", url);
        map.put("urlGUID", urlGUID);
        return CommonReturnType.create(map);
    }

    @ApiOperation(value = "", notes = "客户端向腾讯云 COS 存储桶上传文件完毕")
    @RequestMapping(value = "/upload/finish-upload", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType cosFinishUpload(@RequestParam(value = "fileName") String fileName,
                                            @RequestParam(value = "urlGUID") String urlGUID) throws BusinessException {
        // // 从数据库中找到该任务
        // ScheduleTaskModel task = scheduleTaskService.findTaskByGuid(urlGUID);
        // if (task == null) {
        //     throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "urlGUID 参数校验失败");
        // }
        //
        // // 完成这个任务
        // Boolean isSuccess = scheduleTaskService.doneScheduleTask(task.getId());

        // TODO
        Boolean isSuccess = true;
        return CommonReturnType.create(isSuccess);
    }
}
