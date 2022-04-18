package plus.bookshelf.Service.Model;

import lombok.Data;

import java.util.Date;

@Data
public class FailureFeedbackModel {

    private Integer bookId;

    private Integer fileId;

    private Integer fileObjectId;

    private Integer userId;

    private Date createTime;
}
