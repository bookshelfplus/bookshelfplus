package plus.bookshelf.Common.Enum;

public enum Language {
    SIMPLIFIED_CHINESE(1000, "简体中文"),
    ENGLISH(1001, "English"),
    TRADITIONAL_CHINESE(1001, "繁体中文");

    private Language(Integer langId, String langName) {
        this.langId = langId;
        this.langName = langName;
    }

    private Integer langId;
    private String langName;
}
