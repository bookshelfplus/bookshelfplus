package plus.bookshelf.Controller.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Config.ThirdPartyConfig;
import plus.bookshelf.Controller.VO.UserVO;
import plus.bookshelf.Service.Impl.ThirdPartyUserServiceImpl;
import plus.bookshelf.Service.Model.ThirdPartyUserModel;
import plus.bookshelf.Service.Model.UserModel;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "第三方登录")
@Controller
@RequestMapping("/third-party")
public class ThirdPartyController extends BaseController {

    // refer document: https://justauth.wiki/guide/
    // Gitee:   https://justauth.wiki/guide/oauth/gitee

    // state在OAuth的流程中的主要作用就是保证请求完整性，防止CSRF风险，此处传的state将在回调时传回

    @Autowired
    private ThirdPartyConfig thirdPartyConfig;

    @Autowired
    ThirdPartyUserServiceImpl thirdPartyUserService;

    @ApiOperation(value = "第三方用户登录跳转地址", notes = "传入需要登录的第三方平台（大小写均可），返回跳转url")
    @RequestMapping(value = "login", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType login(@RequestParam(value = "platform") String platform) throws BusinessException {
        AuthRequest authRequest = getAuthRequest(platform);
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        return CommonReturnType.create(authorizeUrl);
    }

    @ApiOperation(value = "快捷登录回调函数", notes = "如果传入 token 那么就是绑定第三方账号到当前登录账号，否则就是通过第三方授权登录")
    @RequestMapping(value = "callback/{platform}", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType callback(@PathVariable("platform") String platform,
                                     @RequestParam(value = "token", required = false) String token,
                                     AuthCallback callback) throws BusinessException {
        AuthRequest authRequest = getAuthRequest(platform);
        AuthResponse authResponse;
        try {
            authResponse = authRequest.login(callback);
        } catch (AuthException e) {
            // [ERROR] - Failed to login with oauth authorization.
            throw new BusinessException(BusinessErrorCode.THIRD_PARTY_LOGIN_FAIL, "第三方登录失败");
        }
        if (token == null || token.isEmpty()) {
            // 通过第三方授权登录
            UserModel userModel = thirdPartyUserService.loginCallback(authResponse);
            UserVO userVO = UserController.convertFromService(userModel);

            if (userModel != null) {
                String userLoginToken = onLogin(userModel);
                userVO.setToken(userLoginToken); // token 仅在用户登录时传一次，后面获取用户状态接口中不重复返回 token 信息
            }
            return CommonReturnType.create(userVO);
        } else {
            // 绑定第三方账号到当前登录账号
            Boolean isSuccess = thirdPartyUserService.bindThirdPartAccountCallback(authResponse, token);
            return CommonReturnType.create(isSuccess);
        }
    }

    @ApiOperation(value = "【用户|管理员】获取用户已绑定的第三方平台", notes = "传入当前登录用户 token ，返回已绑定的第三方平台")
    @RequestMapping(value = "getBindingStatus", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getBindingStatus(@RequestParam(value = "token", required = false) String token) throws BusinessException, InvocationTargetException, IllegalAccessException {
        List<ThirdPartyUserModel> bindingStatus = thirdPartyUserService.getBindingStatus(token);
        List<String> bindingPlatformList = new ArrayList<>();
        for (ThirdPartyUserModel thirdPartyUserModel : bindingStatus) {
            bindingPlatformList.add(thirdPartyUserModel.getSource());
        }
        return CommonReturnType.create(bindingPlatformList);
    }

    @ApiOperation(value = "【用户|管理员】取消第三方平台绑定", notes = "传入当前登录用户 token 和平台 platform （不区分大小写），返回 bool 值，true 为取消绑定成功")
    @RequestMapping(value = "withdrawThirdPartyBings", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType unbindThirdPartAccount(@RequestParam(value = "token", required = true) String token,
                                                    @RequestParam(value = "platform", required = true) String platform) throws BusinessException {
        if(platform == null || platform.isEmpty()){
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "参数错误，第三方平台不能为空");
        }
        Boolean isSuccess = thirdPartyUserService.unbindThirdPartAccount(token, platform);
        if(!isSuccess){
            throw new BusinessException(BusinessErrorCode.THIRD_PARTY_UNBIND_FAIL, "取消绑定失败，系统错误");
        }
        return CommonReturnType.create("success");
    }

    // 创建授权request
    private AuthRequest getAuthRequest(String platform) throws BusinessException {
        switch (platform.toLowerCase()) {
            case "gitee":
                return new AuthGiteeRequest(AuthConfig.builder()
                        .clientId(thirdPartyConfig.getGiteeClientId())
                        .clientSecret(thirdPartyConfig.getGiteeClientsecret())
                        .redirectUri(thirdPartyConfig.getGiteeRedirecturi())
                        .build());
            case "oschina":
                return new AuthOschinaRequest(AuthConfig.builder()
                        .clientId(thirdPartyConfig.getOschinaClientId())
                        .clientSecret(thirdPartyConfig.getOschinaClientsecret())
                        .redirectUri(thirdPartyConfig.getOschinaRedirecturi())
                        .build());
            case "feishu":
                return new AuthFeishuRequest(AuthConfig.builder()
                        .clientId(thirdPartyConfig.getFeishuClientId())
                        .clientSecret(thirdPartyConfig.getFeishuClientsecret())
                        .redirectUri(thirdPartyConfig.getFeishuRedirecturi())
                        .build());
            case "github":
                return new AuthGithubRequest(AuthConfig.builder()
                        .clientId(thirdPartyConfig.getGithubClientid())
                        .clientSecret(thirdPartyConfig.getGithubClientsecret())
                        .redirectUri(thirdPartyConfig.getGithubRedirecturi())
                        .build());
            case "qq":
                return new AuthQqRequest(AuthConfig.builder()
                        .clientId(thirdPartyConfig.getQqClientid())
                        .clientSecret(thirdPartyConfig.getQqClientsecret())
                        .redirectUri(thirdPartyConfig.getQqRedirecturi())
                        .build());
            default:
                throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "不支持该第三方平台");
        }
    }
}
