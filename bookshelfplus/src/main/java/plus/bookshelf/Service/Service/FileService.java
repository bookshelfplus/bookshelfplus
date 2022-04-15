package plus.bookshelf.Service.Service;

import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Service.Model.FileModel;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface FileService {
    /**
     * 列出所有文件
     *
     * @return
     */
    List<FileModel> list(String token) throws InvocationTargetException, IllegalAccessException, BusinessException;

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
     * 获取上一步添加的文件Id
     *
     * @return
     */
    Integer getLastInsertId();
}
