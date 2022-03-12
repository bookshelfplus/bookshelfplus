package plus.bookshelf.Service.Model;

import lombok.Data;

@Data
public class CategoryModel {

    // 分类名称
    Integer id;

    // 分类名称
    String name;

    // 分类简介
    String description;


    Boolean isShow;

    // 分类显示顺序
    Integer order;

    // 分类级别  0为一级分类, 1为二级分类...
    Integer level;

    // 父分类
    Integer parentId;

    // // 子分类集合
    // Integer[] childrenId;
}
