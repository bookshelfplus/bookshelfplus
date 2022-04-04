package plus.bookshelf.Service.Service;

import me.zhyd.oauth.model.AuthResponse;
import org.springframework.transaction.annotation.Transactional;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Service.Model.UserModel;

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
}
