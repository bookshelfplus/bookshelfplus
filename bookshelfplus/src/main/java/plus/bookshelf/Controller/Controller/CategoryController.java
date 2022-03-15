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
import plus.bookshelf.Controller.VO.CategoryVO;
import plus.bookshelf.Service.Model.BookModel;
import plus.bookshelf.Service.Model.CategoryModel;
import plus.bookshelf.Service.Service.BookService;
import plus.bookshelf.Service.Service.CategoryService;

@Api(value = "书籍分类")
@Controller("category")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @ApiOperation(value = "获取书籍分类", notes = "获取书籍分类")
    @RequestMapping(value = "get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType get(@RequestParam(value = "id") Integer id) {
        if (id == null) {
            return null;
        }

        CategoryModel categoryModel = categoryService.getCategoryById(id);
        CategoryVO categoryVO = convertFromModel(categoryModel);
        return CommonReturnType.create(categoryVO);
    }

    private CategoryVO convertFromModel(CategoryModel categoryModel) {
        if (categoryModel == null) {
            return null;
        }
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(categoryModel, categoryVO);
        return categoryVO;
    }
}
