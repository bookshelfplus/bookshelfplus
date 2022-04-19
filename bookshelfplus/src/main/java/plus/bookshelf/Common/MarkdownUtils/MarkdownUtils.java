package plus.bookshelf.Common.MarkdownUtils;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.error.Mark;

public class MarkdownUtils {

    /**
     * markdown 内容
     */
    private StringBuilder markdown;

    /**
     * 构造函数
     */
    private MarkdownUtils() {
        this.markdown = new StringBuilder();
    }

    /**
     * 获取对象实例
     *
     * @return
     */
    public static MarkdownUtils getInstance() {
        return new MarkdownUtils();
    }

    /**
     * 添加内容
     *
     * @param str
     * @return MarkdownUtils
     */
    public MarkdownUtils append(String str) {
        markdown.append(str);
        markdown.append("\n\n");
        return this;
    }

    /**
     * 获取markdown内容
     *
     * @return String
     */
    public String getMarkdown() {
        // // 去除最后的一个换行符 (保留一个换行符)
        // return markdown.deleteCharAt(markdown.length() - 1).toString();

        // 去除最后的两个换行符
        return markdown.delete(markdown.length() - 2, markdown.length()).toString();
    }

    /**
     * 一级标题
     *
     * @param str
     * @return MarkdownUtils
     */

    public MarkdownUtils h1(String str) {
        this.append("# " + str);
        return this;
    }

    /**
     * 二级标题
     *
     * @param str
     * @return MarkdownUtils
     */
    public MarkdownUtils h2(String str) {
        this.append("## " + str);
        return this;
    }

    /**
     * 三级标题
     *
     * @param str
     * @return MarkdownUtils
     */
    public MarkdownUtils h3(String str) {
        this.append("### " + str);
        return this;
    }

    /**
     * 四级标题
     *
     * @param str
     * @return MarkdownUtils
     */
    public MarkdownUtils h4(String str) {
        this.append("#### " + str);
        return this;
    }

    /**
     * 五级标题
     *
     * @param str
     * @return MarkdownUtils
     */
    public MarkdownUtils h5(String str) {
        this.append("##### " + str);
        return this;
    }

    /**
     * 六级标题
     *
     * @param str
     * @return MarkdownUtils
     */
    public MarkdownUtils h6(String str) {
        this.append("###### " + str);
        return this;
    }

    /**
     * n级标题
     */
    public MarkdownUtils h(int n, String str) {
        this.append(StringUtils.repeat("#", n) + " " + str);
        return this;
    }

    /**
     * 加粗
     *
     * @param text
     * @return MarkdownUtils
     */
    public MarkdownUtils bold(String text) {
        this.append("**" + text + "**");
        return this;
    }

    /**
     * 斜体
     *
     * @param text
     * @return MarkdownUtils
     */
    public MarkdownUtils italic(String text) {
        this.append("*" + text + "*");
        return this;
    }

    /**
     * 行内代码
     *
     * @param text
     * @return MarkdownUtils
     */
    public MarkdownUtils inlineCode(String text) {
        this.append("`" + text + "`");
        return this;
    }

    /**
     * 代码片段
     *
     * @param text
     * @return MarkdownUtils
     */
    public MarkdownUtils code(String text) {
        this.append("```\n" + text + "\n```");
        return this;
    }

    /**
     * 引用
     *
     * @param markdown
     * @return MarkdownUtils
     */
    public MarkdownUtils blockquote(MarkdownUtils markdown) {
        this.append("> " + markdown.getMarkdown().replaceAll("\n", "\n> "));
        return this;
    }

    public MarkdownUtils blockquote(String str) {
        this.append("> " + str.replaceAll("\n", "\n> "));
        return this;
    }

    /**
     * 链接
     *
     * @param text
     * @param url
     * @return MarkdownUtils
     */
    public MarkdownUtils link(String text, String url) {
        this.append("[" + text + "](" + url + ")");
        return this;
    }

    /**
     * 图片
     *
     * @param url
     * @return MarkdownUtils
     */
    public MarkdownUtils image(String url) {
        this.append("![]( " + url + ")");
        return this;
    }

    /**
     * 分割线
     *
     * @return MarkdownUtils
     */
    public MarkdownUtils hr() {
        this.append("---");
        return this;
    }

    /**
     * 换行
     *
     * @return MarkdownUtils
     */
    public MarkdownUtils br() {
        this.append("");
        return this;
    }

    /**
     * 无序列表
     *
     * @param text
     * @return MarkdownUtils
     */
    public MarkdownUtils ul(String text) {
        this.append("- " + text);
        return this;
    }

    /**
     * 无序列表 (子分类)
     *
     * @param text
     * @param level
     * @return MarkdownUtils
     */
    public MarkdownUtils ul(String text, int level) {
        this.append(StringUtils.repeat("  ", level) + "- " + text);
        return this;
    }

    /**
     * 有序列表
     *
     * @param text
     * @return MarkdownUtils
     */
    public MarkdownUtils ol(String text) {
        this.append("1. " + text);
        return this;
    }

    /**
     * 有序列表 (子分类)
     *
     * @param text
     * @param level
     * @return MarkdownUtils
     */
    public MarkdownUtils ol(String text, int level) {
        this.append(StringUtils.repeat("   ", level) + "1. " + text);
        return this;
    }

    /**
     * 段落
     *
     * @param text
     * @return MarkdownUtils
     */
    public MarkdownUtils p(String text) {
        this.append(text);
        return this;
    }

    /**
     * 表格
     *
     * @param table
     * @return
     */
    public MarkdownUtils table(MarkdownTable table) {
        this.append(table.getMarkdown());
        return this;
    }
}
