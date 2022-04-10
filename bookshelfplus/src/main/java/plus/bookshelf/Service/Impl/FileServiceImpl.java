package plus.bookshelf.Service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.bookshelf.Dao.DO.FileDO;
import plus.bookshelf.Dao.Mapper.FileDOMapper;
import plus.bookshelf.Service.Model.FileModel;
import plus.bookshelf.Service.Service.FileService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileDOMapper fileDOMapper;

    /**
     * 列出所有文件
     *
     * @return
     */
    @Override
    public List<FileModel> list() throws InvocationTargetException, IllegalAccessException {
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
}
