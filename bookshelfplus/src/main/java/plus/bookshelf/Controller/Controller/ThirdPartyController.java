package plus.bookshelf.Controller.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Common.ThirdParty.ThirdPartyConfig;
import plus.bookshelf.Controller.VO.UserVO;
import plus.bookshelf.Dao.DO.ThirdPartyUserAuthDO;
import plus.bookshelf.Dao.DO.ThirdPartyUserDO;
import plus.bookshelf.Dao.Mapper.ThirdPartyUserAuthDOMapper;
import plus.bookshelf.Dao.Mapper.ThirdPartyUserDOMapper;
import plus.bookshelf.Service.Impl.ThirdPartyUserServiceImpl;
import plus.bookshelf.Service.Model.UserModel;

import java.util.Map;
import java.util.Objects;

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

    // 创建授权request
    private AuthRequest getAuthRequest(String platform) throws BusinessException {
        switch (platform.toLowerCase()) {
            case "gitee":
                return new AuthGiteeRequest(AuthConfig.builder()
                        .clientId(thirdPartyConfig.getGiteeClientId())
                        .clientSecret(thirdPartyConfig.getGiteeClientsecret())
                        .redirectUri(thirdPartyConfig.getGiteeRedirecturi())
                        .build());
            case "qq":
                return new AuthGiteeRequest(AuthConfig.builder()
                        .clientId(thirdPartyConfig.getQqClientid())
                        .clientSecret(thirdPartyConfig.getQqClientsecret())
                        .redirectUri(thirdPartyConfig.getQqRedirecturi())
                        .build());
            default:
                throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "不支持该第三方平台");
        }
    }
}
