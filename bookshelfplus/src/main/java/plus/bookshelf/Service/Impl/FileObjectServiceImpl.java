package plus.bookshelf.Service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Dao.DO.FileObjectDO;
import plus.bookshelf.Dao.Mapper.FileObjectDOMapper;
import plus.bookshelf.Service.Model.FileObjectModel;
import plus.bookshelf.Service.Model.UserModel;
import plus.bookshelf.Service.Service.FileObjectService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileObjectServiceImpl implements FileObjectService {

    @Autowired
    FileObjectDOMapper fileObjectDOMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserServiceImpl userService;

    /**
     * 列出所有文件对象
     *
     * @return
     */
    @Override
    public List<FileObjectModel> list(String token) throws InvocationTargetException, IllegalAccessException, BusinessException {

        // 已经在 getUserByToken 方法中判断了 token 为空、不合法；用户不存在情况，此处无需再判断
        UserModel userModel = userService.getUserByToken(redisTemplate, token);

        FileObjectDO[] fileObjectDOS = fileObjectDOMapper.selectAll();

        List<FileObjectModel> fileObjectModels = new ArrayList<>();
        for (FileObjectDO fileObjectDO : fileObjectDOS) {
            FileObjectModel fileObjectModel = convertFromDataObject(fileObjectDO);
            fileObjectModels.add(fileObjectModel);
        }

        return fileObjectModels;
    }

    private FileObjectModel convertFromDataObject(FileObjectDO fileObjectDO) throws InvocationTargetException, IllegalAccessException {
        FileObjectModel fileObjectModel = new FileObjectModel();
        BeanUtils.copyProperties(fileObjectDO, fileObjectModel);
        return fileObjectModel;
    }
}
