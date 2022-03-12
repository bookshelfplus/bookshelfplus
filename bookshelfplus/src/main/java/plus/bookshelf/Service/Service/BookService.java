package plus.bookshelf.Service.Service;

import plus.bookshelf.Service.Model.BookModel;

public interface BookService {
    /**
     * 通过书籍Id获取书籍
     * @param id
     * @return
     */
    BookModel getBookById(Integer id);
}
