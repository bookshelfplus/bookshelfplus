package plus.bookshelf.Service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.bookshelf.Common.Enum.FileStorageMediumEnum;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.FileManager.QCloudCosUtils;
import plus.bookshelf.Config.QCloudCosConfig;
import plus.bookshelf.Dao.DO.FileObjectDO;
import plus.bookshelf.Dao.Mapper.FileObjectDOMapper;
import plus.bookshelf.Service.Model.FileModel;
import plus.bookshelf.Service.Model.FileObjectModel;
import plus.bookshelf.Service.Service.CosPresignedUrlGenerateLogService;
import plus.bookshelf.Service.Service.FileObjectService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
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

    @Autowired
    QCloudCosConfig qCloudCosConfig;

    @Autowired
    CosPresignedUrlGenerateLogService cosPresignedUrlGenerateLogService;

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
    public List<FileObjectModel> list() throws InvocationTargetException, IllegalAccessException, BusinessException {

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

    /**
     * 修改文件对象
     * 返回是否修改成功
     *
     * @param fileObjectModel
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public Boolean modifyFileObject(FileObjectModel fileObjectModel) {
        FileObjectDO fileObjectDO = convertFromFileObjectModel(fileObjectModel);
        Integer id = fileObjectDO.getId();
        if (id == 0) {
            return false;
        }
        int affectRows = fileObjectDOMapper.updateByPrimaryKeySelective(fileObjectDO);
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
     * @param fileName              文件名（不包含扩展名）
     * @param fileStorageMediumEnum 文件存储介质
     * @param source                文件来源
     * @return 返回文件Id
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws BusinessException
     */
    @Override
    @Transactional
    public Integer[] uploadFile(Integer fileId, String fileName, String filePath, Long fileSize, String fileSHA1,
                                String fileExt, FileStorageMediumEnum fileStorageMediumEnum,
                                String source, Long lastModified
    ) throws InvocationTargetException, IllegalAccessException, BusinessException {

        if (fileId == 0) {
            // 在数据库中创建新文件
            FileModel fileModel = new FileModel();

            fileModel.setFileName(fileName);
            fileModel.setFileSize(fileSize);
            fileModel.setFileSha1(fileSHA1);
            fileModel.setFileExt(fileExt);
            fileModel.setSource(source);

            // 其余使用默认设置
            fileModel.setBookId(0);
            fileModel.setNumberOfPages(0);
            fileModel.setWatermark(false);
            fileModel.setAdvertising(false);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(lastModified);
            fileModel.setFileCreateAt(calendar.getTime());

            // 获取时间戳为 0 的时间 1970-01-01 08:00:00
            // calendar.setTimeInMillis(0);
            // Date time = calendar.getTime();

            Boolean isSuccess = fileService.addFile(fileModel);
            if (!isSuccess) {
                throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "文件创建失败");
            }
            fileId = fileService.getLastInsertId();
        }

        FileObjectModel fileObjectModel = new FileObjectModel();

        fileObjectModel.setFileId(fileId);

        fileObjectModel.setStorageMedium(fileStorageMediumEnum.getStorageMediumName());
        fileObjectModel.setFilePath(filePath);
        fileObjectModel.setUploadStatus("UPLOADING");

        // 其余使用默认设置
        fileObjectModel.setFilePwd("");
        fileObjectModel.setFileShareCode("");

        Boolean isSuccess = addFileObject(fileObjectModel);
        if (!isSuccess) {
            throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "文件对象创建失败");
        }

        int lastInsertId = fileObjectDOMapper.getLastInsertId();

        // fileId, fileObjectId
        return new Integer[]{fileId, lastInsertId};
    }

    /**
     * 修改文件对象上传状态信息
     *
     * @param fileObjectId
     * @param fileStatus
     */
    @Override
    public Boolean updateFileStatus(Integer fileObjectId, String fileStatus) {
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

    /**
     * 列出指定文件的所有文件对象
     *
     * @return
     */
    @Override
    public List<FileObjectModel> getFileObjectListByFileId(Integer fileId) throws InvocationTargetException, IllegalAccessException, BusinessException {

        FileObjectDO[] fileObjectDOS = fileObjectDOMapper.selectByFileId(fileId);

        List<FileObjectModel> fileObjectModels = new ArrayList<>();
        for (FileObjectDO fileObjectDO : fileObjectDOS) {
            FileObjectModel fileObjectModel = convertFromDataObject(fileObjectDO);
            fileObjectModels.add(fileObjectModel);
        }

        return fileObjectModels;
    }

    /**
     * 删除文件对象
     *
     * @param fileObjectId
     * @return
     * @throws BusinessException
     */
    @Override
    public Integer deleteFileObject(Integer fileObjectId) throws BusinessException {
        if (fileObjectId == null || fileObjectId == 0) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "文件对象 Id 不能为空");
        }

        FileObjectDO fileObjectDO = fileObjectDOMapper.selectByPrimaryKey(fileObjectId);
        if (fileObjectDO == null) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "文件对象不存在，或许您已经删掉这个文件了，刷新下页面吧");
        }

        // 删除对象存储中的文件
        QCloudCosUtils qCloudCosUtils = new QCloudCosUtils(qCloudCosConfig, cosPresignedUrlGenerateLogService);
        Boolean isSuccess = qCloudCosUtils.deleteObject(fileObjectDO.getFilePath());
        if (!isSuccess) {
            throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "删除文件对象失败，请稍后重试");
        }

        // 确认文件是否被删除
        Boolean isStillExist = qCloudCosUtils.doesObjectExist(fileObjectDO.getFilePath());
        if (isStillExist) {
            //
            throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "删除腾讯云COS存储桶中文件出现异常，请稍后重试");
        }

        // 删除数据库中记录
        int affecctRows = fileObjectDOMapper.deleteByPrimaryKey(fileObjectId);
        return affecctRows;
    }
}
