package plus.bookshelf.Service.Service;

import plus.bookshelf.Common.Error.BusinessException;

public interface CosPresignedUrlGenerateLogService {
    /**
     * 记录cos获取的预授权url日志
     *
     * @param userId     用户id
     * @param expireTime 过期时间(分钟)
     * @param method     请求方法
     * @param filePath   文件路径
     * @param urlGuid    url唯一标识
     * @return
     */
    void log(Integer userId, Integer expireTime, String method, String filePath, String urlGuid) throws BusinessException;
}
