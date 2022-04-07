package plus.bookshelf.Service.Impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.Validator.ValidationResult;
import plus.bookshelf.Common.Validator.ValidatorImpl;
import plus.bookshelf.Dao.DO.BookDO;
import plus.bookshelf.Dao.DO.BookDOExample;
import plus.bookshelf.Dao.DO.UserFavoritesDO;
import plus.bookshelf.Dao.Mapper.BookDOMapper;
import plus.bookshelf.Dao.Mapper.UserFavoritesDOMapper;
import plus.bookshelf.Service.Model.BookModel;
import plus.bookshelf.Service.Model.CategoryModel;
import plus.bookshelf.Service.Service.BookService;
import plus.bookshelf.Service.Service.CategoryService;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDOMapper bookDOMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserFavoritesDOMapper userFavoritesDOMapper;

    @Autowired
    ValidatorImpl validator;

    /**
     * 通过 书籍id 查找图书
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public BookModel getBookById(Integer id) {
        // 查询得到bookDO
        BookDO bookDO = bookDOMapper.selectByPrimaryKey(id);
        BookModel bookModel = convertFromDataObjecct(bookDO);
        return bookModel;
    }

    /**
     * 查找图书
     *
     * @param bookModel
     * @return
     * @throws BusinessException
     */
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
        if (bookModel.getCategory() != null && bookModel.getCategory().getId() != null && bookModel.getCategory().getId() != 0)
            criteria.andCategoryIdEqualTo(bookModel.getCategory().getId());
        // if (StringUtils.isNotBlank(bookModel.getPublishingHouse()))
        //     criteria.andPublishingHouseEqualTo(bookModel.getPublishingHouse());

        List<BookDO> bookDOs = bookDOMapper.selectByExampleWithBLOBs(bookDOExample);
        return convertFromDataObjecctList(bookDOs);
    }

    /**
     * 添加图书
     *
     * @param bookModel
     * @return
     * @throws BusinessException
     */
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

    /**
     * 修改图书信息
     *
     * @param bookModel
     * @return
     * @throws BusinessException
     */
    @Override
    public Integer modifyBook(BookModel bookModel) throws BusinessException {
        // 校验入参
        ValidationResult result = validator.validate(bookModel);
        if (result.isHasErrors()) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        BookDO bookDO = convertToDataObjecct(bookModel);
        return bookDOMapper.updateByPrimaryKeySelective(bookDO);
    }

    /**
     * 通过 id 删除图书
     *
     * @param bookId
     * @return
     * @throws BusinessException
     */
    @Override
    public Integer deleteBook(Integer bookId) throws BusinessException {
        if (bookId == null || bookId == 0) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR, "书籍id不能为空");
        }
        return bookDOMapper.deleteByPrimaryKey(bookId);
    }

    /**
     * 用户收藏书籍
     *
     * @param userId 用户id
     * @param bookId 书籍id
     * @return
     * @throws BusinessException
     */
    @Override
    public Boolean addFavorites(Integer userId, Integer bookId) throws BusinessException {
        UserFavoritesDO userFavoritesDO = new UserFavoritesDO();
        userFavoritesDO.setBookId(bookId);
        userFavoritesDO.setUserId(userId);
        userFavoritesDO.setCreateTime(new Date());
        int affectRows = userFavoritesDOMapper.insert(userFavoritesDO);
        return affectRows > 0;
    }

    /**
     * 用户取消收藏书籍
     *
     * @param userId 用户id
     * @param bookId 书籍id
     * @return
     * @throws BusinessException
     */
    @Override
    public Boolean removeFavorites(Integer userId, Integer bookId) throws BusinessException {
        int affectRows = userFavoritesDOMapper.deleteByUserIdAndBookId(userId, bookId);
        return affectRows > 0;
    }

    /**
     * 获取用户收藏书籍列表
     *
     * @param userId 用户id
     * @return
     * @throws BusinessException
     */
    @Override
    public List<BookModel> getFavoritesList(Integer userId) throws BusinessException {
        BookDO[] bookDOS = bookDOMapper.selectFavoritesListByUserId(userId);

        List<BookModel> bookModels = new ArrayList<>();
        for (BookDO bookDO : bookDOS) {
            BookModel bookModel = convertFromDataObjecct(bookDO);
            bookModels.add(bookModel);
        }
        return bookModels;
    }

    /**
     * 获取用户收藏状态
     *
     * @param userId 用户id
     * @param bookId 书籍id
     * @return
     * @throws BusinessException
     */
    @Override
    public Map getFavoritesStatus(Integer userId, Integer bookId) throws BusinessException {
        UserFavoritesDO userFavoritesDO = userFavoritesDOMapper.selectCountByUserIdAndBookId(userId, bookId);

        Map<String, Object> result = new HashMap<>();
        if (userFavoritesDO == null) {
            // 用户未收藏
            result.put("status", "0");
            result.put("time", null);
        } else {
            // 用户已收藏，返回收藏时间
            result.put("status", "1");
            result.put("time", userFavoritesDO.getCreateTime());
        }
        return result;
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
