package plus.bookshelf.Common.Enum;

public enum BookOrigin {
    ORIGIN("原版电子书"),
    SCAN("扫描版"),
    WORD("文字版"), // 大部分是手机版的电子书重新制作成为PDF的
    HEELP_DOCUMENT("帮助文档"),
    OTHER("其他");

    private BookOrigin(String intro) {
        this.intro = intro;
    }

    private String intro;
}
