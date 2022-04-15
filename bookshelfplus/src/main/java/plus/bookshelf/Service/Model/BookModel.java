package plus.bookshelf.Service.Model;

import lombok.Data;

@Data
public class BookModel {

    // 书籍id
    Integer id;

    // 书名
    // @NotBlank(message = "书籍名称不能为空")
    String bookName;

    // 书籍简介
    String description;

    // 作者姓名
    String author;

    // 书籍所属分类
    CategoryModel category;

    // 出版社
    String publishingHouse;

    // 语言
    String language;

    // 来源(版权)信息
    String copyright;

    // 是否删除
    Boolean isDelete = false;

    // 缩略图
    String thumbnail;
}
