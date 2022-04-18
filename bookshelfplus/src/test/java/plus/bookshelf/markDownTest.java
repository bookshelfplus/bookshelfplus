package plus.bookshelf;

import org.junit.Test;
import plus.bookshelf.Common.MarkdownUtils.MarkdownUtils;

public class markDownTest {

    @Test
    public void xiu() {
        String markdown = MarkdownUtils.getInstance()
                .h1("标题1")
                .p("这是一段段落")
                .h2("标题2")
                .p("这是一段段落")
                .h3("标题3")
                .p("这是一段段落")
                .h4("标题4")
                .p("这是一段段落")
                .h5("标题5")
                .p("这是一段段落")
                .h6("标题6")
                .p("这是一段段落")
                .hr()
                .p("这是一段段落")
                .bold("这是一段加粗的段落")
                .italic("这是一段斜体的段落")
                .code("这是一段代码的段落")
                .blockquote("这是一段引用的段落")
                .ul("这是一段无序列表的段落")
                .ul("这是一段无序列表的段落", 1)
                .ul("这是一段无序列表的段落", 2)
                .ul("这是一段无序列表的段落", 3)
                .ul("这是一段无序列表的段落", 4)
                .ul("这是一段无序列表的段落", 5)
                .ul("这是一段无序列表的段落", 6)
                .ul("这是一段无序列表的段落", 7)
                .ol("这是一段有序列表的段落")
                .ol("这是一段有序列表的段落", 1)
                .ol("这是一段有序列表的段落", 2)
                .ol("这是一段有序列表的段落", 3)
                .ol("这是一段有序列表的段落", 4)
                .ol("这是一段有序列表的段落", 5)
                .ol("这是一段有序列表的段落", 6)
                .ol("这是一段有序列表的段落", 7)
                .table("这是一段表格的段落")
                .link("这是一段链接的段落", "https://www.baidu.com")
                .image("https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF")
                .blockquote(MarkdownUtils.getInstance()
                        .p("这是一段段落")
                        .p("这是一段段落"))
                .getMarkdown();
        System.out.println(markdown);
    }
}
