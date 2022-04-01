package plus.bookshelf.Controller.VO;

import lombok.Data;

@Data
public class UserVO {

    // 用户Id
    Integer id;

    // 用户名
    String username;

    // 用户昵称
    String nickname;

    // 用户身份    NOT_LOGIN, ADMIN, LOGIN_USER;
    String group;

    // 用户头像
    String avatar;

    // 用户手机号
    String phone;

    // 用户 token (NanoID)
    String token;
}
