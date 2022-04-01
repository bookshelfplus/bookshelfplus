package plus.bookshelf.Service.Impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
        BookDOExample.Criteria criteria = bookDOExample.createCriteria();

        // criteria.andIsDeleteEqualTo(false);
        // if (bookModel.getId() != null && bookModel.getId() != 0)
        //     criteria.andIdEqualTo(bookModel.getId());
        if (StringUtils.isNotBlank(bookModel.getBookName()))
            criteria.andBookNameLike("%" + bookModel.getBookName() + "%");
        // if (StringUtils.isNotBlank(bookModel.getAuthor()))
        //     criteria.andAuthorEqualTo(bookModel.getAuthor());
        // if (bookModel.getCategory() != null && bookModel.getCategory().getId() != null && bookModel.getCategory().getId() != 0)
        //     criteria.andCategoryIdEqualTo(bookModel.getCategory().getId());
        // if (StringUtils.isNotBlank(bookModel.getPublishingHouse()))
        //     criteria.andPublishingHouseEqualTo(bookModel.getPublishingHouse());

        List<BookDO> bookDOs = bookDOMapper.selectByExampleWithBLOBs(bookDOExample);
        return convertFromDataObjecctList(bookDOs);
    }

    @Override
    public Integer addBook(BookModel bookModel) throws BusinessException {

        // 校验入参
        ValidationResult result = validator.validate(bookModel);
        if (result.isHasErrors()) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        BookDO bookDO = convertToDataObjecct(bookModel);
        return bookDOMapper.insertSelective(bookDO);
    }

    private BookModel convertFromDataObjecct(BookDO bookDO) {
        if (bookDO == null) {
            return null;
        }
        BookModel bookModel = new BookModel();
        bookModel.setId(bookDO.getId());
        bookModel.setBookName(bookDO.getBookName());
        bookModel.setDescription(bookDO.getDescription());
        bookModel.setAuthor(bookDO.getAuthor());
        bookModel.setPublishingHouse(bookDO.getPublishingHouse());
        bookModel.setCopyright(bookDO.getCopyright());
        bookModel.setIsDelete(bookDO.getIsDelete());
        bookModel.setThumbnail(bookDO.getThumbnail());
        bookModel.setLanguage(bookDO.getLanguage());

        // 查询得到categoryModel
        CategoryModel categoryModel = categoryService.getCategoryById(bookDO.getCategoryId());
        bookModel.setCategory(categoryModel);

        return bookModel;
    }

    private BookDO convertToDataObjecct(BookModel bookModel) {
        if (bookModel == null) {
            return null;
        }
        BookDO bookDO = new BookDO();
        bookDO.setId(bookModel.getId());
        bookDO.setBookName(bookModel.getBookName());
        bookDO.setDescription(bookModel.getDescription());
        bookDO.setAuthor(bookModel.getAuthor());
        bookDO.setPublishingHouse(bookModel.getPublishingHouse());
        bookDO.setCopyright(bookModel.getCopyright());
        bookDO.setIsDelete(bookModel.getIsDelete());
        bookDO.setThumbnail(bookModel.getThumbnail());
        bookDO.setLanguage(bookModel.getLanguage());
        bookDO.setCategoryId(bookModel.getCategory().getId());
        return bookDO;
    }

    private List<BookModel> convertFromDataObjecctList(List<BookDO> bookDOs) {
        List<BookModel> bookModels = new ArrayList<>();
        for (BookDO bookDO : bookDOs) {
            bookModels.add(convertFromDataObjecct(bookDO));
        }
        return bookModels;
    }
}
