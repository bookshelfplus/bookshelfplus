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
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Controller.VO.BookVO;
import plus.bookshelf.Service.Model.BookModel;
import plus.bookshelf.Service.Service.BookService;

@Api(value = "书籍")
@Controller("book")
@RequestMapping("/book")
public class BookController extends BaseController {

    @Autowired
    BookService bookService;

    @ApiOperation(value = "获取书籍信息",notes = "获取书籍信息")
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

    private BookVO convertFromModel(BookModel bookModel) {
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(bookModel, bookVO);
        return bookVO;
    }
}
