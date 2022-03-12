package plus.bookshelf.Dao.DO;

public class BookDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.id
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.book_name
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    private String bookName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.category_id
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    private Integer categoryId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.publishing_house
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    private String publishingHouse;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.language
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    private String language;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.copyright
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    private String copyright;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.is_delete
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    private Boolean isDelete;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.thumbnail
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    private String thumbnail;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.author
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    private String author;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.description
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    private String description;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.id
     *
     * @return the value of book_info.id
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.id
     *
     * @param id the value for book_info.id
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.book_name
     *
     * @return the value of book_info.book_name
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.book_name
     *
     * @param bookName the value for book_info.book_name
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.category_id
     *
     * @return the value of book_info.category_id
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.category_id
     *
     * @param categoryId the value for book_info.category_id
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.publishing_house
     *
     * @return the value of book_info.publishing_house
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public String getPublishingHouse() {
        return publishingHouse;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.publishing_house
     *
     * @param publishingHouse the value for book_info.publishing_house
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse == null ? null : publishingHouse.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.language
     *
     * @return the value of book_info.language
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public String getLanguage() {
        return language;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.language
     *
     * @param language the value for book_info.language
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.copyright
     *
     * @return the value of book_info.copyright
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.copyright
     *
     * @param copyright the value for book_info.copyright
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright == null ? null : copyright.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.is_delete
     *
     * @return the value of book_info.is_delete
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.is_delete
     *
     * @param isDelete the value for book_info.is_delete
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.thumbnail
     *
     * @return the value of book_info.thumbnail
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.thumbnail
     *
     * @param thumbnail the value for book_info.thumbnail
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail == null ? null : thumbnail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.author
     *
     * @return the value of book_info.author
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public String getAuthor() {
        return author;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.author
     *
     * @param author the value for book_info.author
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.description
     *
     * @return the value of book_info.description
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.description
     *
     * @param description the value for book_info.description
     *
     * @mbg.generated Sat Mar 12 21:38:13 SGT 2022
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}