package plus.bookshelf.Service.Service;

import plus.bookshelf.Service.Model.CategoryModel;

import java.util.List;

public interface CategoryService {
    /**
     * 通过分类Id获取分类
     *
     * @param id
     * @return
     */
    CategoryModel getCategoryById(Integer id);

    /**
     * 获取所有的分类列表
     *
     * @return
     */
    List<CategoryModel> getAllCategorys();
}
