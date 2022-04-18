package plus.bookshelf.Controller.VO;

import lombok.Data;

import java.util.Date;

@Data
public class FailureFeedbackVO {

    private Integer bookId;

    private Integer fileId;

    private Integer fileObjectId;

    private Integer userId;

    private Date createTime;
}
