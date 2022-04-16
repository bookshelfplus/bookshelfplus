package plus.bookshelf.Service.Service;

import org.springframework.transaction.annotation.Transactional;
import plus.bookshelf.Common.Enum.FileStorageMediumEnum;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Service.Model.FileObjectModel;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface FileObjectService {

    /**
     * 通过书本Id获取关联文件，进而获取所有关联文件对应的文件对象
     *
     * @param bookId
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    List<FileObjectModel> getFileObjectByBookId(Integer bookId) throws InvocationTargetException, IllegalAccessException;

    /**
     * 列出所有文件对象
     *
     * @return
     */
    List<FileObjectModel> list(String token) throws InvocationTargetException, IllegalAccessException, BusinessException;

    /**
     * 添加文件对象
     * 返回是否添加成功
     *
     * @param fileObjectModel
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    Boolean addFileObject(FileObjectModel fileObjectModel) throws InvocationTargetException, IllegalAccessException;

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
     * @return 是否插入成功
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws BusinessException
     */
    @Transactional
    Boolean uploadFile(Integer fileId, String fileName, String filePath, Long fileSize, String fileSHA1, String fileExt, String fileNameWithoutExt, FileStorageMediumEnum fileStorageMediumEnum, String bookOrigin, Long lastModified) throws InvocationTargetException, IllegalAccessException, BusinessException;

    /**
     * 修改文件对象上传状态信息
     *
     * @param fileId
     * @param fileStatus
     */
    Boolean updateFileStatus(Integer fileId, String fileStatus) throws InvocationTargetException, IllegalAccessException;

    /**
     * 通过文件路径获取文件
     *
     * @param filePath 文件路径
     * @return
     */
    FileObjectModel getFileObjectByFilePath(String filePath) throws InvocationTargetException, IllegalAccessException;

    /**
     * 通过 Id 获取文件对象
     *
     * @param fileObjectId 文件对象 Id
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    FileObjectModel getFileObjectById(Integer fileObjectId) throws InvocationTargetException, IllegalAccessException;
}
