package plus.bookshelf.Controller.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import plus.bookshelf.Common.Enum.FileStorageMediumEnum;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.FileManager.QCloudCosUtils;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Config.QCloudCosConfig;
import plus.bookshelf.Controller.VO.FileObjectVO;
import plus.bookshelf.Service.Impl.*;
import plus.bookshelf.Service.Model.FailureFeedbackModel;
import plus.bookshelf.Service.Model.FileModel;
import plus.bookshelf.Service.Model.FileObjectModel;
import plus.bookshelf.Service.Model.UserModel;
import plus.bookshelf.Service.Service.CosPresignedUrlGenerateLogService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Api(tags = "文件对象管理")
@Controller("file/object")
@RequestMapping("/file/object")
public class FileObjectController extends BaseController {

    @Autowired
    QCloudCosConfig qCloudCosConfig;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    CosPresignedUrlGenerateLogService cosPresignedUrlGenerateLogService;

    @Autowired
    FileObjectServiceImpl fileObjectService;

    @Autowired
    FailureFeedbackServiceImpl failureFeedbackService;

    @Autowired
    VisitorFingerprintLogServiceImpl visitorFingerprintLogService;


    @ApiOperation(value = "链接失效反馈", notes = "查询文件列表")
    @RequestMapping(value = "FailureFeedback", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType failureFeedback(@RequestParam(value = "token", required = false) String token,
                                            @RequestParam(value = "bookId", required = false) Integer bookId,
                                            @RequestParam(value = "fileId", required = false) Integer fileId,
                                            @RequestParam(value = "fileObjectId", required = false) Integer fileObjectId,
                                            @RequestParam(value = "visitorId", required = true) String visitorFingerprint) throws BusinessException {

        Integer userId = null;
        if (token != null) {
            try {
                UserModel userModel = userService.getUserByToken(redisTemplate, token);
                userId = userModel.getId();
            } catch (BusinessException e) {
                // 用户未登录，不返回错误信息
            }
        }

        if (!visitorFingerprintLogService.saveFingerprint("Failure Feedback", userId, visitorFingerprint)) {
            throw new BusinessException(BusinessErrorCode.OPERATION_NOT_ALLOWED, "参数错误，请联系管理员处理");
        }

        FailureFeedbackModel failureFeedbackModel = new FailureFeedbackModel();
        failureFeedbackModel.setBookId(bookId);
        failureFeedbackModel.setFileId(fileId);
        failureFeedbackModel.setFileObjectId(fileObjectId);
        failureFeedbackModel.setUserId(userId);
        failureFeedbackModel.setCreateTime(Calendar.getInstance().getTime());

        Boolean isSuccess = failureFeedbackService.addFailureFeedback(failureFeedbackModel);
        if (!isSuccess) {
            throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "反馈失败");
        }
        return CommonReturnType.create(isSuccess);
    }

    @ApiOperation(value = "【管理员】查询文件对象列表", notes = "查询文件列表")
    @RequestMapping(value = "list", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType objectList(@RequestParam(value = "token", required = false) String token) throws InvocationTargetException, IllegalAccessException, BusinessException {

        UserModel userModel = userService.getUserByToken(redisTemplate, token);
        if (userModel == null || !Objects.equals(userModel.getGroup(), "ADMIN")) {
            throw new BusinessException(BusinessErrorCode.OPERATION_NOT_ALLOWED, "非管理员用户无权进行此操作");
        }

        List<FileObjectModel> fileObjectModels = fileObjectService.list();
        List<FileObjectVO> fileObjectVOS = new ArrayList<>();
        for (FileObjectModel fileObjectModel : fileObjectModels) {
            FileObjectVO fileObjectVO = convertFileObjectVOFromModel(fileObjectModel);
            fileObjectVOS.add(fileObjectVO);
        }
        return CommonReturnType.create(fileObjectVOS);
    }

    @ApiOperation(value = "【管理员】更新文件对象上传状态", notes = "重新从 COS 对象存储中获取文件对象上传状态")
    @RequestMapping(value = "refreshFileObjectStatus", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType refreshFileObjectStatus(@RequestParam(value = "token", required = false) String token,
                                                    @RequestParam(value = "fileObjectId") Integer fileObjectId) throws InvocationTargetException, IllegalAccessException, BusinessException {

        // 验证用户权限
        UserModel userModel = userService.getUserByToken(redisTemplate, token);
        if (userModel == null || !Objects.equals(userModel.getGroup(), "ADMIN")) {
            throw new BusinessException(BusinessErrorCode.OPERATION_NOT_ALLOWED, "非管理员用户无权进行此操作");
        }

        // 获取旧文件权限
        FileObjectModel fileObjectModel = fileObjectService.getFileObjectById(fileObjectId);
        String filePath = fileObjectModel.getFilePath();
        String oldUploadStatus = fileObjectModel.getUploadStatus();

        QCloudCosUtils qCloudCosUtils = new QCloudCosUtils(qCloudCosConfig, cosPresignedUrlGenerateLogService);

        // 判断对象是否存在
        Boolean isExist = qCloudCosUtils.doesObjectExist(filePath);

        Boolean isSuccess = false;

        if (!isExist) {
            // 现在文件不存在
            switch (oldUploadStatus) {
                case "NOT_EXIST": // 文件不存在，不需要更新
                default:
                    isSuccess = true;
                    break;
                case "SUCCESS": // 上传成功，但是文件不存在了，更新为不存在
                case "UPLOADING": // 上传中，更新为不存在
                    isSuccess = fileObjectService.updateFileStatus(fileObjectModel.getId(), "NOT_EXIST");
                    break;
            }
        } else {
            // 现在文件存在
            switch (oldUploadStatus) {
                case "SUCCESS": // 之前上传成功，现在存在，不做任何操作
                default:
                    isSuccess = true;
                    break;
                case "NOT_EXIST": // 之前不存在，现在存在，更新为上传成功
                case "UPLOADING": // 之前上传中，现在存在，更新为上传成功
                    isSuccess = fileObjectService.updateFileStatus(fileObjectModel.getId(), "SUCCESS");
                    break;
            }
        }
        return CommonReturnType.create(isSuccess);
    }

    public static FileObjectVO convertFileObjectVOFromModel(FileObjectModel fileObjectModel) {
        if (fileObjectModel == null) {
            return null;
        }
        FileObjectVO fileObjectVO = new FileObjectVO();
        BeanUtils.copyProperties(fileObjectModel, fileObjectVO);
        try {
            // 尝试将 FileStorageMedium 转为中文，如果没有成功，那么就保留英文
            fileObjectVO.setStorageMedium(FileStorageMediumEnum.valueOf(fileObjectModel.getStorageMedium()).getStorageMediumDisplayName());
        } catch (Exception e) {
        }
        return fileObjectVO;
    }
}
