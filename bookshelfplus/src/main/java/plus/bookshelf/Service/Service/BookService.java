package plus.bookshelf.Service.Service;

import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Dao.DO.BookDO;
import plus.bookshelf.Service.Model.BookModel;

import java.util.List;

public interface BookService {
    /**
     * 通过书籍Id获取书籍
     * @param id
     * @return
     */
    BookModel getBookById(Integer id);

    /**
     * 通过搜索条件获取书籍列表
     * @return
     */
    List<BookModel> searchBooks(BookModel bookModel) throws BusinessException;
}
