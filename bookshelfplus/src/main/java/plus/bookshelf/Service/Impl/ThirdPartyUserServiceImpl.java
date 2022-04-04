package plus.bookshelf.Service.Impl;

import me.zhyd.oauth.enums.AuthUserGender;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

@Service
public class ThirdPartyUserServiceImpl implements ThirdPartyUserService {

    @Autowired
    ThirdPartyUserDOMapper thirdPartyUserDOMapper;

    @Autowired
    ThirdPartyUserAuthDOMapper thirdPartyUserAuthDOMapper;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 第三方登录
     *
     * @param authResponse
     * @return
     * @throws BusinessException
     */
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

                // 检查第三方账号有无绑定到系统账号
                ThirdPartyUserAuthDO thirdPartyUserAuthDO = thirdPartyUserAuthDOMapper.selectByThirdPartyUserId(existThirdPartyUserDO.getId());
                if (thirdPartyUserAuthDO != null) {
                    // 已经绑定到系统账号

                    // 更新数据库中的第三方登录信息
                    currentThirdPartyUserDO.setId(existThirdPartyUserDO.getId());
                    thirdPartyUserDOMapper.updateByPrimaryKeySelective(currentThirdPartyUserDO);

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
                throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "第三方登录失败，该第三方账号未绑定到系统账号，请先绑定");
            }
        } else {
            // 未知错误
            throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "未知错误，登录失败");
        }
    }

    @Override
    @Transactional
    public Boolean bindThirdPartAccountCallback(AuthResponse authResponse, String token) throws BusinessException {
        int code = authResponse.getCode();
        if (code == 5008) {
            // 第三方平台拒绝授权
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "绑定失败，用户已取消第三方授权或第三方平台拒绝授权");
        } else if (code == 5009) {
            // 授权过期
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "第三方授权过期，请尝试重新绑定");
        }
        if (code == 2000) {
            // 回调成功
            // 将回调结果转换为 Data Object
            ThirdPartyUserDO currentThirdPartyUserDO = getThirdPartyUserDOFromAuthData(authResponse.getData());

            // 根据 uuid + source 唯一确定一个平台的用户 refer: https://justauth.wiki/features/integrate-existing-systems/
            String uuid = currentThirdPartyUserDO.getUuid();
            String source = currentThirdPartyUserDO.getSource();
            ThirdPartyUserDO existThirdPartyUserDO = thirdPartyUserDOMapper.selectByUuidAndSource(uuid, source);

            if (existThirdPartyUserDO == null) {
                // 之前未授权过

                // 获取当前登录用户信息
                UserModel userModel = userService.getUserByToken(redisTemplate, token);
                if (userModel == null) {
                    throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "绑定失败，用户未登录");
                }

                // 将用户账号与第三方账号信息插入到数据库
                int affectRows = thirdPartyUserDOMapper.insertSelective(currentThirdPartyUserDO);

                // 判断是否插入成功
                if (affectRows > 0) {
                    // 用户第三方账号保存到数据库表中的主键id
                    Integer lastInsertId = thirdPartyUserDOMapper.getLastInsertId();

                    // 在 Auth 表中建立 用户 和第三方授权的联系
                    ThirdPartyUserAuthDO thirdPartyUserAuthDO = new ThirdPartyUserAuthDO();
                    thirdPartyUserAuthDO.setThirdPartyUserId(lastInsertId);
                    thirdPartyUserAuthDO.setUserId(userModel.getId());
                    int affectRows2 = thirdPartyUserAuthDOMapper.insert(thirdPartyUserAuthDO);
                    // 判断是否插入成功
                    if (affectRows2 > 0) {
                        // 绑定成功
                        return true;
                    } else {
                        throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "绑定失败，系统错误");
                    }
                } else {
                    // 第三方账号信息插入失败
                    throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "绑定失败，系统错误");
                }
            } else {
                // 之前已经授权过
                throw new BusinessException(BusinessErrorCode.THIRD_PARTY_ACCOUNT_ALREADY_BOUND, "绑定失败，该账号已被其他账号绑定");
            }
        } else {
            // 未知错误
            throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "未知错误，绑定失败");
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
