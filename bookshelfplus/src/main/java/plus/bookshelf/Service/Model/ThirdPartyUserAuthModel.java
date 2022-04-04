package plus.bookshelf.Service.Model;

import lombok.Data;

@Data
public class ThirdPartyUserAuthModel {

    private Integer id;

    private String userId;

    private String thirdPartyUserId;
}
