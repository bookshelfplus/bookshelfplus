package plus.bookshelf.Service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.bookshelf.Common.Enum.ScheduleTaskActionEnum;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Config.QCloudCosConfig;
import plus.bookshelf.Dao.DO.ScheduleTaskDO;
import plus.bookshelf.Dao.Mapper.ScheduleTaskDOMapper;
import plus.bookshelf.Service.Model.ScheduleTaskModel;
import plus.bookshelf.Service.Service.CosPresignedUrlGenerateLogService;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static plus.bookshelf.Common.Enum.ScheduleTaskActionEnum.*;

@Service
public class ScheduleTaskServiceImpl {

    @Autowired
    QCloudCosConfig qCloudCosConfig;

    @Autowired
    CosPresignedUrlGenerateLogService cosPresignedUrlGenerateLogService;

    @Autowired
    ScheduleTaskDOMapper scheduleTaskDOMapper;

    @Autowired
    FileServiceImpl fileService;

    public void setExecutor() {
        // 创建定时任务 （主要是待用户将文件上传成功到COS后）
        java.util.concurrent.ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 参数：1、任务体 2、首次执行的延时时间
        //      3、任务执行间隔 4、间隔时间单位
        service.scheduleAtFixedRate(() -> {
            try {
                thingsTodo();
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    @Transactional
    public void thingsTodo() throws BusinessException {

        // 检查上传文件后未收到客户端发来的上传完成消息的情况。
        // 如果系统里面没有记录，那么删除COS存储桶中用户上传的这个文件。

        ScheduleTaskDO[] scheduleTaskDO = scheduleTaskDOMapper.selectTodoTask();

        for (ScheduleTaskDO task : scheduleTaskDO) {
            ScheduleTaskModel scheduleTaskModel = convertToModel(task);
            switch (scheduleTaskModel.getAction()) {
                case CHECK_FILE_IS_UPLOADED:
                    fileService.doScheduleTask(scheduleTaskModel);
                    break;
                default:
                    throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "未知的定时任务类型");
            }
        }

        // 输出
        System.out.println("Task ScheduledExecutorService " + new Date());
    }

    /**
     * 创建一个新的定时任务（存入数据库）
     *
     * @param scheduleTaskModel
     * @return
     */
    public Boolean insertScheduleTask(ScheduleTaskModel scheduleTaskModel) {
        ScheduleTaskDO scheduleTaskDO = convertToDataObject(scheduleTaskModel);
        int affectRows = scheduleTaskDOMapper.insertSelective(scheduleTaskDO);
        return affectRows > 0;
    }

    /**
     * 完成一个定时任务（从数据库中删除）
     * 注意先删除，删除成功后才进行处理，避免自动触发和手动触发同时进行
     *
     * @param scheduleTaskId
     * @return
     */
    public Boolean doneScheduleTask(Integer scheduleTaskId) {
        int affectRows = scheduleTaskDOMapper.deleteByPrimaryKey(scheduleTaskId);
        return affectRows > 0;
    }

    /**
     * 通过 Guid 查询任务
     *
     * @param guid
     * @return
     */
    public ScheduleTaskModel findTaskByGuid(String guid) {
        ScheduleTaskDO scheduleTaskDO = scheduleTaskDOMapper.selectByGuid(guid);
        return convertToModel(scheduleTaskDO);
    }

    private static ScheduleTaskDO convertToDataObject(ScheduleTaskModel scheduleTaskModel) {
        ScheduleTaskDO scheduleTaskDO = new ScheduleTaskDO();
        BeanUtils.copyProperties(scheduleTaskModel, scheduleTaskDO);
        scheduleTaskDO.setAction(String.valueOf(scheduleTaskModel.getAction()));
        return scheduleTaskDO;
    }

    private static ScheduleTaskModel convertToModel(ScheduleTaskDO scheduleTaskDO) {
        ScheduleTaskModel scheduleTaskModel = new ScheduleTaskModel();
        BeanUtils.copyProperties(scheduleTaskDO, scheduleTaskModel);
        scheduleTaskModel.setAction(ScheduleTaskActionEnum.valueOf(scheduleTaskDO.getAction()));
        return scheduleTaskModel;
    }
}
