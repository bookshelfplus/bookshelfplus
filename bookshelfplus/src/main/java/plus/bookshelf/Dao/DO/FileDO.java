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
     * This field corresponds to the database column file_info.file_display_name
     *
     * @mbg.generated
     */
    private String fileDisplayName;

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
     * This field corresponds to the database column file_info.file_format
     *
     * @mbg.generated
     */
    private String fileFormat;

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
     * This field corresponds to the database column file_info.book_origin
     *
     * @mbg.generated
     */
    private String bookOrigin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_info.thumbnail
     *
     * @mbg.generated
     */
    private String thumbnail;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_info.file_create_at
     *
     * @mbg.generated
     */
    private Date fileCreateAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_info.file_modified_at
     *
     * @mbg.generated
     */
    private Date fileModifiedAt;

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
     * This field corresponds to the database column file_info.hash_sha1
     *
     * @mbg.generated
     */
    private String hashSha1;

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
     * This method returns the value of the database column file_info.file_display_name
     *
     * @return the value of file_info.file_display_name
     *
     * @mbg.generated
     */
    public String getFileDisplayName() {
        return fileDisplayName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.file_display_name
     *
     * @param fileDisplayName the value for file_info.file_display_name
     *
     * @mbg.generated
     */
    public void setFileDisplayName(String fileDisplayName) {
        this.fileDisplayName = fileDisplayName == null ? null : fileDisplayName.trim();
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
     * This method returns the value of the database column file_info.file_format
     *
     * @return the value of file_info.file_format
     *
     * @mbg.generated
     */
    public String getFileFormat() {
        return fileFormat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.file_format
     *
     * @param fileFormat the value for file_info.file_format
     *
     * @mbg.generated
     */
    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat == null ? null : fileFormat.trim();
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
     * This method returns the value of the database column file_info.book_origin
     *
     * @return the value of file_info.book_origin
     *
     * @mbg.generated
     */
    public String getBookOrigin() {
        return bookOrigin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.book_origin
     *
     * @param bookOrigin the value for file_info.book_origin
     *
     * @mbg.generated
     */
    public void setBookOrigin(String bookOrigin) {
        this.bookOrigin = bookOrigin == null ? null : bookOrigin.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_info.thumbnail
     *
     * @return the value of file_info.thumbnail
     *
     * @mbg.generated
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.thumbnail
     *
     * @param thumbnail the value for file_info.thumbnail
     *
     * @mbg.generated
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail == null ? null : thumbnail.trim();
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

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_info.file_modified_at
     *
     * @return the value of file_info.file_modified_at
     *
     * @mbg.generated
     */
    public Date getFileModifiedAt() {
        return fileModifiedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.file_modified_at
     *
     * @param fileModifiedAt the value for file_info.file_modified_at
     *
     * @mbg.generated
     */
    public void setFileModifiedAt(Date fileModifiedAt) {
        this.fileModifiedAt = fileModifiedAt;
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
     * This method returns the value of the database column file_info.hash_sha1
     *
     * @return the value of file_info.hash_sha1
     *
     * @mbg.generated
     */
    public String getHashSha1() {
        return hashSha1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_info.hash_sha1
     *
     * @param hashSha1 the value for file_info.hash_sha1
     *
     * @mbg.generated
     */
    public void setHashSha1(String hashSha1) {
        this.hashSha1 = hashSha1 == null ? null : hashSha1.trim();
    }
}