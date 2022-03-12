package plus.bookshelf.Service.Model.Book;

import plus.bookshelf.Common.Enum.Language;
import plus.bookshelf.Service.Model.Category.CategoryModel;

public class BookModel {

    // 书籍id
    Integer id;

    // 书名
    String bookName;

    // 书籍简介
    String description;

    // 作者姓名
    AuthorModel[] author;

    //书籍所属分类
    CategoryModel category;

    // 出版社
    PublishingHouseModel publishingHouse;

    // 语言
    Language language;

    // 来源(版权)信息
    String copyright;

    // 是否删除
    Boolean isDelete;
}
