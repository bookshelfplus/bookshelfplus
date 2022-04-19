package plus.bookshelf.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.bookshelf.Dao.DO.VisitorFingerprintLogDO;
import plus.bookshelf.Dao.Mapper.VisitorFingerprintLogDOMapper;
import plus.bookshelf.Service.Service.VisitorFingerprintLogService;

import java.util.Calendar;

@Service
public class VisitorFingerprintLogServiceImpl implements VisitorFingerprintLogService {

    @Autowired
    VisitorFingerprintLogDOMapper visitorFingerprintLogDOMapper;

    /**
     * 将用户指纹信息存入数据库
     *
     * @param action
     * @param userId
     * @param fingerprint
     * @return
     */
    @Override
    public Boolean saveFingerprint(String action, Integer userId, String fingerprint) {

        VisitorFingerprintLogDO visitorFingerprintLogDO = new VisitorFingerprintLogDO();
        visitorFingerprintLogDO.setCreateAt(Calendar.getInstance().getTime());
        visitorFingerprintLogDO.setAction(action);
        visitorFingerprintLogDO.setUserId(userId);
        visitorFingerprintLogDO.setVisitorId(fingerprint);

        if (visitorFingerprintLogDO == null) {
            return false;
        } else {
            int affectRows = visitorFingerprintLogDOMapper.insertSelective(visitorFingerprintLogDO);
            return affectRows > 0;
        }
    }
}
