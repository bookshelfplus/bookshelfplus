package plus.bookshelf.Service.Model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserModel {

    // 用户Id
    Integer id;

    // 用户名
    @NotBlank(message = "用户名不能为空")
    String username;

    // 用户加密后的密码
    String encriptPwd;

    // 用户昵称
    String nickname;

    // 用户身份    NOT_LOGIN, ADMIN, USER;
    String group;

    // 用户头像
    String avatar;

    // 用户手机号
    String phone;

    // 微信第三方登录授权
    String weixinThirdPartyAuthCode;

    // QQ第三方登录授权
    String qqThirdPartyAuthCode;
}
