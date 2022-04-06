package plus.bookshelf.Service.Service;

import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Dao.DO.BookDO;
import plus.bookshelf.Service.Model.BookModel;

import java.util.List;
import java.util.Map;

public interface BookService {
    /**
     * 通过书籍Id获取书籍
     *
     * @param id
     * @return
     */
    BookModel getBookById(Integer id);

    /**
     * 通过搜索条件获取书籍列表
     *
     * @return
     */
    List<BookModel> searchBooks(BookModel bookModel) throws BusinessException;

    /**
     * 添加书籍
     *
     * @param bookModel
     * @return
     */
    Integer addBook(BookModel bookModel) throws BusinessException;

    /**
     * 修改书籍
     * @param bookModel
     * @return
     * @throws BusinessException
     */
    Integer modifyBook(BookModel bookModel) throws BusinessException;

    /**
     * 用户收藏书籍
     *
     * @param bookId 书籍id
     * @return
     */
    Boolean addFavorites(Integer userId, Integer bookId) throws BusinessException;

    /**
     * 用户取消收藏书籍
     * @param userId
     * @param bookId
     * @return
     * @throws BusinessException
     */
    Boolean removeFavorites(Integer userId, Integer bookId) throws BusinessException;

    /**
     * 获取用户收藏状态
     * @param userId 用户id
     * @param bookId 书籍id
     * @return
     * @throws BusinessException
     */
    Map getFavoritesStatus(Integer userId, Integer bookId) throws BusinessException;
}
