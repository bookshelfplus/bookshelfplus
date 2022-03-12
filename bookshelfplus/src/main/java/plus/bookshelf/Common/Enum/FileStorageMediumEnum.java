package plus.bookshelf.Common.Enum;

public enum FileStorageMediumEnum {
    LOCAL (10000, "本地"),
    BAIDU_NETDISK (20001, "百度网盘"),
    ALIYUN_DRIVE (20002, "阿里网盘");

    private FileStorageMediumEnum(int storageMediumIndex, String storageMediumDisplayName) {
        this.storageMediumIndex = storageMediumIndex;
        this.storageMediumDisplayName = storageMediumDisplayName;
    }
    private Integer storageMediumIndex;
    private String storageMediumDisplayName;

    public Integer getStorageMediumIndex() {
        return storageMediumIndex;
    }

    public String getStorageMediumDisplayName() {
        return storageMediumDisplayName;
    }
}
