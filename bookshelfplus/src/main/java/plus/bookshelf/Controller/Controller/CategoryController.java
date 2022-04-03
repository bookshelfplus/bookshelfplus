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
import plus.bookshelf.Controller.VO.CategoryVO;
import plus.bookshelf.Service.Model.CategoryModel;
import plus.bookshelf.Service.Service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "书籍分类信息")
@Controller("category")
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @Autowired
    CategoryService categoryService;

    @ApiOperation(value = "获取指定分类", notes = "获取指定的书籍分类")
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

    @ApiOperation(value = "获取所有分类", notes = "获取所有的书籍分类")
    @RequestMapping(value = "list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getAll() {
        List<CategoryModel> categoryModels = categoryService.getAllCategorys();
        List<CategoryVO> categoryVOS = new ArrayList<>();
        for (CategoryModel categoryModel : categoryModels) {
            CategoryVO categoryVO = convertFromModel(categoryModel);
            categoryVOS.add(categoryVO);
        }
        return CommonReturnType.create(categoryVOS);
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
