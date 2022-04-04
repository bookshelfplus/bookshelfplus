package plus.bookshelf.Service.Service;

import me.zhyd.oauth.model.AuthResponse;
import org.springframework.transaction.annotation.Transactional;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Service.Model.ThirdPartyUserModel;
import plus.bookshelf.Service.Model.UserModel;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ThirdPartyUserService {

    /**
     * 第三方登录完成回调函数
     * @param authResponse
     * @throws BusinessException
     * @return
     */
    @Transactional
    UserModel loginCallback(AuthResponse authResponse) throws BusinessException;


    /**
     * 个人账号中心绑定第三方账号回调函数
     * @param authResponse
     * @throws BusinessException
     * @return
     */
    @Transactional
    Boolean bindThirdPartAccountCallback(AuthResponse authResponse, String token) throws BusinessException;

    /**
     * 获取用户登录的所有第三方平台信息
     * @param token
     * @return
     */
    List<ThirdPartyUserModel> getBindingStatus(String token) throws BusinessException, InvocationTargetException, IllegalAccessException;

    /**
     * 解绑第三方账号
     * @param token 用户token
     * @param platform 第三方平台
     * @return
     */
    @Transactional
    Boolean unbindThirdPartAccount(String token, String platform) throws BusinessException;
}
