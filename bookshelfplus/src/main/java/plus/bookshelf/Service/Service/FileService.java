package plus.bookshelf.Service.Service;

import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Service.Model.FileModel;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface FileService {
    /**
     * 列出文件支持的下载方式
     *
     * @param bookId
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws BusinessException
     */
    List<FileModel> getFileByBookId(Integer bookId) throws InvocationTargetException, IllegalAccessException, BusinessException;

    /**
     * 根据文件ID获取文件信息
     *
     * @param fileId
     * @return
     * @throws BusinessException
     */
    FileModel getFileById(Integer fileId) throws BusinessException;

    /**
     * 列出所有文件
     *
     * @return
     */
    List<FileModel> list();

    /**
     * 列出所有SHA1匹配或者未设置SHA1的文件
     *
     * @return
     */
    List<FileModel> selectBySha1WithNullValue(String token) throws InvocationTargetException, IllegalAccessException, BusinessException;

    /**
     * 列出一个 SHA1匹配 的文件
     *
     * @param fileSha1
     * @return
     */
    FileModel selectBySha1(String fileSha1);

    /**
     * 添加文件信息
     * 返回是否添加成功
     *
     * @param fileModel
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    Boolean addFile(FileModel fileModel) throws InvocationTargetException, IllegalAccessException;

    /**
     * 更新文件对象
     *
     * @param fileModel
     * @return
     */
    Integer updateSelective(FileModel fileModel);

    /**
     * 取消文件和书籍的关联
     *
     * @return
     */
    Integer unbindBook(Integer bookId);

    /**
     * 获取上一步添加的文件Id
     *
     * @return
     */
    Integer getLastInsertId();

    /**
     * 更新文件的SHA1值
     *
     * @param fileId   文件Id
     * @param fileSha1 文件SHA1值
     * @return
     * @throws BusinessException
     */
    Boolean updateFileSha1(Integer fileId, String fileSha1) throws BusinessException;

    /**
     * 通过文件对象Id找到文件Id
     *
     * @param fileObjectId
     * @return
     */
    FileModel getFileByFileObjectId(Integer fileObjectId);
}
