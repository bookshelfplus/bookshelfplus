package plus.bookshelf.Controller.Controller;

import com.qcloud.cos.http.HttpMethodName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.FileManager.QCloudCosUtils;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Config.QCloudCosConfig;
import plus.bookshelf.Controller.VO.FileVO;
import plus.bookshelf.Service.Impl.FileServiceImpl;
import plus.bookshelf.Service.Impl.UserServiceImpl;
import plus.bookshelf.Service.Model.FileModel;
import plus.bookshelf.Service.Model.UserModel;
import plus.bookshelf.Service.Service.CosPresignedUrlGenerateLogService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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

    @ApiOperation(value = "查询文件列表", notes = "查询文件列表")
    @RequestMapping(value = "list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType list() throws InvocationTargetException, IllegalAccessException {
        List<FileModel> fileModels = fileService.list();
        List<FileVO> fileVOS = new ArrayList<>();
        for (FileModel fileModel : fileModels) {
            FileVO fileVO = convertFromModel(fileModel);
            fileVOS.add(fileVO);
        }
        return CommonReturnType.create(fileVOS);
    }

    private FileVO convertFromModel(FileModel fileModel) {
        FileVO fileVO = new FileVO();
        BeanUtils.copyProperties(fileModel, fileVO);
        fileVO.setFileCreateAt(fileModel.getFileCreateAt().getTime());
        fileVO.setFileModifiedAt(fileModel.getFileModifiedAt().getTime());
        return fileVO;
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

        // 判断对象是否存在
        Boolean isExist = qCloudCosUtils.doesObjectExist(fileName);
        switch (httpMethodName) {
            case PUT:
                if (isExist) throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "文件已存在");
                break;
            case GET:
            case DELETE:
                if (!isExist) throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "文件不存在");
                break;
            default:
                throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "httpMethod 参数暂不支持");
        }

        String url = qCloudCosUtils.generatePresignedUrl(userModel.getId(), httpMethodName, fileName, 30);

        return CommonReturnType.create(url);
    }
}
