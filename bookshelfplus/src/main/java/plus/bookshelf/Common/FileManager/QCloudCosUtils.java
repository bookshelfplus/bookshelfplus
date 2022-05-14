package plus.bookshelf.Common.FileManager;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.Headers;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.ResponseHeaderOverrides;
import com.qcloud.cos.region.Region;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Config.QCloudCosConfig;
import plus.bookshelf.Service.Service.CosPresignedUrlGenerateLogService;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QCloudCosUtils {

    // 定义常量
    public static final String BOOK_SAVE_FOLDER = "book/"; //一定要以 / 结尾

    // 配置信息
    QCloudCosConfig qCloudCosConfig;

    // 记录日志 Service
    CosPresignedUrlGenerateLogService cosPresignedUrlGenerateLogService;

    /**
     * 构造函数
     *
     * @param qCloudCosConfig
     */
    public QCloudCosUtils(QCloudCosConfig qCloudCosConfig, CosPresignedUrlGenerateLogService cosPresignedUrlGenerateLogService) {
        this.qCloudCosConfig = qCloudCosConfig;
        this.cosPresignedUrlGenerateLogService = cosPresignedUrlGenerateLogService;
    }

    private static COSClient _cosClient = null;

    // refer: https://cloud.tencent.com/document/product/436/35217#.E5.88.9B.E5.BB.BA-cosclient
    // 创建 COSClient 实例，这个实例用来后续调用请求
    COSClient createCOSClient() {
        if (_cosClient != null) {
            return _cosClient;
        }
        // 设置用户身份信息。
        // SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = qCloudCosConfig.getAccessKey();
        String secretKey = qCloudCosConfig.getSecretKey();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        // ClientConfig 中包含了后续请求 COS 的客户端设置：
        ClientConfig clientConfig = new ClientConfig();

        // 设置 bucket 的地域
        // COS_REGION 请参照 https://cloud.tencent.com/document/product/436/6224
        clientConfig.setRegion(new Region(qCloudCosConfig.getRegionName()));

        // 设置请求协议, http 或者 https
        // 5.6.53 及更低的版本，建议设置使用 https 协议
        // 5.6.54 及更高版本，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);

        // 以下的设置，是可选的：

        // // 设置 socket 读取超时，默认 30s
        // clientConfig.setSocketTimeout(30*1000);
        // // 设置建立连接超时，默认 30s
        // clientConfig.setConnectionTimeout(30*1000);
        //
        // // 如果需要的话，设置 http 代理，ip 以及 port
        // clientConfig.setHttpProxyIp("httpProxyIp");
        // clientConfig.setHttpProxyPort(80);

        // 生成 cos 客户端
        _cosClient = new COSClient(cred, clientConfig);
        System.out.println("cosClient construct success.");
        return _cosClient;
    }

    /**
     * 判断文件是否存在
     *
     * @param folder
     * @param objectKey
     * @return
     */
    public Boolean doesObjectExist(String folder, String objectKey) {
        return doesObjectExist(folder + objectKey);
    }

    public Boolean doesObjectExist(String filePath) {
        COSClient cosClient = createCOSClient();
        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = qCloudCosConfig.getBucketName();
        // 对象键(Key)是对象在存储桶中的唯一标识。详情请参见 [对象键](https://cloud.tencent.com/document/product/436/13324)
        String key = qCloudCosConfig.getKeyName() + filePath;
        return cosClient.doesObjectExist(bucketName, key);
    }

    /**
     * 删除文件
     *
     * @param folder
     * @param objectKey
     * @return
     */
    public Boolean deleteObject(String folder, String objectKey) {
        return deleteObject(folder + objectKey);
    }

    public Boolean deleteObject(String filePath) {
        COSClient cosClient = createCOSClient();
        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = qCloudCosConfig.getBucketName();
        // 对象键(Key)是对象在存储桶中的唯一标识。详情请参见 [对象键](https://cloud.tencent.com/document/product/436/13324)
        String key = qCloudCosConfig.getKeyName() + filePath;

        // refer: https://cloud.tencent.com/document/product/436/65939#.E5.88.A0.E9.99.A4.E5.AF.B9.E8.B1.A1
        try {
            cosClient.deleteObject(bucketName, key);
            return true;
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 生成预签名URL
     * <p>
     * refer: https://cloud.tencent.com/document/product/436/35217
     *
     * @param userId         当前登录用户的 id
     * @param httpMethodName 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
     * @param objectKey      文件对象的 key
     * @param expireMinute   过期时间
     * @return
     */
    public String generatePresignedUrl(Integer userId, HttpMethodName httpMethodName, String savePath, String objectKey, Integer expireMinute, String urlGUID) throws BusinessException {
        // 调用 COS 接口之前必须保证本进程存在一个 COSClient 实例，如果没有则创建
        // 详细代码参见本页：简单操作 -> 创建 COSClient
        COSClient cosClient = createCOSClient();

        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = qCloudCosConfig.getBucketName();
        // 对象键(Key)是对象在存储桶中的唯一标识。详情请参见 [对象键](https://cloud.tencent.com/document/product/436/13324)
        String key = qCloudCosConfig.getKeyName() + savePath + objectKey;

        // 设置签名过期时间(可选), 若未进行设置则默认使用 ClientConfig 中的签名过期时间(1小时)
        // 这里设置签名在 expireMinute 分钟后过期
        Date expirationDate = new Date(System.currentTimeMillis() + expireMinute * 60 * 1000);

        // 填写本次请求的参数，需与实际请求相同，能够防止用户篡改此签名的 HTTP 请求的参数
        Map<String, String> params = new HashMap<>();
        params.put("by", "书单网 bookshelf.plus");
        params.put("userId", String.valueOf(userId));
        params.put("guid", urlGUID);

        // 填写本次请求的头部，需与实际请求相同，能够防止用户篡改此签名的 HTTP 请求的头部
        Map<String, String> headers = new HashMap<>();
        // headers.put("header1", "value1");

        // 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
        HttpMethodName method = httpMethodName;

        if (method.equals(HttpMethodName.DELETE)) {
            throw new BusinessException(BusinessErrorCode.NOT_SUPPORT, "不支持DELETE方法，请在后台文件管理页面删除文件，而非通过此Api");
        }

        URL url = cosClient.generatePresignedUrl(bucketName, key, expirationDate, method, headers, params);
        // System.out.println(url.toString());

        // 记录用户操作日志
        cosPresignedUrlGenerateLogService.log(userId, expireMinute, httpMethodName.name(), key, urlGUID);

        return url.toString();
    }

    /**
     * 生成预签名URL (下载使用，用于下载时指定下载的文件名)
     * <p>
     * refer: https://cloud.tencent.com/document/product/436/35217
     *
     * @param userId          当前登录用户的 id
     * @param savePath
     * @param objectKey       文件对象的 key
     * @param expireMinute    过期时间
     * @param urlGUID
     * @param fileNameForUser
     * @return
     * @throws BusinessException
     */
    public String generatePresignedUrlForGET(Integer userId, String savePath, String objectKey, Integer expireMinute, String urlGUID, String fileNameForUser) throws BusinessException {
        // 调用 COS 接口之前必须保证本进程存在一个 COSClient 实例，如果没有则创建
        // 详细代码参见本页：简单操作 -> 创建 COSClient
        COSClient cosClient = createCOSClient();

        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = qCloudCosConfig.getBucketName();
        // 对象键(Key)是对象在存储桶中的唯一标识。详情请参见 [对象键](https://cloud.tencent.com/document/product/436/13324)
        String key = qCloudCosConfig.getKeyName() + savePath + objectKey;

        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);

        // 设置下载时返回的 http 头
        ResponseHeaderOverrides responseHeaders = new ResponseHeaderOverrides();
        responseHeaders.setContentDisposition("attachment; filename=\"" + fileNameForUser + "\""); // 作为附件下载；设置返回头部里包含文件名信息
        responseHeaders.setCacheControl("no-cache");
        req.setResponseHeaders(responseHeaders);

        // 设置签名过期时间(可选)，若未进行设置，则默认使用 ClientConfig 中的签名过期时间(1小时)
        // 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 30L * 60L * 1000L);
        req.setExpiration(expirationDate);

        // 填写本次请求的参数，需与实际请求相同，能够防止用户篡改此签名的 HTTP 请求的参数
        Map<String, String> params = new HashMap<>();
        req.addRequestParameter("by", "书单网 bookshelf.plus");
        req.addRequestParameter("userId", String.valueOf(userId));
        req.addRequestParameter("guid", urlGUID);
        // 填写本次请求的头部
        // host 必填
        req.putCustomRequestHeader(Headers.HOST, cosClient.getClientConfig().getEndpointBuilder().buildGeneralApiEndpoint(bucketName));
        // req.putCustomRequestHeader("header1", "value1");

        URL url = cosClient.generatePresignedUrl(req);

        // 记录用户操作日志
        cosPresignedUrlGenerateLogService.log(userId, expireMinute, HttpMethodName.GET.name(), key, urlGUID);

        return url.toString();
    }

    /**
     * 销毁 CosClient 对象方法
     */
    public static void destoryInstance() {
        if (_cosClient != null) {
            // 确认本进程不再使用 cosClient 实例之后，关闭之
            _cosClient.shutdown();
            System.out.println("cosClient destory success.");
        }
    }
}