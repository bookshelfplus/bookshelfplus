package plus.bookshelf.Service.Service;

public interface VisitorFingerprintLogService {
    /**
     * 将用户指纹信息存入数据库
     *
     * @param action
     * @param userId
     * @param fingerprint
     * @return
     */
    Boolean saveFingerprint(String action, Integer userId, String fingerprint);
}
