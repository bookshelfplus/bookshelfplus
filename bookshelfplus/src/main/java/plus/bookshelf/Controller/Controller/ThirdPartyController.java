package plus.bookshelf.Controller.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Common.ThirdParty.ThirdPartyConfig;
import plus.bookshelf.Service.Impl.UserServiceImpl;

import java.util.Map;

@Api(tags = "第三方登录")
@Controller
@RequestMapping("/third-party")
public class ThirdPartyController extends BaseController {

    // refer document: https://justauth.wiki/guide/
    // Gitee:   https://justauth.wiki/guide/oauth/gitee

    // state在OAuth的流程中的主要作用就是保证请求完整性，防止CSRF风险，此处传的state将在回调时传回

    @Autowired
    private ThirdPartyConfig thirdPartyConfig;

    @ApiOperation(value = "第三方用户登录跳转地址", notes = "传入需要登录的第三方平台（大小写均可），返回跳转url")
    @RequestMapping(value = "login", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType login(@RequestParam(value = "platform") String platform) throws BusinessException {
        AuthRequest authRequest = getAuthRequest(platform);
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        return CommonReturnType.create(authorizeUrl);
    }

    @ApiOperation(value = "快捷登录回调函数", notes = "传入 code 值，进行登录")
    @RequestMapping(value = "callback/{platform}", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType qq(@PathVariable("platform") String platform,
                               // @RequestParam Map<String,String> params,
                               AuthCallback callback) throws BusinessException {
        // System.out.println(params);
        // System.out.println(platform);
        // System.out.println(params.get("code"));
        // System.out.println(params.get("state"));
        AuthRequest authRequest = getAuthRequest(platform);
        return CommonReturnType.create(authRequest.login(callback));
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