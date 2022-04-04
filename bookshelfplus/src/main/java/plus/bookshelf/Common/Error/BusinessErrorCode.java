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
    OPERATION_NOT_ALLOWED(30001, "用户没有此操作的权限");


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
