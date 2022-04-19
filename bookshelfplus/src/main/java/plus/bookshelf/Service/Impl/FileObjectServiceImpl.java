package plus.bookshelf.Service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.bookshelf.Common.Enum.FileStorageMediumEnum;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Dao.DO.FileObjectDO;
import plus.bookshelf.Dao.Mapper.FileObjectDOMapper;
import plus.bookshelf.Service.Model.FileModel;
import plus.bookshelf.Service.Model.FileObjectModel;
import plus.bookshelf.Service.Model.UserModel;
import plus.bookshelf.Service.Service.FileObjectService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class FileObjectServiceImpl implements FileObjectService {

    @Autowired
    FileObjectDOMapper fileObjectDOMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    FileServiceImpl fileService;

    /**
     * 通过书本Id获取关联文件，进而获取所有关联文件对应的文件对象
     *
     * @return
     */
    @Override
    public List<FileObjectModel> getFileObjectByBookId(Integer bookId) throws InvocationTargetException, IllegalAccessException {

        FileObjectDO[] fileObjectDOS = fileObjectDOMapper.selectFileObjectByBookId(bookId);

        List<FileObjectModel> fileObjectModels = new ArrayList<>();
        for (FileObjectDO fileObjectDO : fileObjectDOS) {
            FileObjectModel fileObjectModel = convertFromDataObject(fileObjectDO);
            fileObjectModels.add(fileObjectModel);
        }

        return fileObjectModels;
    }

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
        if (fileObjectDO == null) {
            return null;
        }
        FileObjectModel fileObjectModel = new FileObjectModel();
        BeanUtils.copyProperties(fileObjectDO, fileObjectModel);
        return fileObjectModel;
    }

    /**
     * 添加文件对象
     * 返回是否添加成功
     *
     * @param fileObjectModel
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public Boolean addFileObject(FileObjectModel fileObjectModel) throws InvocationTargetException, IllegalAccessException {
        FileObjectDO fileObjectDO = convertFromFileObjectModel(fileObjectModel);
        int affectRows = fileObjectDOMapper.insertSelective(fileObjectDO);
        return affectRows > 0;
    }

    private FileObjectDO convertFromFileObjectModel(FileObjectModel fileObjectModel) {
        if (fileObjectModel == null) {
            return null;
        }
        FileObjectDO fileObjectDO = new FileObjectDO();
        BeanUtils.copyProperties(fileObjectModel, fileObjectDO);
        return fileObjectDO;
    }


    /**
     * 向数据库中插入文件信息
     *
     * @param fileName              文件名
     * @param filePath              文件路径
     * @param fileSize              文件大小
     * @param fileSHA1              文件SHA1
     * @param fileExt               文件扩展名
     * @param fileNameWithoutExt    文件名（不包含扩展名）
     * @param fileStorageMediumEnum 文件存储介质
     * @param bookOrigin            文件来源
     * @return 返回文件Id
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws BusinessException
     */
    @Override
    @Transactional
    public Integer uploadFile(Integer fileId, String fileName, String filePath, Long fileSize, String fileSHA1,
                              String fileExt, String fileNameWithoutExt, FileStorageMediumEnum fileStorageMediumEnum,
                              String bookOrigin, Long lastModified
    ) throws InvocationTargetException, IllegalAccessException, BusinessException {

        if (fileId == 0) {
            // 在数据库中创建新文件
            FileModel fileModel = new FileModel();

            fileModel.setFileName(fileName);
            fileModel.setFileSize(fileSize);
            fileModel.setFileSha1(fileSHA1);
            fileModel.setFileFormat(fileExt);
            fileModel.setFileDisplayName(fileNameWithoutExt);
            fileModel.setBookOrigin(bookOrigin);

            // 其余使用默认设置
            fileModel.setBookId(0);
            fileModel.setNumberOfPages(0);
            fileModel.setWatermark(false);
            fileModel.setAdvertising(false);

            // 获取时间戳为 0 的时间 1970-01-01
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(0);
            Date time = calendar.getTime();

            fileModel.setFileCreateAt(time);
            fileModel.setFileModifiedAt(time);

            Boolean isSuccess = fileService.addFile(fileModel);
            if (!isSuccess) {
                throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "文件创建失败");
            }
            fileId = fileService.getLastInsertId();
        }

        FileObjectModel fileObjectModel = new FileObjectModel();

        fileObjectModel.setFileId(fileId);

        fileObjectModel.setFileName(fileName);
        fileObjectModel.setFileSize(fileSize);
        fileObjectModel.setFileType(fileExt);
        fileObjectModel.setStorageMediumType(fileStorageMediumEnum.getStorageMediumName());
        fileObjectModel.setFilePath(filePath);
        fileObjectModel.setFileSha1(fileSHA1);
        fileObjectModel.setUploadStatus("UPLOADING");
        fileObjectModel.setLastModified(lastModified);

        // 其余使用默认设置
        fileObjectModel.setFilePwd("");
        fileObjectModel.setFileShareCode("");
        fileObjectModel.setAdditionalFields("");

        Boolean isSuccess = addFileObject(fileObjectModel);
        if (!isSuccess) {
            throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "文件对象创建失败");
        }
        return fileId;
    }

    /**
     * 修改文件对象上传状态信息
     *
     * @param fileObjectId
     * @param fileStatus
     */
    @Override
    public Boolean updateFileStatus(Integer fileObjectId, String fileStatus) throws InvocationTargetException, IllegalAccessException {
        if (fileObjectId == null || fileObjectId == 0) {
            return false;
        }
        FileObjectModel fileObjectModel = new FileObjectModel();
        fileObjectModel.setId(fileObjectId);
        fileObjectModel.setUploadStatus(fileStatus);

        FileObjectDO fileObjectDO = convertFromFileObjectModel(fileObjectModel);
        int affectRows = fileObjectDOMapper.updateByPrimaryKeySelective(fileObjectDO);
        return affectRows > 0;
    }

    /**
     * 通过文件路径获取文件对象
     *
     * @param filePath 文件路径
     * @return
     */
    @Override
    public FileObjectModel getFileObjectByFilePath(String filePath) throws InvocationTargetException, IllegalAccessException {
        FileObjectDO fileObjectDO = fileObjectDOMapper.selectByFilePath(filePath);
        FileObjectModel fileObjectModel = convertFromDataObject(fileObjectDO);
        return fileObjectModel;
    }

    /**
     * 通过 Id 获取文件对象
     *
     * @param fileObjectId 文件对象 Id
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public FileObjectModel getFileObjectById(Integer fileObjectId) throws InvocationTargetException, IllegalAccessException {
        FileObjectDO fileObjectDO = fileObjectDOMapper.selectByPrimaryKey(fileObjectId);
        FileObjectModel fileObjectModel = convertFromDataObject(fileObjectDO);
        return fileObjectModel;
    }
}
