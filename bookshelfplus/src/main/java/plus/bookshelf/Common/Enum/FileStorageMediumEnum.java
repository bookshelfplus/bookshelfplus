package plus.bookshelf.Common.Enum;

public enum FileStorageMediumEnum {
    LOCAL("LOCAL", "本地"),
    QCLOUD_COS("QCLOUD_COS", "腾讯云对象存储"),
    BAIDU_NETDISK("BAIDU_NETDISK", "百度网盘"),
    ALIYUN_DRIVE("ALIYUN_DRIVE", "阿里网盘");

    private FileStorageMediumEnum(String storageMediumIndex, String storageMediumDisplayName) {
        this.storageMediumIndex = storageMediumIndex;
        this.storageMediumDisplayName = storageMediumDisplayName;
    }

    private String storageMediumIndex;
    private String storageMediumDisplayName;

    public String getStorageMediumName() {
        return storageMediumIndex;
    }

    public String getStorageMediumDisplayName() {
        return storageMediumDisplayName;
    }
}
