package plus.bookshelf.Dao.DO;

public class UserDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.username
     *
     * @mbg.generated
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.encript_pwd
     *
     * @mbg.generated
     */
    private String encriptPwd;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.nickname
     *
     * @mbg.generated
     */
    private String nickname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.group
     *
     * @mbg.generated
     */
    private String group;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.avatar
     *
     * @mbg.generated
     */
    private String avatar;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.email
     *
     * @mbg.generated
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.phone
     *
     * @mbg.generated
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.weixin_third_party_auth_code
     *
     * @mbg.generated
     */
    private String weixinThirdPartyAuthCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.qq_third_party_auth_code
     *
     * @mbg.generated
     */
    private String qqThirdPartyAuthCode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.id
     *
     * @return the value of user_info.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.id
     *
     * @param id the value for user_info.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.username
     *
     * @return the value of user_info.username
     *
     * @mbg.generated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.username
     *
     * @param username the value for user_info.username
     *
     * @mbg.generated
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.encript_pwd
     *
     * @return the value of user_info.encript_pwd
     *
     * @mbg.generated
     */
    public String getEncriptPwd() {
        return encriptPwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.encript_pwd
     *
     * @param encriptPwd the value for user_info.encript_pwd
     *
     * @mbg.generated
     */
    public void setEncriptPwd(String encriptPwd) {
        this.encriptPwd = encriptPwd == null ? null : encriptPwd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.nickname
     *
     * @return the value of user_info.nickname
     *
     * @mbg.generated
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.nickname
     *
     * @param nickname the value for user_info.nickname
     *
     * @mbg.generated
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.group
     *
     * @return the value of user_info.group
     *
     * @mbg.generated
     */
    public String getGroup() {
        return group;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.group
     *
     * @param group the value for user_info.group
     *
     * @mbg.generated
     */
    public void setGroup(String group) {
        this.group = group == null ? null : group.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.avatar
     *
     * @return the value of user_info.avatar
     *
     * @mbg.generated
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.avatar
     *
     * @param avatar the value for user_info.avatar
     *
     * @mbg.generated
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.email
     *
     * @return the value of user_info.email
     *
     * @mbg.generated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.email
     *
     * @param email the value for user_info.email
     *
     * @mbg.generated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.phone
     *
     * @return the value of user_info.phone
     *
     * @mbg.generated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.phone
     *
     * @param phone the value for user_info.phone
     *
     * @mbg.generated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.weixin_third_party_auth_code
     *
     * @return the value of user_info.weixin_third_party_auth_code
     *
     * @mbg.generated
     */
    public String getWeixinThirdPartyAuthCode() {
        return weixinThirdPartyAuthCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.weixin_third_party_auth_code
     *
     * @param weixinThirdPartyAuthCode the value for user_info.weixin_third_party_auth_code
     *
     * @mbg.generated
     */
    public void setWeixinThirdPartyAuthCode(String weixinThirdPartyAuthCode) {
        this.weixinThirdPartyAuthCode = weixinThirdPartyAuthCode == null ? null : weixinThirdPartyAuthCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.qq_third_party_auth_code
     *
     * @return the value of user_info.qq_third_party_auth_code
     *
     * @mbg.generated
     */
    public String getQqThirdPartyAuthCode() {
        return qqThirdPartyAuthCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.qq_third_party_auth_code
     *
     * @param qqThirdPartyAuthCode the value for user_info.qq_third_party_auth_code
     *
     * @mbg.generated
     */
    public void setQqThirdPartyAuthCode(String qqThirdPartyAuthCode) {
        this.qqThirdPartyAuthCode = qqThirdPartyAuthCode == null ? null : qqThirdPartyAuthCode.trim();
    }
}