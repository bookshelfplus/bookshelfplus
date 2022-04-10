package plus.bookshelf.Service.Service;

import plus.bookshelf.Service.Model.FileModel;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface FileService {
    /**
     * 列出所有文件
     *
     * @return
     */
    List<FileModel> list(String token) throws InvocationTargetException, IllegalAccessException;
}
