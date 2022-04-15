package plus.bookshelf.Service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Config.QCloudCosConfig;
import plus.bookshelf.Dao.DO.FileDO;
import plus.bookshelf.Dao.Mapper.FileDOMapper;
import plus.bookshelf.Dao.Mapper.FileObjectDOMapper;
import plus.bookshelf.Service.Model.FileModel;
import plus.bookshelf.Service.Model.UserModel;
import plus.bookshelf.Service.Service.CosPresignedUrlGenerateLogService;
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

    @Autowired
    FileObjectDOMapper fileObjectDOMapper;

    @Autowired
    QCloudCosConfig qCloudCosConfig;

    @Autowired
    CosPresignedUrlGenerateLogService cosPresignedUrlGenerateLogService;

    /**
     * 列出所有文件
     *
     * @return
     */
    @Override
    public List<FileModel> list(String token) throws InvocationTargetException, IllegalAccessException, BusinessException {

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

    private FileModel convertFromDataObject(FileDO fileDO) {
        if(fileDO == null) {
            return null;
        }
        FileModel fileModel = new FileModel();
        BeanUtils.copyProperties(fileDO, fileModel);
        return fileModel;
    }

    /**
     * 添加文件信息
     * 返回是否添加成功
     *
     * @param fileModel
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public Boolean addFile(FileModel fileModel) throws InvocationTargetException, IllegalAccessException {
        FileDO fileDO = copyFileToDataObject(fileModel);
        int affectRows = fileDOMapper.insertSelective(fileDO);
        return affectRows > 0;
    }

    private FileDO copyFileToDataObject(FileModel fileModel) throws InvocationTargetException, IllegalAccessException {
        FileDO fileDO = new FileDO();
        BeanUtils.copyProperties(fileModel, fileDO);
        return fileDO;
    }

    /**
     * 获取上一步添加的文件Id
     *
     * @return
     */
    @Override
    public Integer getLastInsertId() {
        return fileDOMapper.getLastInsertId();
    }
}
