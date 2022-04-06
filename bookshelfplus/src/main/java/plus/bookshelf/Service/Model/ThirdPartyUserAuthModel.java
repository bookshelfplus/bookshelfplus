package plus.bookshelf.Service.Model;

import lombok.Data;

@Data
public class ThirdPartyUserAuthModel {

    private Integer id;

    // 用户 Id
    private String userId;

    // 用户第三方授权 Id
    private String thirdPartyUserId;
}
