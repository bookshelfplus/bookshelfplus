package plus.bookshelf.Controller.Controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Common.Response.CommonReturnTypeStatus;
import plus.bookshelf.Service.Model.UserModel;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BaseController {
    // content-type 常量
    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    // PageHelper分页常量
    public static final Integer COMMON_START_PAGE = 1;
    public static final Integer COMMON_PAGE_SIZE = 10;

    @Autowired
    HttpServletRequest httpServletRequest;

    // @Autowired
    // private RedisTemplate redisTemplate;

    /**
     * 获取用户登陆状态
     */
    public Boolean isLogin() {
        return (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
    }

    /**
     * 保存用户的登录状态
     *
     * @return String uuidToken
     */
    public String onLogin(UserModel userModel) {
        String uuidToken = UUID.randomUUID().toString();
        // redisTemplate.expire(uuidToken, 1, TimeUnit.HOURS);

        // // 建立token和用户登录态之间的联系
        // redisTemplate.opsForValue().set(uuidToken, userModel);
        return uuidToken;
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
            responseData.put("errCode", BusinessErrorCode.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", BusinessErrorCode.UNKNOWN_ERROR.getErrMsg());
        }

        return CommonReturnType.create(responseData, CommonReturnTypeStatus.FAILED);
    }
}
