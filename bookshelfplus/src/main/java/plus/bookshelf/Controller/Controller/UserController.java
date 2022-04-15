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
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Controller.VO.UserVO;
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

    @ApiOperation(value = "用户注册", notes = "传入用户名，以及密码明文，后台计算密码SHA1值，进行注册")
    @RequestMapping(value = "register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(value = "username") String username,
                                     @RequestParam(value = "password") String password) throws BusinessException {
        if (username == null || password == null) {
            return null;
        }
        String encryptPwd = DigestUtils.sha1Hex(password);

        if (!userService.userRegister(username, encryptPwd)) {
            throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "未知错误，注册失败");
        }
        // 注册成功后，进行登录
        return login(username, password);
    }

    @ApiOperation(value = "用户登出", notes = "用户退出登录")
    // @ApiImplicitParams({
    //         @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String")
    // })
    @RequestMapping(value = "logout", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType logout(@RequestParam(value = "token", required = false) String token) throws BusinessException {
        // // token 未传入
        // if (token == null || "".equals(token)) {
        //     throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "用户令牌未传入");
        // }
        onLogout(token);
        return CommonReturnType.create("success");
    }

    @ApiOperation(value = "获取用户登录状态", notes = "获取用户登录状态")
    // @ApiImplicitParams({
    //         @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String")
    // })
    @RequestMapping(value = "getUserStatus", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getUserStatus(@RequestParam(value = "token", required = false) String token) throws BusinessException {
        // 已经在 getUserByToken 方法中判断了 token 为空、不合法；用户不存在情况，此处无需再判断
        UserModel userModel = userService.getUserByToken(redisTemplate, token);
        UserVO userVO = convertFromService(userModel);
        return CommonReturnType.create(userVO);
    }

    @ApiOperation(value = "账号注销", notes = "传入用户 token ，以及密码明文，后台计算密码SHA1值，进行注销")
    @RequestMapping(value = "cancelAccount", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType cancelAccount(@RequestParam(value = "token", required = false) String token,
                                          @RequestParam(value = "password", required = false) String password) throws BusinessException {
        // 已经在 getUserByToken 方法中判断了 token 为空、不合法；用户不存在情况，此处无需再判断
        UserModel userModel = userService.getUserByToken(redisTemplate, token);

        if(password == null || "".equals(password)){
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "参数不合法，缺少密码");
        }

        // 验证用户密码是否输入正确
        if (!userModel.getEncriptPwd().equals(DigestUtils.sha1Hex(password))) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "密码错误");
        }

        // 进行用户账号注销流程
        Boolean isSuccess = userService.cancelAccount(userModel);
        if (!isSuccess) {
            throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "系统未知错误，注销失败，请联系网站管理员");
        }

        // 用户退出登录
        onLogout(token);

        return CommonReturnType.create("success");
    }

    public static UserVO convertFromService(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }
}
