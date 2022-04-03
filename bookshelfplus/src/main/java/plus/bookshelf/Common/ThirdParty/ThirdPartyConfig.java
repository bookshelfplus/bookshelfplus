package plus.bookshelf.Common.ThirdParty;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "thirdparty")
@PropertySource("thirdparty.properties")
@Data
public class ThirdPartyConfig {

    // Gitee
    @Value("${thirdparty.gitee.clientid}")
    private String giteeClientId;
    @Value("${thirdparty.gitee.clientsecret}")
    private String giteeClientsecret;
    @Value("${thirdparty.gitee.redirecturi}")
    private String giteeRedirecturi;

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
