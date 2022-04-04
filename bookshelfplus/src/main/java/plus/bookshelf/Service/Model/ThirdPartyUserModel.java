package plus.bookshelf.Service.Model;

import lombok.Data;

@Data
public class ThirdPartyUserModel {

    private Integer id;

    private String uuid;

    private String source;

    private String accessToken;

    private Integer expireIn;

    private String refreshToken;

    private String openId;

    private String uid;

    private String accessCode;

    private String unionId;

    private String scope;

    private String tokenType;

    private String idToken;

    private String macAlgorithm;

    private String macKey;

    private String code;

    private String oauthToken;

    private String oauthTokenSecret;
}
