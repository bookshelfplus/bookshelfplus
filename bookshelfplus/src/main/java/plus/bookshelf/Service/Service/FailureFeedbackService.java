package plus.bookshelf.Service.Service;

import plus.bookshelf.Service.Model.FailureFeedbackModel;

public interface FailureFeedbackService {

    /**
     * 添加反馈
     *
     * @param failureFeedbackModel
     * @return
     */
    Boolean addFailureFeedback(FailureFeedbackModel failureFeedbackModel);
}
