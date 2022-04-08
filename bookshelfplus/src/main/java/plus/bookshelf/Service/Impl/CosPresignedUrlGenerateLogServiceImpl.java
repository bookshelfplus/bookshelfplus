package plus.bookshelf.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Dao.DO.CosPresignedUrlGenerateLogDO;
import plus.bookshelf.Dao.Mapper.CosPresignedUrlGenerateLogDOMapper;
import plus.bookshelf.Service.Service.CosPresignedUrlGenerateLogService;

import java.util.Date;

@Service
public class CosPresignedUrlGenerateLogServiceImpl implements CosPresignedUrlGenerateLogService {

    @Autowired
    CosPresignedUrlGenerateLogDOMapper cosPresignedUrlGenerateLogDOMapper;

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
    @Override
    public void log(Integer userId, Integer expireTime, String method, String filePath, String urlGuid) throws BusinessException {
        CosPresignedUrlGenerateLogDO cosPresignedUrlGenerateLogDO = new CosPresignedUrlGenerateLogDO();
        cosPresignedUrlGenerateLogDO.setUserId(userId);
        cosPresignedUrlGenerateLogDO.setTime(new Date());
        cosPresignedUrlGenerateLogDO.setExpireMinute(expireTime);
        cosPresignedUrlGenerateLogDO.setMethod(method);
        cosPresignedUrlGenerateLogDO.setFilePath(filePath);
        cosPresignedUrlGenerateLogDO.setUrlGuid(urlGuid);

        Integer affectRows = cosPresignedUrlGenerateLogDOMapper.insertSelective(cosPresignedUrlGenerateLogDO);
        if (affectRows == 0) {
            throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "日志记录失败");
        }
    }
}
