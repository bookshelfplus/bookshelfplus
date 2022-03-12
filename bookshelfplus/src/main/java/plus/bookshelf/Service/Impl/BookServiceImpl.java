package plus.bookshelf.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.bookshelf.Dao.DO.BookDO;
import plus.bookshelf.Dao.Mapper.BookDOMapper;
import plus.bookshelf.Service.Model.BookModel;
import plus.bookshelf.Service.Model.CategoryModel;
import plus.bookshelf.Service.Service.BookService;
import plus.bookshelf.Service.Service.CategoryService;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDOMapper bookDOMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    @Transactional
    public BookModel getBookById(Integer id) {
        // 查询得到bookDO
        BookDO bookDO = bookDOMapper.selectByPrimaryKey(id);
        // 查询得到categoryModel
        CategoryModel categoryModel = categoryService.getCategoryById(bookDO.getCategoryId());

        BookModel bookModel = convertFromDataObjecct(bookDO, categoryModel);
        return bookModel;
    }

    private BookModel convertFromDataObjecct(BookDO bookDO, CategoryModel categoryModel) {
        BookModel bookModel = new BookModel();
        if (bookDO == null) {
            return null;
        }
        bookModel.setId(bookDO.getId());
        bookModel.setBookName(bookDO.getBookName());
        bookModel.setDescription(bookDO.getDescription());
        bookModel.setAuthor(bookDO.getAuthor());
        bookModel.setCategory(categoryModel);
        bookModel.setPublishingHouse(bookDO.getPublishingHouse());
        bookModel.setCopyright(bookDO.getCopyright());
        bookModel.setIsDelete(bookDO.getIsDelete());
        bookModel.setThumbnail(bookDO.getThumbnail());
        return bookModel;
    }
}
