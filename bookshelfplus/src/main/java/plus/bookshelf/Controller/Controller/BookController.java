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
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Controller.VO.BookVO;
import plus.bookshelf.Service.Model.BookModel;
import plus.bookshelf.Service.Model.CategoryModel;
import plus.bookshelf.Service.Service.BookService;

import java.util.List;

@Api(value = "书籍")
@Controller("book")
@RequestMapping("/book")
public class BookController extends BaseController {

    @Autowired
    BookService bookService;

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

    private BookVO convertFromModel(BookModel bookModel) {
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(bookModel, bookVO);
        return bookVO;
    }
}
