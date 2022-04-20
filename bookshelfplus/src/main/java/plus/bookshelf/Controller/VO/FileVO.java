package plus.bookshelf.Controller.VO;

import lombok.Data;

@Data
public class FileVO {

    // 文件Id
    Integer id;

    // 关联的书籍Id
    Integer bookId;

    // 文件名，不含扩展名
    String fileName;

    // 文件扩展名
    String fileExt;

    // 文件大小
    long fileSize;

    // 文件哈希 - SHA1
    String fileSha1;

    // 总页数
    Integer numberOfPages;

    // 是否含有水印
    Boolean watermark;

    // 是否有广告
    Boolean advertising;

    // 文件来源 电子版/扫描版
    String source;

    // 文件创建时间
    long fileCreateAt;

    // 文件修改时间
    long fileModifiedAt;
}
