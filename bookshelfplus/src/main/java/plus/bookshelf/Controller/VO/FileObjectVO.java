package plus.bookshelf.Controller.VO;

import lombok.Data;

@Data
public class FileObjectVO {

    // 文件存储介质Id
    private Integer id;

    // 存储的文件Id
    private Integer fileId;

    // 文件名
    private String fileName;

    // 文件大小
    private Long fileSize;

    // 文件类型
    private String fileType;

    // 文件存储介质类型
    String storageMediumType;

    // 文件地址
    // 如果是网盘就是分享链接，如果是本地存储就是文件路径
    String filePath;

    // 如果文件有压缩，那么就是压缩包密码
    String filePwd;

    // 文件提取码
    String fileShareCode;

    // 文件上传状态
    String uploadStatus;

    // 文件哈希 - SHA1
    String hashSha1;

    // 附加字段(JSON存储)
    String additionalFields;
}
