package plus.bookshelf.Common.Response;

import lombok.Data;

@Data
public class CommonReturnType {
    // 表明对应请求的返回处理结果 "success" 或 "fail"
    private String Status;

    // 若 status == "success" 则data内返回前端需要的JSON数据
    // 若 status == "fail" 则data内使用通用的错误码格式
    private Object Data;

    // 定义一个通用的创建方法
    public static CommonReturnType create(Object result) {
        return CommonReturnType.create(result, CommonReturnTypeStatus.SUCCESS);
    }

    public static CommonReturnType create(Object result, CommonReturnTypeStatus status) {
        CommonReturnType type = new CommonReturnType();
        type.setData(result);
        type.setStatus(status.toString());
        return type;
    }
}

