package plus.bookshelf.Controller.Controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import plus.bookshelf.Controller.VO.BookVO;
import plus.bookshelf.Service.Model.BookModel;
import plus.bookshelf.Service.Service.BookService;

@Controller("book")
@RequestMapping("/apiv1/book")
public class BookController extends BaseController {

    @Autowired
    BookService bookService;

    @RequestMapping(value = "get", method = {RequestMethod.GET})
    @ResponseBody
    public BookVO get(@RequestParam(value = "id") Integer id) {
        if (id == null) {
            return null;
        }

        BookModel bookModel = bookService.getBookById(id);
        BookVO bookVO = convertFromModel(bookModel);
        return bookVO;
    }

    private BookVO convertFromModel(BookModel bookModel) {
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(bookModel, bookVO);
        return bookVO;
    }
}
