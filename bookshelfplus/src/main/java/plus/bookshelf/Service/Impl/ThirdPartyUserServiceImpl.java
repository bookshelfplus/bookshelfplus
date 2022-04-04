package plus.bookshelf.Service.Impl;

import me.zhyd.oauth.enums.AuthUserGender;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Dao.DO.ThirdPartyUserAuthDO;
import plus.bookshelf.Dao.DO.ThirdPartyUserDO;
import plus.bookshelf.Dao.Mapper.ThirdPartyUserAuthDOMapper;
import plus.bookshelf.Dao.Mapper.ThirdPartyUserDOMapper;
import plus.bookshelf.Service.Model.UserModel;
import plus.bookshelf.Service.Service.ThirdPartyUserService;

import java.util.Map;

@Service
public class ThirdPartyUserServiceImpl implements ThirdPartyUserService {

    @Autowired
    ThirdPartyUserDOMapper thirdPartyUserDOMapper;

    @Autowired
    ThirdPartyUserAuthDOMapper thirdPartyUserAuthDOMapper;

    @Autowired
    UserServiceImpl userService;

    @Override
    @Transactional
    public UserModel loginCallback(AuthResponse authResponse) throws BusinessException {
        int code = authResponse.getCode();
        if (code == 5008) {
            // 第三方平台拒绝授权
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "第三方登录失败，用户已取消第三方登录或第三方平台拒绝授权");
        } else if (code == 5009) {
            // 授权过期
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "第三方授权过期，请重新登录");
        }
        if (code == 2000) {
            // 回调成功
            // 将回调结果转换为 Data Object
            ThirdPartyUserDO currentThirdPartyUserDO = getThirdPartyUserDOFromAuthData(authResponse.getData());

            // 根据 uuid + source 唯一确定一个平台的用户 refer: https://justauth.wiki/features/integrate-existing-systems/
            String uuid = currentThirdPartyUserDO.getUuid();
            String source = currentThirdPartyUserDO.getSource();
            ThirdPartyUserDO existThirdPartyUserDO = thirdPartyUserDOMapper.selectByUuidAndSource(uuid, source);

            if (existThirdPartyUserDO != null) {
                // 之前已经授权登录过
                // 更新数据库中的第三方登录信息
                currentThirdPartyUserDO.setId(existThirdPartyUserDO.getId());
                thirdPartyUserDOMapper.updateByPrimaryKeySelective(currentThirdPartyUserDO);

                // 检查第三方账号有无绑定到系统账号
                ThirdPartyUserAuthDO thirdPartyUserAuthDO = thirdPartyUserAuthDOMapper.selectByThirdPartyUserId(currentThirdPartyUserDO.getId());
                if (thirdPartyUserAuthDO != null) {
                    // 已经绑定到系统账号
                    // 取得用户信息，并登录
                    Integer userId = thirdPartyUserAuthDO.getUserId();
                    UserModel userModel = userService.getUserById(userId);
                    return userModel;
                } else {
                    // 未绑定到系统账号
                    throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "第三方登录失败，该第三方账号未绑定到系统账号，请先绑定");
                }
            } else {
                // 之前未授权登录过
                // 将新的用户信息插入到数据库
                thirdPartyUserDOMapper.insertSelective(currentThirdPartyUserDO);
                throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "第三方登录失败，该第三方账号未绑定到系统账号，请先绑定");
            }
        } else {
            // 未知错误
            throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "未知错误，登录失败");
        }
    }

    private ThirdPartyUserDO getThirdPartyUserDOFromAuthData(Object authData) {
        AuthUser data = (AuthUser) authData;
        String uuid = data.getUuid();
        String username = data.getUsername();
        String nickname = data.getNickname();
        String avatar = data.getAvatar();
        String blog = data.getBlog();
        String company = data.getCompany();
        String location = data.getLocation();
        String email = data.getEmail();
        String remark = data.getRemark();
        AuthUserGender gender = data.getGender();
        String source = data.getSource();

        AuthToken token = data.getToken();

        String accessToken = token.getAccessToken();
        int expiresIn = token.getExpireIn();
        String refreshToken = token.getRefreshToken();
        int refreshTokenExpireIn = token.getRefreshTokenExpireIn();
        String uid = token.getUid();
        String openId = token.getOpenId();
        String accessCode = token.getAccessCode();
        String unionId = token.getUnionId();
        String scope = token.getScope();
        String tokenType = token.getTokenType();
        String idToken = token.getIdToken();
        String macAlgorithm = token.getMacAlgorithm();
        String macKey = token.getMacKey();
        String code = token.getCode();
        String oauthToken = token.getOauthToken();
        String oauthTokenSecret = token.getOauthTokenSecret();
        String userId = token.getUserId();
        String screenName = token.getScreenName();
        String oauthCallbackConfirmed = token.getOauthToken();

        // String uuid = data.get("uuid").toString();
        // String username = data.get("username").toString();
        // String nickname = data.get("nickname").toString();
        // String avatar = data.get("avatar").toString();
        // String blog = data.get("blog").toString();
        // String company = data.get("company").toString();
        // String location = data.get("location").toString();
        // String email = data.get("email").toString();
        // String remark = data.get("remark").toString();
        // String gender = data.get("gender").toString();
        // String source = data.get("source").toString();

        // Map token = (Map) data.get("token");
        // String accessToken = token.get("accessToken").toString();
        // int expiresIn = Integer.parseInt(token.get("expiresIn").toString());
        // String refreshToken = token.get("refreshToken").toString();
        // int refreshTokenExpireIn = Integer.parseInt(token.get("refreshTokenExpireIn").toString());
        // String uid = token.get("uid").toString();
        // String openId = token.get("openId").toString();
        // String accessCode = token.get("accessCode").toString();
        // String unionId = token.get("unionId").toString();
        // String scope = token.get("scope").toString();
        // String tokenType = token.get("tokenType").toString();// token 类型，默认为 bearer
        // String idToken = token.get("idToken").toString();// id_token
        // String macAlgorithm = token.get("macAlgorithm").toString();// mac 算法
        // String macKey = token.get("macKey").toString();// mac 密钥
        // String code = token.get("code").toString();// code
        // String oauthToken = token.get("oauthToken").toString();// oauth_token
        // String oauthTokenSecret = token.get("oauthTokenSecret").toString();// oauth_token_secret
        // String userId = token.get("userId").toString();// user_id
        // String screenName = token.get("screenName").toString();// screen_name
        // String oauthCallbackConfirmed = token.get("oauthCallbackConfirmed").toString();// oauth_callback_confirmed

        ThirdPartyUserDO thirdPartyUserDO = new ThirdPartyUserDO();
        thirdPartyUserDO.setUuid(uuid);
        thirdPartyUserDO.setSource(source);
        thirdPartyUserDO.setAccessToken(accessToken);
        thirdPartyUserDO.setExpireIn(expiresIn);
        thirdPartyUserDO.setRefreshToken(refreshToken);
        thirdPartyUserDO.setOpenId(openId);
        thirdPartyUserDO.setUid(uid);
        thirdPartyUserDO.setAccessCode(accessCode);
        thirdPartyUserDO.setUnionId(unionId);
        thirdPartyUserDO.setScope(scope);
        thirdPartyUserDO.setTokenType(tokenType);
        thirdPartyUserDO.setIdToken(idToken);
        thirdPartyUserDO.setMacAlgorithm(macAlgorithm);
        thirdPartyUserDO.setMacKey(macKey);
        thirdPartyUserDO.setCode(code);
        thirdPartyUserDO.setOauthToken(oauthToken);
        thirdPartyUserDO.setOauthTokenSecret(oauthTokenSecret);

        return thirdPartyUserDO;
    }
}
