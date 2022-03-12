package plus.bookshelf.Controller.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import plus.bookshelf.Controller.VO.UserVO;
import plus.bookshelf.Service.Impl.UserServiceImpl;
import plus.bookshelf.Service.Model.UserModel;

import static plus.bookshelf.Controller.Controller.BaseController.CONTENT_TYPE_FORMED;

@Api(value = "用户")
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @ApiOperation(value = "用户登录",notes = "传入用户名，以及密码的MD5值，进行登录")
    @RequestMapping(value = "login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public UserVO login(@RequestParam(value = "username") String username,
                        @RequestParam(value = "encryptpwd") String encryptPwd) {
        UserModel userModel = userService.userLogin(username, encryptPwd);
        UserVO userVO = convertFromService(userModel);
        return userVO;
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
