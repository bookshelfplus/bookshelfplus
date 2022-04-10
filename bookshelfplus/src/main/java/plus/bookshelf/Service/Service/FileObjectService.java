package plus.bookshelf.Service.Service;

import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Service.Model.FileObjectModel;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface FileObjectService {
    /**
     * 列出所有文件对象
     *
     * @return
     */
    List<FileObjectModel> list(String token) throws InvocationTargetException, IllegalAccessException, BusinessException;
}
