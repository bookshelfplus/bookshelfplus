package plus.bookshelf.Service.Model.Category;

public class CategoryModel {

    // 分类名称
    Integer id;

    // 分类名称
    Integer name;

    // 分类简介
    Integer description;


    Boolean isShow;

    // 分类显示顺序
    Integer order;

    // 分类级别  0为一级分类, 1为二级分类...
    Integer level;

    // 所属父分类Id
    Integer parentId;

    // 父分类
    CategoryModel parent;

    // 子分类集合
    CategoryModel[] children;
}
