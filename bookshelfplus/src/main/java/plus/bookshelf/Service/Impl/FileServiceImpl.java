package plus.bookshelf.Service.Impl;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import plus.bookshelf.Dao.DO.FileDO;
import plus.bookshelf.Dao.Mapper.FileDOMapper;
import plus.bookshelf.Service.Model.FileModel;
import plus.bookshelf.Service.Model.UserModel;
import plus.bookshelf.Service.Service.FileService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileDOMapper fileDOMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserServiceImpl userService;


    // @Autowired
    // ScheduleTaskServiceImpl scheduleTaskService;

    /**
     * 列出所有文件
     *
     * @return
     */
    @SneakyThrows
    @Override
    public List<FileModel> list(String token) throws InvocationTargetException, IllegalAccessException {

        // 已经在 getUserByToken 方法中判断了 token 为空、不合法；用户不存在情况，此处无需再判断
        UserModel userModel = userService.getUserByToken(redisTemplate, token);

        FileDO[] fileDOS = fileDOMapper.selectAll();

        List<FileModel> fileModels = new ArrayList<>();
        for (FileDO fileDO : fileDOS) {
            FileModel fileModel = convertFromDataObject(fileDO);
            fileModels.add(fileModel);
        }

        return fileModels;
    }

    private FileModel convertFromDataObject(FileDO fileDO) throws InvocationTargetException, IllegalAccessException {
        FileModel fileModel = new FileModel();
        BeanUtils.copyProperties(fileDO, fileModel);
        return fileModel;
    }

    // /**
    //  * 向数据库中添加一个 scheduleTask
    //  *
    //  * @param expireMinute
    //  * @param fileName
    //  * @param urlGUID
    //  * @param userId
    //  */
    // @Override
    // public void addScheduleTask(Integer expireMinute, String fileName, String urlGUID, Integer userId) {
    //     ScheduleTaskModel scheduleTaskModel = new ScheduleTaskModel();
    //     Calendar now = Calendar.getInstance();
    //     scheduleTaskModel.setCreateTime(now.getTime());
    //     now.add(Calendar.MILLISECOND, expireMinute * 60 * 1000);
    //     scheduleTaskModel.setScheduleTime(now.getTime());
    //     scheduleTaskModel.setAction(ScheduleTaskActionEnum.CHECK_FILE_IS_UPLOADED);
    //     scheduleTaskModel.setData(fileName);
    //     scheduleTaskModel.setTaskGuid(urlGUID);
    //     scheduleTaskModel.setAssociatedUserId(userId);
    //     scheduleTaskModel.setFailTime((byte) 0);
    //     scheduleTaskService.insertScheduleTask(scheduleTaskModel);
    // }
}
