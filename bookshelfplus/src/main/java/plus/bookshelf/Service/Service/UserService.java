package plus.bookshelf.Service.Service;

import org.springframework.data.redis.core.RedisTemplate;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Service.Model.UserModel;

public interface UserService {
    /**
     * 用户登录
     *
     * @param username   用户名
     * @param encryptPwd 加密后密码
     */
    UserModel userLogin(String username, String encryptPwd);

    /**
     * 通过用户Id获取用户
     *
     * @param id 用户Id
     * @return UserModel
     */
    UserModel getUserById(Integer id);

    /**
     * 检查用户令牌是否有效，并返回令牌对应的用户 UserModel
     * （令牌无效直接抛出异常）
     *
     * @param token 用户令牌
     * @return UserModel
     */
    UserModel getUserByToken(RedisTemplate redisTemplate, String token) throws BusinessException;

    /**
     * 用户注册
     *
     * @param username   用户名
     * @param encryptPwd 加密后密码
     * @return 注册成功返回true，否则返回false
     */
    Boolean userRegister(String username, String encryptPwd) throws BusinessException;
}
