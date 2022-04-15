package plus.bookshelf.Controller.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Config.ThirdPartyConfig;
import plus.bookshelf.Service.Impl.UserServiceImpl;
import plus.bookshelf.Service.Model.UserModel;

import java.util.Objects;

@Api(tags = "系统调试接口")
@Controller("system")
@RequestMapping("/system")
public class SystemController extends BaseController {

    @Autowired
    ThirdPartyConfig thirdPartyConfig;

    @Autowired
    UserServiceImpl userService;

    @ApiOperation(value = "【管理员】获取系统配置", notes = "仅限管理员登录状态下可获取")
    @RequestMapping(value = "status", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType status(@RequestParam(value = "token", required = false) String token) throws BusinessException {
        UserModel userModel = userService.getUserByToken(redisTemplate, token);
        if (userModel == null || !Objects.equals(userModel.getGroup(), "ADMIN")) {
            throw new BusinessException(BusinessErrorCode.OPERATION_NOT_ALLOWED, "非管理员用户无权进行此操作");
        }
        return CommonReturnType.create(thirdPartyConfig);
    }
}
