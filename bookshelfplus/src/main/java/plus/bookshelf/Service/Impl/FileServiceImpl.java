package plus.bookshelf.Service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Config.QCloudCosConfig;
import plus.bookshelf.Dao.DO.FileDO;
import plus.bookshelf.Dao.Mapper.FileDOMapper;
import plus.bookshelf.Dao.Mapper.FileObjectDOMapper;
import plus.bookshelf.Service.Model.FileModel;
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
     * 列出文件支持的下载方式
     *
     * @param bookId
     * @return
     * @throws BusinessException
     */
    @Override
    public List<FileModel> getFileByBookId(Integer bookId) throws BusinessException {

        if (bookId == 0 || bookId == null) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "bookId不能为空");
        }

        FileDO[] fileDOS = fileDOMapper.selectAvailableByBookId(bookId);

        List<FileModel> fileModels = new ArrayList<>();
        for (FileDO fileDO : fileDOS) {
            FileModel fileModel = convertFromDataObject(fileDO);
            fileModels.add(fileModel);
        }

        return fileModels;
    }

    /**
     * 根据文件ID获取文件信息
     *
     * @param fileId
     * @return
     * @throws BusinessException
     */
    @Override
    public FileModel getFileById(Integer fileId) throws BusinessException {

        if (fileId == 0 || fileId == null) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "fileId不能为空");
        }

        FileDO fileDO = fileDOMapper.selectByPrimaryKey(fileId);
        FileModel fileModel = convertFromDataObject(fileDO);
        return fileModel;
    }

    /**
     * 列出所有文件
     *
     * @return
     */
    @Override
    public List<FileModel> list() {

        FileDO[] fileDOS = fileDOMapper.selectAll();

        List<FileModel> fileModels = new ArrayList<>();
        for (FileDO fileDO : fileDOS) {
            FileModel fileModel = convertFromDataObject(fileDO);
            fileModels.add(fileModel);
        }

        return fileModels;
    }

    /**
     * 列出所有 SHA1匹配 和 未设置SHA1 的文件
     *
     * @return
     */
    @Override
    public List<FileModel> selectBySha1WithNullValue(String fileSha1) {

        FileDO[] fileDOS = fileDOMapper.selectBySha1WithNullValue(fileSha1);

        List<FileModel> fileModels = new ArrayList<>();
        for (FileDO fileDO : fileDOS) {
            FileModel fileModel = convertFromDataObject(fileDO);
            fileModels.add(fileModel);
        }

        return fileModels;
    }

    /**
     * 列出一个 SHA1匹配 的文件
     *
     * @return
     */
    @Override
    public FileModel selectBySha1(String fileSha1) {
        FileDO fileDO = fileDOMapper.selectBySha1(fileSha1);
        FileModel fileModel = convertFromDataObject(fileDO);
        return fileModel;
    }

    private FileModel convertFromDataObject(FileDO fileDO) {
        if (fileDO == null) {
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
    public Boolean addFile(FileModel fileModel) {
        FileDO fileDO = convertFromFileModel(fileModel);
        int affectRows = fileDOMapper.insertSelective(fileDO);
        return affectRows > 0;
    }

    private FileDO convertFromFileModel(FileModel fileModel) {
        FileDO fileDO = new FileDO();
        BeanUtils.copyProperties(fileModel, fileDO);
        return fileDO;
    }

    /**
     * 取消文件和书籍的关联
     *
     * @return
     */
    @Override
    public Integer unbindBook(Integer bookId) {
        return fileDOMapper.unbindBook(bookId);
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

    /**
     * 更新文件的SHA1值
     *
     * @return
     */
    @Override
    public Boolean updateFileSha1(Integer fileId, String fileSha1) throws BusinessException {
        if (fileId == null || fileId == 0) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "fileId不能为空");
        }
        Integer affectRows = fileDOMapper.updateFileSha1(fileId, fileSha1);
        return affectRows > 0;
    }

    /**
     * 通过文件对象Id找到文件Id
     *
     * @param fileObjectId
     * @return
     */
    @Override
    public FileModel getFileByFileObjectId(Integer fileObjectId) {
        FileDO fileDO = fileDOMapper.selectByPrimaryKey(fileObjectId);
        FileModel fileModel = convertFromDataObject(fileDO);
        return fileModel;
    }
}
