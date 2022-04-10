package plus.bookshelf.Service.Model;

import lombok.Data;
import plus.bookshelf.Common.Enum.ScheduleTaskActionEnum;

import java.util.Date;

@Data
public class ScheduleTaskModel {

    Integer id;

    Date createTime;

    Date scheduleTime;

    ScheduleTaskActionEnum action;

    String data;

    String taskGuid;

    Integer associatedUserId;

    Byte failTime;
}
