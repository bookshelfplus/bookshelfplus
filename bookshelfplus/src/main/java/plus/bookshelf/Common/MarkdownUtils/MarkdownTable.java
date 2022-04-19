package plus.bookshelf.Common.MarkdownUtils;

import java.util.ArrayList;
import java.util.List;

public class MarkdownTable {

    public enum Alignment {
        LEFT(" :--- "), CENTER(" :--: "), RIGHT(" ---: ");
        public final String format;

        Alignment(String format) {
            this.format = format;
        }

        public String toString() {
            return format;
        }
    }

    // 表格的列数
    private Integer numOfColumns = 0;

    // 表格的行
    private List<String[]> rows;

    // 表格的表头
    private String[] headers;

    // 表格的样式
    private Alignment[] alignments;

    private MarkdownTable(Integer numOfColumns) {
        this.numOfColumns = numOfColumns;
        headers = new String[numOfColumns];
        alignments = new Alignment[numOfColumns];
        rows = new ArrayList<>();
    }

    /**
     * 创建一个表格
     * <p>
     * 创建表格前需要先指定表格的列数，然后添加行。
     * 创建表格后，表格列数不可再修改。
     * 可以调用toString()方法获取表格的Markdown字符串。
     *
     * @param numOfColumns
     * @return
     */
    public static MarkdownTable create(Integer numOfColumns) {
        return new MarkdownTable(numOfColumns);
    }

    /**
     * 设置表格的表头
     *
     * @param header
     * @return
     */
    public MarkdownTable setHeader(Object... header) {
        if (header.length != numOfColumns) {
            throw new IllegalArgumentException("表格的列数不匹配");
        }

        int i = 0;
        for (Object o : header) {
            headers[i++] = o.toString();
        }
        return this;
    }

    /**
     * 设置对齐方式
     *
     * @param alignments
     * @return
     */
    public MarkdownTable setAlignment(Alignment... alignments) {
        if (alignments.length != numOfColumns) {
            throw new IllegalArgumentException("表格的列数不匹配");
        }
        this.alignments = alignments;
        return this;
    }

    public MarkdownTable setAlignment(Alignment alignment) {
        for (int i = 0; i < numOfColumns; i++) {
            alignments[i] = alignment;
        }
        return this;
    }

    /**
     * 添加一行
     *
     * @param row
     * @return
     */
    public MarkdownTable addRow(String... row) {
        if (row.length != numOfColumns) {
            throw new IllegalArgumentException("表格的列数不匹配");
        }
        rows.add(row);
        return this;
    }

    /**
     * 获取表格的Markdown字符串
     *
     * @return
     */
    @Override
    public String toString() {
        return toStringBuilder().toString();
    }

    public StringBuilder toStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder();

        // 表头
        stringBuilder.append("| ");
        String headerStr = String.join(" | ", headers);
        stringBuilder.append(headerStr);
        stringBuilder.append(" |\n");

        // 分割线
        for (int i = 0; i < alignments.length; i++) {
            stringBuilder.append("|");
            String format = alignments[i].toString();
            stringBuilder.append(format);
        }
        stringBuilder.append("|\n");

        // 表格内容
        for (String[] row : rows) {
            stringBuilder.append("|");

            // String rowStr = String.join(" | ", row);
            // stringBuilder.append(rowStr);
            // stringBuilder.append(" |\n");

            for (String o : row) {
                stringBuilder.append(" ");
                stringBuilder.append(o.replace("\n", "<br>")
                        .replace("|", "\\|"));
                stringBuilder.append(" |");
            }
            stringBuilder.append("\n");
        }

        // 删除最后一个换行符
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("\n"));
        return stringBuilder;
    }
}
