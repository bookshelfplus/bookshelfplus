package plus.bookshelf.Common.Response;

public enum CommonReturnTypeStatus {
    SUCCESS("success"),
    FAILED("failed");

    private String str;

    private CommonReturnTypeStatus(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
