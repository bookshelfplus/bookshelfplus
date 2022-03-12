package plus.bookshelf.Service.Model.File;

import org.joda.time.DateTime;
import plus.bookshelf.Common.Enum.BookOrigin;
import plus.bookshelf.Common.Enum.FileFormatEnum;
import plus.bookshelf.Service.Model.Book.ThumbnailModel;

public class FileModel {

    // 文件Id
    Integer id;

    // 关联的书籍Id
    Integer bookId;

    // 文件名 (用于展示给用户的文件名，不含扩展名)
    String fileDisplayName;

    // 文件存储名称 (文件的实际文件名，含扩展名)
    String fileName;

    // 文件格式
    FileFormatEnum fileFormat;

    // 总页数
    Integer numberOfPages;

    // 是否含有水印
    Boolean watermark;

    // 是否有广告
    Boolean advertising;

    // 文件来源 电子版/扫描版
    BookOrigin bookOrigin;

    // 缩略图
    ThumbnailModel thumbnail;

    // 文件创建时间
    DateTime fileCreateAt;

    // 文件修改时间
    DateTime fileModifiedAt;

    // 文件大小
    long fileSize;

    // 文件哈希 - MD5
    String hashMd5;

    // 文件哈希 - SHA1
    String hashSha1;

    // 文件哈希 - SHA256
    String hashSha256;
}
