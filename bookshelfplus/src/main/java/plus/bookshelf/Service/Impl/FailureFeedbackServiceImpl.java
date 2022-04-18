package plus.bookshelf.Service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.bookshelf.Dao.DO.FailureFeedbackDO;
import plus.bookshelf.Dao.Mapper.FailureFeedbackDOMapper;
import plus.bookshelf.Service.Model.FailureFeedbackModel;
import plus.bookshelf.Service.Service.FailureFeedbackService;

@Service
public class FailureFeedbackServiceImpl implements FailureFeedbackService {

    @Autowired
    FailureFeedbackDOMapper failureFeedbackDOMapper;

    /**
     * 添加反馈
     *
     * @return
     */
    @Override
    public Boolean addFailureFeedback(FailureFeedbackModel failureFeedbackModel) {
        FailureFeedbackDO failureFeedbackDO = convertToDataObject(failureFeedbackModel);
        int affectRows = failureFeedbackDOMapper.insertSelective(failureFeedbackDO);
        return affectRows > 0;
    }

    private FailureFeedbackDO convertToDataObject(FailureFeedbackModel failureFeedbackModel) {
        if (failureFeedbackModel == null) {
            return null;
        }
        FailureFeedbackDO failureFeedbackDO = new FailureFeedbackDO();
        BeanUtils.copyProperties(failureFeedbackModel, failureFeedbackDO);
        return failureFeedbackDO;
    }
}
