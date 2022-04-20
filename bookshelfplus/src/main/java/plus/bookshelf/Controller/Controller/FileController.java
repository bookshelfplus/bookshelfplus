package plus.bookshelf.Controller.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import plus.bookshelf.Service.Impl.*;
import plus.bookshelf.Service.Model.FileModel;
import plus.bookshelf.Service.Model.FileObjectModel;
import plus.bookshelf.Service.Model.UserModel;
import plus.bookshelf.Service.Service.CosPresignedUrlGenerateLogService;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Api(tags = "文件管理")
@Controller("file")
@RequestMapping("/file")
public class FileController extends BaseController {

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

    @ApiOperation(value = "书籍下载页面获取文件提供的下载方式", notes = "")
    @RequestMapping(value = "getFileByBookId", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getFileByBookId(@RequestParam(value = "bookId", required = false) Integer bookId) throws BusinessException, InvocationTargetException, IllegalAccessException {

        List<FileModel> fileModels = fileService.getFileByBookId(bookId);
        List<FileVO> fileVOS = new ArrayList<>();
        for (FileModel fileModel : fileModels) {
            FileVO fileVO = convertFileVOFromModel(fileModel);
            fileVOS.add(fileVO);
        }

        List<FileObjectModel> fileObjectModels = fileObjectService.getFileObjectByBookId(bookId);
        List<FileObjectVO> fileObjectVOS = new ArrayList<>();
        for (FileObjectModel fileObjectModel : fileObjectModels) {
            FileObjectVO fileObjectVO = FileObjectController.convertFileObjectVOFromModel(fileObjectModel);
            fileObjectVOS.add(fileObjectVO);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("file", fileVOS);
        map.put("fileObject", fileObjectVOS);

        return CommonReturnType.create(map);
    }

    @ApiOperation(value = "通过 fileId 获取文件信息", notes = "")
    @RequestMapping(value = "getFileById", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getFileById(@RequestParam(value = "fileId", required = false) Integer fileId) throws BusinessException, InvocationTargetException, IllegalAccessException {
        FileModel fileModel = fileService.getFileById(fileId);
        FileVO fileVO = convertFileVOFromModel(fileModel);
        return CommonReturnType.create(fileVO);
    }

    @ApiOperation(value = "【管理员】查询文件列表", notes = "查询文件列表")
    @RequestMapping(value = "list", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType list(@RequestParam(value = "token", required = false) String token) throws InvocationTargetException, IllegalAccessException, BusinessException {

        UserModel userModel = userService.getUserByToken(redisTemplate, token);
        if (userModel == null || !Objects.equals(userModel.getGroup(), "ADMIN")) {
            throw new BusinessException(BusinessErrorCode.OPERATION_NOT_ALLOWED, "非管理员用户无权进行此操作");
        }

        List<FileModel> fileModels = fileService.list();
        List<FileVO> fileVOS = new ArrayList<>();
        for (FileModel fileModel : fileModels) {
            FileVO fileVO = convertFileVOFromModel(fileModel);
            fileVOS.add(fileVO);
        }
        return CommonReturnType.create(fileVOS);
    }

    @ApiOperation(value = "【管理员】查询文件列表（匹配文件哈希）", notes = "查询文件列表，返回文件哈希为空或者相同的文件")
    @RequestMapping(value = "list/MatchfileHashWithNullValue", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType matchfileHashWithNullValue(@RequestParam(value = "token", required = false) String token,
                                                       @RequestParam(value = "fileSha1", required = true) String fileSha1) throws InvocationTargetException, IllegalAccessException, BusinessException {

        UserModel userModel = userService.getUserByToken(redisTemplate, token);
        if (userModel == null || !Objects.equals(userModel.getGroup(), "ADMIN")) {
            throw new BusinessException(BusinessErrorCode.OPERATION_NOT_ALLOWED, "非管理员用户无权进行此操作");
        }

        List<FileModel> fileModels = fileService.selectBySha1WithNullValue(fileSha1);
        List<FileVO> fileVOS = new ArrayList<>();
        for (FileModel fileModel : fileModels) {
            FileVO fileVO = convertFileVOFromModel(fileModel);
            fileVOS.add(fileVO);
        }
        return CommonReturnType.create(fileVOS);
    }

    @ApiOperation(value = "【管理员】通过文件SHA1哈希查找文件Id", notes = "查询文件列表，返回文件哈希匹配的文件Id")
    @RequestMapping(value = "getFileByHash", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getFileByHash(@RequestParam(value = "token", required = false) String token,
                                          @RequestParam(value = "fileSha1", required = true) String fileSha1) throws InvocationTargetException, IllegalAccessException, BusinessException {

        UserModel userModel = userService.getUserByToken(redisTemplate, token);
        if (userModel == null || !Objects.equals(userModel.getGroup(), "ADMIN")) {
            throw new BusinessException(BusinessErrorCode.OPERATION_NOT_ALLOWED, "非管理员用户无权进行此操作");
        }

        FileModel fileModel = fileService.selectBySha1(fileSha1);
        FileVO fileVO = convertFileVOFromModel(fileModel);
        return CommonReturnType.create(fileVO);
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
    @ApiOperation(value = "【用户|管理员】创建腾讯云 COS 预授权 URL", notes = "")
    @RequestMapping(value = "/cos/{httpMethod}", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType cos(@PathVariable(value = "httpMethod") String httpMethod,
                                @RequestParam(value = "token") String token,
                                @RequestParam(value = "fileSha1") String fileSha1,
                                @RequestParam(value = "expireMinute", required = false) Integer expireMinute,

                                // 以下为 PUT 请求必传参数
                                @RequestParam(value = "fileName", required = false) String fileName, // 不含扩展名
                                @RequestParam(value = "fileSize", required = false) Long fileSize,
                                // @RequestParam(value = "fileType", required = false) String fileType,
                                @RequestParam(value = "lastModified", required = false) Long lastModified,
                                @RequestParam(value = "fileExt", required = false) String fileExt,
                                @RequestParam(value = "fileId", required = false) Integer fileId, // 关联的文件ID，创建新文件则为0

                                // 以下为 GET 请求必传参数
                                @RequestParam(value = "fileNameAndExt", required = false) String fileNameAndExt,
                                @RequestParam(value = "visitorId", required = false) String visitorFingerprint
    ) throws BusinessException, InvocationTargetException, IllegalAccessException {
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
        Boolean isExist = qCloudCosUtils.doesObjectExist(bookSaveFolder, fileSha1);
        String url = null;
        Map resultMap = new HashMap();
        resultMap.put("guid", urlGUID);
        switch (httpMethodName) {
            case PUT:
                // 上传文件
                if (isExist) throw new BusinessException(BusinessErrorCode.FILE_ALREADY_EXIST, "文件已存在");

                Integer[] integers = fileObjectService.uploadFile(fileId, fileName, bookSaveFolder + fileSha1, fileSize,
                        fileSha1, fileExt, FileStorageMediumEnum.QCLOUD_COS, "", lastModified);
                Integer realFileId = integers[0];
                Integer fileObjectId = integers[1];

                // fileId 可能为 0 （创建新文件）
                // realFileId 是从数据库中查询出来的，真实的文件id
                resultMap.put("fileId", realFileId);
                resultMap.put("fileObjectId", fileObjectId);
                url = qCloudCosUtils.generatePresignedUrl(userModel.getId(), httpMethodName, bookSaveFolder, fileSha1, expireMinute, urlGUID);
                break;
            case GET:
                if (!isExist) throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "文件不存在");
                if (visitorFingerprint == null || !visitorFingerprintLogService.saveFingerprint("FailureFeedback", userModel.getId(), visitorFingerprint)) {
                    throw new BusinessException(BusinessErrorCode.OPERATION_NOT_ALLOWED, "参数错误，请联系管理员处理");
                }
                url = qCloudCosUtils.generatePresignedUrlForGET(userModel.getId(), bookSaveFolder, fileSha1, expireMinute, urlGUID, fileNameAndExt);
                break;
            case DELETE:
                if (!isExist) throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "文件不存在");
                url = qCloudCosUtils.generatePresignedUrl(userModel.getId(), httpMethodName, bookSaveFolder, fileSha1, expireMinute, urlGUID);
                break;
            default:
                throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "httpMethod 参数暂不支持");
        }

        resultMap.put("url", url);
        return CommonReturnType.create(resultMap);
    }

    /**
     * 腾讯云 COS 文件上传成功回调方法
     *
     * @param eventStr
     * @param contextStr
     * @return
     * @throws BusinessException
     */
    @ApiOperation(value = "【COS】腾讯云 COS 文件上传成功回调", notes = "客户端向腾讯云 COS 存储桶上传文件完毕，有云函数触发此请求")
    @RequestMapping(value = "/upload/cos-check-file-state", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType cosCheckFileStatus(
            // @RequestParam() Map<String, String> params,
            @RequestParam(value = "event") String eventStr,
            @RequestParam(value = "context", required = false) String contextStr
    ) throws BusinessException, InvocationTargetException, IllegalAccessException {

        JSONObject eventObject;

        try {
            eventObject = JSON.parseObject(eventStr);
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "event参数不合法");
        }

        String cosBucketName, appid, cosRegion, eventName, key;
        try {
            // 获取 Records 节点
            JSONArray records = eventObject.getJSONArray("Records");
            JSONObject record = records.getJSONObject(0);

            JSONObject cos = record.getJSONObject("cos");
            JSONObject cosBucket = cos.getJSONObject("cosBucket");
            appid = cosBucket.getString("appid");
            cosBucketName = cosBucket.getString("name");
            if (Objects.equals(appid, "1253970026") && Objects.equals(cosBucketName, "testpic")) {
                // 执行的是腾讯云云函数的测试请求
                return CommonReturnType.create("您的云函数配置成功。");
            }
            cosRegion = cosBucket.getString("cosRegion");

            JSONObject cosObject = cos.getJSONObject("cosObject");
            key = cosObject.getString("key");
            // 获取 /1000000000/bookshelfplus/fileSha1 中的 fileSha1 部分
            key = key.substring(key.indexOf("/", key.indexOf("/", key.indexOf("/") + 1) + 1) + 1);

            JSONObject event = record.getJSONObject("event");
            eventName = event.getString("eventName");

        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "JSON解析出错！");
        }

        // 判断是否是由系统的存储桶触发
        if (qCloudCosConfig.getBucketName().equals(cosBucketName + "-" + appid) &&
                qCloudCosConfig.getRegionName().equals(cosRegion) &&
                "cos:ObjectCreated:Put".equals(eventName)) {
            // 是由系统的存储桶触发的，则认为是文件上传成功

            // 通过文件 key 获取文件对象
            FileObjectModel fileObject = fileObjectService.getFileObjectByFilePath(QCloudCosUtils.BOOK_SAVE_FOLDER + key);

            // 如果找不到，就抛出异常
            if (fileObject == null) {
                throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "文件不存在！");
            }
            // 更新文件对象状态
            Boolean isSuccess1 = fileObjectService.updateFileStatus(fileObject.getId(), "SUCCESS");

            if (!isSuccess1) {
                throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "更新文件状态失败");
            }
        } else {
            // 不是由系统的存储桶触发的
            return CommonReturnType.create("Not triggered by the bucket specified in the configuration file, skip.");
        }
        return CommonReturnType.create("success");
    }

    private FileVO convertFileVOFromModel(FileModel fileModel) {
        if (fileModel == null) {
            return null;
        }
        FileVO fileVO = new FileVO();
        BeanUtils.copyProperties(fileModel, fileVO);
        fileVO.setFileCreateAt(fileModel.getFileCreateAt().getTime());
        fileVO.setFileModifiedAt(fileModel.getFileModifiedAt().getTime());
        return fileVO;
    }
}

/*
示例返回
event
{
    "Records": [
        {
            "cos": {
                "cosBucket": {
                    "appid": "1302260381",
                    "cosRegion": "ap-shanghai",
                    "name": "bookshelfplus",
                    "region": "sh",
                    "s3Region": "ap-shanghai"
                },
                "cosNotificationId": "unkown",
                "cosObject": {
                    "key": "/1302260381/bookshelfplus/!!!!!!!",
                    "meta": {
                        "Content-Type": "text/plain",
                        "ETag": "\"0d7316832fef232e5dcdcf81f39bfdba\"",
                        "x-cos-request-id": "NjI1OTNmOWNfNGIzN2YyMDlfMmJhZjVfNDJkZTBmYQ==",
                        "x-cos-storage-class": "Standard"
                    },
                    "size": 557,
                    "url": "http://bookshelfplus-1302260381.cos.ap-shanghai.myqcloud.com/%21%21%21%21%21%21%21",
                    "vid": ""
                },
                "cosSchemaVersion": "1.0"
            },
            "event": {
                "eventName": "cos:ObjectCreated:Put",
                "eventQueue": "qcs:0:scf:ap-shanghai:appid/1302260381:default.bookshelf-scf.$DEFAULT",
                "eventSource": "qcs::cos",
                "eventTime": 1650016156,
                "eventVersion": "1.0",
                "reqid": 0,
                "requestParameters": {
                    "requestHeaders": {
                        "Authorization": "q-sign-algorithm=sha1&q-ak=AKIDgEWYJo2yd7KGvIPFn45pJWT9YgX8RTEi&q-sign-time=1650016155;1650017955&q-key-time=1650016155;1650017955&q-header-list=host&q-url-param-list=by;guid;userid&q-signature=0ceb85180d6b0b665662d5d139d4276cdc0fbbbd"
                    },
                    "requestSourceIP": "117.154.65.144"
                },
                "reservedInfo": ""
            }
        }
    ]
}

context
{
    "callbackWaitsForEmptyEventLoop": true,
    "memory_limit_in_mb": 512,
    "time_limit_in_ms": 3000,
    "request_id": "279ba5cc-e435-4af9-8ede-baa71373d75b",
    "environment": "{\"SCF_NAMESPACE\":\"default\"}",
    "environ": "SCF_NAMESPACE=default;SCF_NAMESPACE=default",
    "function_version": "$LATEST",
    "function_name": "bookshelf-scf",
    "namespace": "default",
    "tencentcloud_region": "ap-shanghai",
    "tencentcloud_appid": "1302260381",
    "tencentcloud_uin": "100014397291"
}
 */