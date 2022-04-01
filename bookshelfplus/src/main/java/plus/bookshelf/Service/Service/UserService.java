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

    /**
     * 通过用户Id获取用户
     * @param id 用户Id
     * @return
     */
    UserModel getUserById(Integer id);
}
