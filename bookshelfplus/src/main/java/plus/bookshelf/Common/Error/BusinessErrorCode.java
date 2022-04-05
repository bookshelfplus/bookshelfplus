package plus.bookshelf.Common.Error;

public enum BusinessErrorCode implements CommonError {
    // 通用错误类型 10001
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    UNKNOWN_ERROR(10002, "未知错误"),

    // 20000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_FAILED(20002, "用户手机号或密码不正确"),
    USER_NOT_LOGIN(20003, "用户还未登录"),
    USER_TOKEN_EXPIRED(20004, "用户令牌过期"),
    USER_ALREADY_EXIST(20005, "用户已存在"),

    // 30000开头为权限相关错误定义
    OPERATION_NOT_ALLOWED(30001, "用户没有此操作的权限"),

    // 40000开头为第三方登录相关错误定义
    THIRD_PARTY_LOGIN_FAIL(40001, "第三方登录失败"),
    THIRD_PARTY_ACCOUNT_ALREADY_BOUND(40002, "该账号已被其他账号绑定"),
    THIRD_PARTY_UNBIND_FAIL(40003, "第三方账号解绑失败"),

    // 50000开头为书籍相关错误定义
    BOOK_NOT_EXIST(50001, "书籍不存在"),
    BOOK_FAVORITES_ALREADY_EXIST(50002, "书籍已经在收藏夹中"),
    BOOK_FAVORITES_NOT_EXIST(50003, "书籍不在收藏夹中"),

    // 占位
    PLACE_HOLDER(99999, "这是一个占位符错误");

    private BusinessErrorCode(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        return this;
    }
}
