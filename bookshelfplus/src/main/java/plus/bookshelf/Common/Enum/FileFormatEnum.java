package plus.bookshelf.Common.Enum;

public enum FileFormatEnum {
    PDF("pdf", "PDF文件"),
    MOBI("mobi", "mobi文件"),
    EPUB("epub", "epub电子书文件"),
    TXT("txt", "文本文档"),
    CHM("chm", "CHM帮助文档"),
    AZW3("azw3", "azw3文件"),
    DOC("doc", "Word文档"),
    DOCX("docx", "Word文档");

    private FileFormatEnum(String ext, String extIntro) {
        this.ext = ext;
        this.extIntro = extIntro;
    }

    private String ext;
    private String extIntro;

    public String getExt() {
        return ext;
    }

    public String getExtIntro() {
        return extIntro;
    }
}
