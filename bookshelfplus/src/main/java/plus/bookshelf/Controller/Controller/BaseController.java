package plus.bookshelf.Controller.Controller;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Common.Response.CommonReturnTypeStatus;
import plus.bookshelf.Common.SessionManager.LocalSessionManager;
import plus.bookshelf.Common.SessionManager.RedisSessionManager;
import plus.bookshelf.Common.SessionManager.SessionManager;
import plus.bookshelf.Service.Model.UserModel;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BaseController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    RedisTemplate redisTemplate;

    // content-type 常量
    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    // PageHelper分页常量
    public static final Integer COMMON_START_PAGE = 1;
    public static final Integer COMMON_PAGE_SIZE = 10;

    /**
     * 获取用户登陆状态
     */
    public Boolean isLogin() {
        SessionManager sessionManager = RedisSessionManager.getInstance(redisTemplate);
        return (Boolean) sessionManager.getValue("IS_LOGIN");

        // SessionManager sessionManager = LocalSessionManager.getInstance(httpServletRequest);
        // return (Boolean) sessionManager.getValue("IS_LOGIN");
    }

    /**
     * 保存用户的登录状态
     *
     * @return String uuidToken
     */
    public String onLogin(UserModel userModel) {
        String token = NanoIdUtils.randomNanoId(); // UUID.randomUUID().toString();
        SessionManager sessionManager = RedisSessionManager.getInstance(redisTemplate);
        sessionManager.setValue(token, userModel.getId());
        return token;

        // SessionManager sessionManager = LocalSessionManager.getInstance(httpServletRequest);
        // sessionManager.setValue("IS_LOGIN", true);
        // sessionManager.setValue("user", userModel);
        // return;
    }

    /**
     * 用户退出登录
     */
    public void onLogout(String token) {
        SessionManager sessionManager = RedisSessionManager.getInstance(redisTemplate);
        sessionManager.remove(token);
        return;

        // SessionManager sessionManager = LocalSessionManager.getInstance(httpServletRequest);
        // sessionManager.setValue("IS_LOGIN", false);
        // sessionManager.remove("user");
        // return;
    }

    // 定义ExceptionHandler解决未被Controller层吸收的Exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex) {
        HashMap<Object, Object> responseData = new HashMap<>();

        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            responseData.put("errCode", businessException.getErrCode());
            responseData.put("errMsg", businessException.getErrMsg());
        } else {
            // 生产环境输出格式化信息
            responseData.put("errCode", BusinessErrorCode.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", BusinessErrorCode.UNKNOWN_ERROR.getErrMsg());
        }

        return CommonReturnType.create(responseData, CommonReturnTypeStatus.FAILED);
    }
}
