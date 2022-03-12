package plus.bookshelf.Service.Service;

import plus.bookshelf.Service.Model.UserModel;

public interface UserService {
    /**
     * 用户登录
     *
     * @param username
     * @param encryptPwd
     */
    UserModel userLogin(String username, String encryptPwd);
}
