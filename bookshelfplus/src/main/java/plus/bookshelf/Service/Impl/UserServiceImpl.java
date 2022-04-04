package plus.bookshelf.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.SessionManager.RedisSessionManager;
import plus.bookshelf.Controller.VO.UserVO;
import plus.bookshelf.Dao.DO.ThirdPartyUserDO;
import plus.bookshelf.Dao.DO.UserDO;
import plus.bookshelf.Dao.Mapper.UserDOMapper;
import plus.bookshelf.Service.Model.UserModel;
import plus.bookshelf.Service.Service.UserService;

import java.util.Map;

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

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        UserModel userModel = convertFromDataObject(userDO);

        return userModel;
    }

    @Override
    public UserModel getUserByToken(RedisTemplate redisTemplate, String token) throws BusinessException {
        // token 未传入
        if (token == null || "".equals(token)) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "用户令牌未传入");
        }

        // token 已过期
        Object userIdObject = RedisSessionManager.getInstance(redisTemplate).getValue(token);
        if (userIdObject == null) {
            throw new BusinessException(BusinessErrorCode.USER_TOKEN_EXPIRED, "登陆过期啦，请重新登录");
        }

        Integer userId = (Integer) userIdObject;
        UserModel userModel = getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(BusinessErrorCode.USER_NOT_EXIST);
        }
        return userModel;
    }

    @Override
    @Transactional
    public Boolean userRegister(String username, String encryptPwd) throws BusinessException {
        if (username == null || "".equals(username)) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "用户名不能为空");
        } else if (encryptPwd == null || "".equals(encryptPwd)) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "密码不能为空");
        }
        Integer count = userDOMapper.selectCountByUsername(username);
        if (count > 0) {
            throw new BusinessException(BusinessErrorCode.USER_ALREADY_EXIST, "用户已存在");
        }
        UserDO userDO = new UserDO();
        userDO.setUsername(username);
        userDO.setEncriptPwd(encryptPwd);
        userDO.setGroup("USER");
        userDO.setNickname("该用户尚未设置昵称");
        return userDOMapper.insertSelective(userDO) > 0;
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
        userModel.setGroup(userDO.getGroup());
        userModel.setAvatar(userDO.getAvatar());
        userModel.setPhone(userDO.getPhone());
        userModel.setWeixinThirdPartyAuthCode(userDO.getWeixinThirdPartyAuthCode());
        userModel.setQqThirdPartyAuthCode(userDO.getQqThirdPartyAuthCode());

        return userModel;
    }
}
