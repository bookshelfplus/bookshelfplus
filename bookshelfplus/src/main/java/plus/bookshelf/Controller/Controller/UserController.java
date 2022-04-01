package plus.bookshelf.Controller.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Common.SessionManager.LocalSessionManager;
import plus.bookshelf.Controller.VO.UserVO;
import plus.bookshelf.Service.Impl.UserServiceImpl;
import plus.bookshelf.Service.Model.UserModel;

@Api(value = "用户")
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    UserServiceImpl userService;

    @ApiOperation(value = "用户登录", notes = "传入用户名，以及密码明文，后台计算密码SHA1值，进行登录")
    @RequestMapping(value = "login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(value = "username") String username,
                                  @RequestParam(value = "password") String password) {
        if (username == null || password == null) {
            return null;
        }
        String encryptPwd = DigestUtils.sha1Hex(password);

        UserModel userModel = userService.userLogin(username, encryptPwd);
        UserVO userVO = convertFromService(userModel);

        if (userModel != null) {
            onLogin(userModel);
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
    @RequestMapping(value = "logout", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType logout() {
        onLogout();
        return CommonReturnType.create("success");
    }

    @ApiOperation(value = "获取用户登录状态", notes = "获取用户登录状态")
    @RequestMapping(value = "getUserStatus", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getUserStatus() {
        Object userModelObject = LocalSessionManager.getInstance(httpServletRequest).getValue("user");
        if (userModelObject == null) {
            return CommonReturnType.create(null);
        }

        UserModel userModel = (UserModel) userModelObject;
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
