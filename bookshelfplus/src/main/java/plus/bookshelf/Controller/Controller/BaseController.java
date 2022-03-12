package plus.bookshelf.Controller.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import plus.bookshelf.Service.Model.UserModel;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BaseController {
    // content-type 常量
    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取用户登陆状态
     */
    public Boolean isLogin() {
        return (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
    }

    /**
     * 保存用户的登录状态
     * @return String uuidToken
     */
    public String onLogin(UserModel userModel) {
        String uuidToken = UUID.randomUUID().toString();
        redisTemplate.expire(uuidToken, 1, TimeUnit.HOURS);

        // 建立token和用户登录态之间的联系
        redisTemplate.opsForValue().set(uuidToken, userModel);
        return uuidToken;
    }
}
