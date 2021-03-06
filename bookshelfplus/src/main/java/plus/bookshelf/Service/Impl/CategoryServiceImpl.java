package plus.bookshelf.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.bookshelf.Dao.DO.CategoryDO;
import plus.bookshelf.Dao.Mapper.CategoryDOMapper;
import plus.bookshelf.Service.Model.CategoryModel;
import plus.bookshelf.Service.Service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDOMapper categoryDOMapper;

    /**
     * 获取指定分类详情
     *
     * @param id
     * @return
     */
    @Override
    public CategoryModel getCategoryById(Integer id) {
        CategoryDO categoryDO = categoryDOMapper.selectByPrimaryKey(id);
        CategoryModel categoryModel = convertFromDataObject(categoryDO);

        // // 获得儿子
        // CategoryDO[] childrenDO = categoryDOMapper.selectChildrenByCategoryId(categoryDO.getId());
        // CategoryModel[] children = new CategoryModel[childrenDO.length];
        // int index = 0;
        // for (CategoryDO childDO : childrenDO) {
        //     children[index++] = convertFromDataObject(childDO);
        // }
        //
        // categoryModel.setChildren(children);

        return categoryModel;
    }

    /**
     * 取得所有分类
     *
     * @return
     */
    @Override
    public List<CategoryModel> getAllCategorys() {
        CategoryDO[] categoryDOS = categoryDOMapper.selectAll();
        List<CategoryModel> categoryModels = new ArrayList<>();

        for (CategoryDO categoryDO : categoryDOS) {
            CategoryModel categoryModel = convertFromDataObject(categoryDO);
            categoryModels.add(categoryModel);
        }
        return categoryModels;
    }

    // 转换时不转换父亲与儿子，否则会陷入死循环
    private CategoryModel convertFromDataObject(CategoryDO categoryDO) {
        if (categoryDO == null) {
            return null;
        }

        //设置属性
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(categoryDO.getId());
        categoryModel.setName(categoryDO.getName());
        categoryModel.setDescription(categoryDO.getDescription());
        categoryModel.setIsShow(categoryDO.getIsShow());
        categoryModel.setOrder(categoryDO.getOrder());
        categoryModel.setLevel(categoryDO.getLevel());
        categoryModel.setParentId(categoryDO.getParentId());

        return categoryModel;
    }
}
