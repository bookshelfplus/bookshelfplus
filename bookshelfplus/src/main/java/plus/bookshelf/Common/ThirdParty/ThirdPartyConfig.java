package plus.bookshelf.Common.ThirdParty;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ThirdPartyConfig {

    // Gitee
    @Value("${thirdparty.gitee.clientid}")
    private String giteeClientId;
    @Value("${thirdparty.gitee.clientsecret}")
    private String giteeClientsecret;
    @Value("${thirdparty.gitee.redirecturi}")
    private String giteeRedirecturi;

    // OSCHINA
    @Value("${thirdparty.oschina.clientid}")
    private String oschinaClientId;
    @Value("${thirdparty.oschina.clientsecret}")
    private String oschinaClientsecret;
    @Value("${thirdparty.oschina.redirecturi}")
    private String oschinaRedirecturi;

    // QQ
    @Value("${thirdparty.qq.clientid}")
    private String qqClientid;
    @Value("${thirdparty.qq.clientsecret}")
    private String qqClientsecret;
    @Value("${thirdparty.qq.redirecturi}")
    private String qqRedirecturi;

    //GitHub
    @Value("${thirdparty.github.clientid}")
    private String githubClientid;
    @Value("${thirdparty.github.clientsecret}")
    private String githubClientsecret;
    @Value("${thirdparty.github.redirecturi}")
    private String githubRedirecturi;
}
