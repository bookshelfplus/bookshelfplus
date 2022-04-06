package plus.bookshelf.Controller.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Controller.VO.BookVO;
import plus.bookshelf.Service.Impl.UserServiceImpl;
import plus.bookshelf.Service.Model.BookModel;
import plus.bookshelf.Service.Model.CategoryModel;
import plus.bookshelf.Service.Model.UserModel;
import plus.bookshelf.Service.Service.BookService;

import java.util.List;
import java.util.Map;

@Api(tags = "书籍信息")
@Controller("book")
@RequestMapping("/book")
public class BookController extends BaseController {

    @Autowired
    BookService bookService;

    @Autowired
    UserServiceImpl userService;

    @ApiOperation(value = "获取书籍信息", notes = "获取书籍信息")
    // @ApiImplicitParam(name = "book", value = "图书详细实体", required = true, dataType = "Book")
    @RequestMapping(value = "get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType get(@RequestParam(value = "id") Integer id) {
        if (id == null) {
            return null;
        }

        BookModel bookModel = bookService.getBookById(id);
        BookVO bookVO = convertFromModel(bookModel);
        return CommonReturnType.create(bookVO);
    }

    @ApiOperation(value = "查询书籍列表", notes = "通过指定条件查询书籍列表")
    @RequestMapping(value = "search", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType search(@RequestParam(required = false, value = "id") Integer id,
                                   @RequestParam(required = false, value = "bookName") String bookName,
                                   @RequestParam(required = false, value = "author") String author,
                                   @RequestParam(required = false, value = "categoryId") Integer categoryId,
                                   @RequestParam(required = false, value = "publishingHouse") String publishingHouse,
                                   @RequestParam(required = false, value = "language") String language) throws BusinessException {
        BookModel bookModel = new BookModel();

        bookModel.setId(id);
        bookModel.setBookName(bookName);
        bookModel.setAuthor(author);
        bookModel.setPublishingHouse(publishingHouse);
        bookModel.setLanguage(language);
        if (categoryId != null) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setId(categoryId);
            bookModel.setCategory(categoryModel);
        }

        List<BookModel> bookModels = bookService.searchBooks(bookModel);
        return CommonReturnType.create(bookModels);
    }

    @ApiOperation(value = "【用户】收藏/取消收藏书籍", notes = "用户收藏书籍")
    @RequestMapping(value = "toggleFavorites", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType addFavorites(@RequestParam(value = "token", required = false) String token,
                                         @RequestParam(value = "bookId", required = false) Integer bookId,
                                         @RequestParam(value = "status", required = true) Boolean isFavoritesNow) throws BusinessException {

        // 已经在 getUserByToken 方法中判断了 token 为空、不合法；用户不存在情况，此处无需再判断
        UserModel userModel = userService.getUserByToken(redisTemplate, token);

        if (isFavoritesNow) {
            // 这里查询一次书籍的用处是为了防止用户收藏的书籍不存在
            BookModel bookModel = bookService.getBookById(bookId);
            if (bookModel == null) {
                throw new BusinessException(BusinessErrorCode.BOOK_NOT_EXIST, "书籍不存在");
            }

            Boolean isSuccess = bookService.addFavorites(userModel.getId(), bookModel.getId());
            if (!isSuccess) {
                throw new BusinessException(BusinessErrorCode.BOOK_FAVORITES_ALREADY_EXIST, "书籍已经收藏");
            }
        } else {
            // 不用担心书籍不存在的情况，因为书籍不存在数据库中不会删除成功
            Boolean isSuccess = bookService.removeFavorites(userModel.getId(), bookId);
            if (!isSuccess) {
                throw new BusinessException(BusinessErrorCode.BOOK_FAVORITES_NOT_EXIST, "书籍尚未收藏");
            }
        }
        return CommonReturnType.create("success");
    }

    @ApiOperation(value = "【用户】收藏/取消收藏书籍", notes = "用户收藏书籍")
    @RequestMapping(value = "getFavoritesStatus", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType addFavorites(@RequestParam(value = "token", required = false) String token,
                                         @RequestParam(value = "bookId", required = false) Integer bookId) throws BusinessException {

        // 已经在 getUserByToken 方法中判断了 token 为空、不合法；用户不存在情况，此处无需再判断
        UserModel userModel = userService.getUserByToken(redisTemplate, token);

        Map favoritesStatus = bookService.getFavoritesStatus(userModel.getId(), bookId);
        return CommonReturnType.create(favoritesStatus);
    }

    @ApiOperation(value = "【管理员】添加/修改书籍", notes = "管理员在后台添加/修改书籍（bookId 传 0 或 null 或 不传 即为添加）")
    @RequestMapping(value = "detail", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType detail(@RequestParam(value = "token", required = false) String token,
                                   @RequestParam(required = false, value = "id") Integer bookId,
                                   @RequestParam(required = false, value = "bookName") String bookName,
                                   @RequestParam(required = false, value = "description") String description,
                                   @RequestParam(required = false, value = "categoryId") Integer categoryId,
                                   @RequestParam(required = false, value = "publishingHouse") String publishingHouse,
                                   @RequestParam(required = false, value = "language") String language,
                                   @RequestParam(required = false, value = "copyright") String copyright,
                                   // 【前端未提交到后端的参数】 isDelete thumbnail
                                   @RequestParam(required = false, value = "isDelete") Boolean isDelete,
                                   @RequestParam(required = false, value = "thumbnail") String thumbnail,
                                   @RequestParam(required = false, value = "author") String author) throws BusinessException {
        // 已经在 getUserByToken 方法中判断了 token 为空、不合法；用户不存在情况，此处无需再判断
        UserModel userModel = userService.getUserByToken(redisTemplate, token);

        BookModel bookModel = new BookModel();

        bookModel.setBookName(bookName);
        bookModel.setDescription(description);
        bookModel.setPublishingHouse(publishingHouse);
        bookModel.setLanguage(language);
        bookModel.setCopyright(copyright);
        bookModel.setIsDelete(isDelete);
        bookModel.setThumbnail(thumbnail);
        bookModel.setAuthor(author);
        if (categoryId != null) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setId(categoryId);
            bookModel.setCategory(categoryModel);
        }

        Integer affectRows = 0;
        if (bookId == null || bookId == 0) {
            // 新增图书
            isDelete = false;
            affectRows = bookService.addBook(bookModel);
        } else {
            //修改图书
            bookModel.setId(bookId);
            affectRows = bookService.modifyBook(bookModel);
        }

        if (affectRows > 0) {
            return CommonReturnType.create("success");
        } else {
            return CommonReturnType.create("failed");
        }
    }

    @ApiOperation(value = "【管理员】删除书籍", notes = "管理员在后台删除书籍")
    @RequestMapping(value = "delete", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType deleteBook(@RequestParam(value = "token", required = false) String token,
                                       @RequestParam(required = true, value = "id") Integer bookId) throws BusinessException {
        // 已经在 getUserByToken 方法中判断了 token 为空、不合法；用户不存在情况，此处无需再判断
        UserModel userModel = userService.getUserByToken(redisTemplate, token);

        Integer affectRows = bookService.deleteBook(bookId);
        if (affectRows > 0) {
            return CommonReturnType.create("success");
        } else {
            return CommonReturnType.create("failed");
        }
    }


    private BookVO convertFromModel(BookModel bookModel) {
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(bookModel, bookVO);
        return bookVO;
    }
}
