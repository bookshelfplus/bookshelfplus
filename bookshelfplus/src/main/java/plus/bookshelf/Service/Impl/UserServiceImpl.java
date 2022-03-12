package plus.bookshelf.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.bookshelf.Dao.DO.UserDO;
import plus.bookshelf.Dao.Mapper.UserDOMapper;
import plus.bookshelf.Service.Model.UserModel;
import plus.bookshelf.Service.Service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDOMapper userDOMapper;

    @Override
    public UserModel userLogin(String username, String encryptPwd) {
        UserDO userDO = userDOMapper.selectByUsernameAndEncryptpwd(username, encryptPwd);
        UserModel userModel = convertFromDataObject(userDO);

        return userModel;
    }

    private UserModel convertFromDataObject(UserDO userDO) {
        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        userModel.setId(userDO.getId());
        userModel.setUsername(userDO.getUsername());
        userModel.setEncriptPwd(userDO.getEncriptPwd());
        userModel.setNickname(userDO.getNickname());
        userModel.setUserIdentity(userDO.getUserIdentity());
        userModel.setAvatar(userDO.getAvatar());
        userModel.setPhone(userDO.getPhone());
        userModel.setWeixinThirdPartyAuthCode(userDO.getWeixinThirdPartyAuthCode());
        userModel.setQqThirdPartyAuthCode(userDO.getQqThirdPartyAuthCode());

        return userModel;
    }
}
