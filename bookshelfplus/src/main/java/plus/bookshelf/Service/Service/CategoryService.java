package plus.bookshelf.Service.Service;

import plus.bookshelf.Service.Model.CategoryModel;

public interface CategoryService {
    /**
     * 通过分类Id获取分类
     * @param id
     * @return
     */
    CategoryModel getCategoryById(Integer id);
}
