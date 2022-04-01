package plus.bookshelf.Controller.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Common.SessionManager.LocalSessionManager;
import plus.bookshelf.Common.SessionManager.RedisSessionManager;
import plus.bookshelf.Controller.VO.UserVO;
import plus.bookshelf.Dao.Mapper.UserDOMapper;
import plus.bookshelf.Service.Impl.UserServiceImpl;
import plus.bookshelf.Service.Model.UserModel;

@Api(tags = "用户操作")
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    UserServiceImpl userService;

    @ApiOperation(value = "用户登录", notes = "传入用户名，以及密码明文，后台计算密码SHA1值，进行登录")
    // @ApiImplicitParams(value = {
    //         @ApiImplicitParam(name = "username", value = "用户名", example = "username1", paramType = "form", dataType = "String", required = true, dataTypeClass = String.class),
    //         @ApiImplicitParam(name = "password", value = "密码", example = "password1", paramType = "form", dataType = "String", required = true, dataTypeClass = String.class)
    // })
    @RequestMapping(value = "login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(value = "username") String username,
                                  @RequestParam(value = "password") String password) throws BusinessException {
        if (username == null || password == null) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR);
        }
        String encryptPwd = DigestUtils.sha1Hex(password);

        UserModel userModel = userService.userLogin(username, encryptPwd);
        UserVO userVO = convertFromService(userModel);

        if (userModel != null) {
            String token = onLogin(userModel);
            userVO.setToken(token); // token 仅在用户登录时传一次，后面获取用户状态接口中不重复返回 token 信息
        }
        return CommonReturnType.create(userVO);
    }

    // @ApiOperation(value = "用户注册", notes = "传入用户名，以及密码明文，后台计算密码SHA1值，进行注册")
    // @RequestMapping(value = "register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    // @ResponseBody
    // public CommonReturnType register(@RequestParam(value = "username") String username,
    //                                  @RequestParam(value = "password") String password) {
    //     if (username == null || password == null) {
    //         return null;
    //     }
    //     String encryptPwd = DigestUtils.sha1Hex(password);
    //
    //     UserModel userModel = userService.userRegister(username, encryptPwd);
    //     UserVO userVO = convertFromService(userModel);
    //     return CommonReturnType.create(userVO);
    // }

    @ApiOperation(value = "用户登出", notes = "用户退出登录")
    // @ApiImplicitParams({
    //         @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String")
    // })
    @RequestMapping(value = "logout", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType logout(@RequestParam(value = "token", required = false) String token) throws BusinessException {
        // token 未传入
        if (token == null || "".equals(token)) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "用户令牌未传入");
        }

        onLogout(token);
        return CommonReturnType.create("success");
    }

    @ApiOperation(value = "获取用户登录状态", notes = "获取用户登录状态")
    // @ApiImplicitParams({
    //         @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String")
    // })
    @RequestMapping(value = "getUserStatus", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getUserStatus(@RequestParam(value = "token", required = false) String token) throws BusinessException {
        // token 未传入
        if (token == null || "".equals(token)) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "用户令牌未传入");
        }

        // token 已过期
        Object userIdObject = RedisSessionManager.getInstance(redisTemplate).getValue(token);
        if (userIdObject == null) {
            throw new BusinessException(BusinessErrorCode.USER_TOKEN_EXPIRED, "登陆过期啦，请重新登录");
        }
        Integer userId = (Integer) userIdObject;
        UserModel userModel = userService.getUserById(userId);
        UserVO userVO = convertFromService(userModel);
        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromService(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }
}
