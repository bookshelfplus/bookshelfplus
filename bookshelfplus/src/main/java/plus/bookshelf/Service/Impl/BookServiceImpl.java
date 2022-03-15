package plus.bookshelf.Service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.Validator.ValidationResult;
import plus.bookshelf.Common.Validator.ValidatorImpl;
import plus.bookshelf.Dao.DO.BookDO;
import plus.bookshelf.Dao.DO.BookDOExample;
import plus.bookshelf.Dao.Mapper.BookDOMapper;
import plus.bookshelf.Service.Model.BookModel;
import plus.bookshelf.Service.Model.CategoryModel;
import plus.bookshelf.Service.Service.BookService;
import plus.bookshelf.Service.Service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDOMapper bookDOMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    ValidatorImpl validator;

    @Override
    @Transactional
    public BookModel getBookById(Integer id) {
        // 查询得到bookDO
        BookDO bookDO = bookDOMapper.selectByPrimaryKey(id);
        BookModel bookModel = convertFromDataObjecct(bookDO);
        return bookModel;
    }

    @Override
    public List<BookModel> searchBooks(BookModel bookModel) throws BusinessException {

        // 校验入参
        ValidationResult result = validator.validate(bookModel);
        if (result.isHasErrors()) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        BookDOExample bookDOExample = new BookDOExample();
        BeanUtils.copyProperties(bookModel, bookDOExample);
        List<BookDO> bookDOS = bookDOMapper.selectByExample(bookDOExample);

        List<BookModel> bookModels = new ArrayList<>();
        for (BookDO bookDO : bookDOS) {
            bookModels.add(convertFromDataObjecct(bookDO));
        }
        return bookModels;
    }

    private BookModel convertFromDataObjecct(BookDO bookDO) {
        BookModel bookModel = new BookModel();
        if (bookDO == null) {
            return null;
        }
        bookModel.setId(bookDO.getId());
        bookModel.setBookName(bookDO.getBookName());
        bookModel.setDescription(bookDO.getDescription());
        bookModel.setAuthor(bookDO.getAuthor());
        bookModel.setPublishingHouse(bookDO.getPublishingHouse());
        bookModel.setCopyright(bookDO.getCopyright());
        bookModel.setIsDelete(bookDO.getIsDelete());
        bookModel.setThumbnail(bookDO.getThumbnail());

        // 查询得到categoryModel
        CategoryModel categoryModel = categoryService.getCategoryById(bookDO.getCategoryId());
        bookModel.setCategory(categoryModel);

        return bookModel;
    }
}
