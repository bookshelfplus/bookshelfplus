package plus.bookshelf.Service.Model;

import lombok.Data;
import plus.bookshelf.Common.Enum.Language;

@Data
public class BookModel {

    // 书籍id
    Integer id;

    // 书名
    String bookName;

    // 书籍简介
    String description;

    // 作者姓名
    String author;

    //书籍所属分类
    CategoryModel category;

    // 出版社
    String publishingHouse;

    // 语言
    Language language;

    // 来源(版权)信息
    String copyright;

    // 是否删除
    Boolean isDelete;

    // 缩略图
    String thumbnail;
}
