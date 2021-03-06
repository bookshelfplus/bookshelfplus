package plus.bookshelf.Dao.DO;

import java.util.Date;

public class FileDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_info.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_info.book_id
     *
     * @mbg.generated
     */
    private Integer bookId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_info.file_name
     *
     * @mbg.generated
     */
    private String fileName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_info.file_ext
     *
     * @mbg.generated
     */
    private String fileExt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_info.file_size
     *
     * @mbg.generated
     */
    private Long fileSize;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_info.file_sha1
     *
     * @mbg.generated
     */
    private String fileSha1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_info.number_of_pages
     *
     * @mbg.generated
     */
    private Integer numberOfPages;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_info.watermark
     *
     * @mbg.generated
     */
    private Boolean watermark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_info.advertising
     *
     * @mbg.generated
     */
    private Boolean advertising;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_info.source
     *
     * @mbg.generated
     */
    private String source;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_info.file_create_at
     *
     * @mbg.generated
     */
    private Date fileCreateAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_info.id
     *
     * @return the value of file_info.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.id
     *
     * @param id the value for file_info.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_info.book_id
     *
     * @return the value of file_info.book_id
     *
     * @mbg.generated
     */
    public Integer getBookId() {
        return bookId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.book_id
     *
     * @param bookId the value for file_info.book_id
     *
     * @mbg.generated
     */
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_info.file_name
     *
     * @return the value of file_info.file_name
     *
     * @mbg.generated
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.file_name
     *
     * @param fileName the value for file_info.file_name
     *
     * @mbg.generated
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_info.file_ext
     *
     * @return the value of file_info.file_ext
     *
     * @mbg.generated
     */
    public String getFileExt() {
        return fileExt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.file_ext
     *
     * @param fileExt the value for file_info.file_ext
     *
     * @mbg.generated
     */
    public void setFileExt(String fileExt) {
        this.fileExt = fileExt == null ? null : fileExt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_info.file_size
     *
     * @return the value of file_info.file_size
     *
     * @mbg.generated
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.file_size
     *
     * @param fileSize the value for file_info.file_size
     *
     * @mbg.generated
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_info.file_sha1
     *
     * @return the value of file_info.file_sha1
     *
     * @mbg.generated
     */
    public String getFileSha1() {
        return fileSha1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.file_sha1
     *
     * @param fileSha1 the value for file_info.file_sha1
     *
     * @mbg.generated
     */
    public void setFileSha1(String fileSha1) {
        this.fileSha1 = fileSha1 == null ? null : fileSha1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_info.number_of_pages
     *
     * @return the value of file_info.number_of_pages
     *
     * @mbg.generated
     */
    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.number_of_pages
     *
     * @param numberOfPages the value for file_info.number_of_pages
     *
     * @mbg.generated
     */
    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_info.watermark
     *
     * @return the value of file_info.watermark
     *
     * @mbg.generated
     */
    public Boolean getWatermark() {
        return watermark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.watermark
     *
     * @param watermark the value for file_info.watermark
     *
     * @mbg.generated
     */
    public void setWatermark(Boolean watermark) {
        this.watermark = watermark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_info.advertising
     *
     * @return the value of file_info.advertising
     *
     * @mbg.generated
     */
    public Boolean getAdvertising() {
        return advertising;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.advertising
     *
     * @param advertising the value for file_info.advertising
     *
     * @mbg.generated
     */
    public void setAdvertising(Boolean advertising) {
        this.advertising = advertising;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_info.source
     *
     * @return the value of file_info.source
     *
     * @mbg.generated
     */
    public String getSource() {
        return source;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.source
     *
     * @param source the value for file_info.source
     *
     * @mbg.generated
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_info.file_create_at
     *
     * @return the value of file_info.file_create_at
     *
     * @mbg.generated
     */
    public Date getFileCreateAt() {
        return fileCreateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.file_create_at
     *
     * @param fileCreateAt the value for file_info.file_create_at
     *
     * @mbg.generated
     */
    public void setFileCreateAt(Date fileCreateAt) {
        this.fileCreateAt = fileCreateAt;
    }
}