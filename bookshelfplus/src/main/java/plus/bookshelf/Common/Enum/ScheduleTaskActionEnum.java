package plus.bookshelf.Common.Enum;

public enum ScheduleTaskActionEnum {
    CHECK_FILE_IS_UPLOADED("checkFileIsUploaded");

    private String action;

    ScheduleTaskActionEnum(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
