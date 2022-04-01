package plus.bookshelf.Service.Service;

import org.springframework.data.redis.core.RedisTemplate;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Service.Model.UserModel;

public interface UserService {
    /**
     * 用户登录
     *
     * @param username
     * @param encryptPwd
     */
    UserModel userLogin(String username, String encryptPwd);

    /**
     * 通过用户Id获取用户
     *
     * @param id 用户Id
     * @return
     */
    UserModel getUserById(Integer id);

    /**
     * 检查用户令牌是否有效，并返回令牌对应的用户 UserModel
     * （令牌无效直接抛出异常）
     *
     * @param token
     * @return
     */
    UserModel getUserByToken(RedisTemplate redisTemplate, String token) throws BusinessException;
}
