package plus.bookshelf.Dao.DO;

public class FileObjectDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_object_info.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_object_info.file_id
     *
     * @mbg.generated
     */
    private Integer fileId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_object_info.storage_medium_type
     *
     * @mbg.generated
     */
    private String storageMediumType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_object_info.file_path
     *
     * @mbg.generated
     */
    private String filePath;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_object_info.file_pwd
     *
     * @mbg.generated
     */
    private String filePwd;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_object_info.file_share_code
     *
     * @mbg.generated
     */
    private String fileShareCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_object_info.additional_fields
     *
     * @mbg.generated
     */
    private String additionalFields;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_object_info.id
     *
     * @return the value of file_object_info.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_object_info.id
     *
     * @param id the value for file_object_info.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_object_info.file_id
     *
     * @return the value of file_object_info.file_id
     *
     * @mbg.generated
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_object_info.file_id
     *
     * @param fileId the value for file_object_info.file_id
     *
     * @mbg.generated
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_object_info.storage_medium_type
     *
     * @return the value of file_object_info.storage_medium_type
     *
     * @mbg.generated
     */
    public String getStorageMediumType() {
        return storageMediumType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_object_info.storage_medium_type
     *
     * @param storageMediumType the value for file_object_info.storage_medium_type
     *
     * @mbg.generated
     */
    public void setStorageMediumType(String storageMediumType) {
        this.storageMediumType = storageMediumType == null ? null : storageMediumType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_object_info.file_path
     *
     * @return the value of file_object_info.file_path
     *
     * @mbg.generated
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_object_info.file_path
     *
     * @param filePath the value for file_object_info.file_path
     *
     * @mbg.generated
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_object_info.file_pwd
     *
     * @return the value of file_object_info.file_pwd
     *
     * @mbg.generated
     */
    public String getFilePwd() {
        return filePwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_object_info.file_pwd
     *
     * @param filePwd the value for file_object_info.file_pwd
     *
     * @mbg.generated
     */
    public void setFilePwd(String filePwd) {
        this.filePwd = filePwd == null ? null : filePwd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_object_info.file_share_code
     *
     * @return the value of file_object_info.file_share_code
     *
     * @mbg.generated
     */
    public String getFileShareCode() {
        return fileShareCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_object_info.file_share_code
     *
     * @param fileShareCode the value for file_object_info.file_share_code
     *
     * @mbg.generated
     */
    public void setFileShareCode(String fileShareCode) {
        this.fileShareCode = fileShareCode == null ? null : fileShareCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_object_info.additional_fields
     *
     * @return the value of file_object_info.additional_fields
     *
     * @mbg.generated
     */
    public String getAdditionalFields() {
        return additionalFields;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_object_info.additional_fields
     *
     * @param additionalFields the value for file_object_info.additional_fields
     *
     * @mbg.generated
     */
    public void setAdditionalFields(String additionalFields) {
        this.additionalFields = additionalFields == null ? null : additionalFields.trim();
    }
}