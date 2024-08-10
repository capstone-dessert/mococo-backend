package capstone.dessert.mococobackend.entity;

import capstone.dessert.mococobackend.exception.ScheduleNotFoundException;
import lombok.Getter;

@Getter
public enum Schedule {

    SIMPLE_OUTING("간단 외출"),
    GO_TO_SCHOOL("등교"),
    PRESENTATION("발표"),
    DATE("데이트"),
    GO_TO_WORK("출근"),
    INTERVIEW("면접"),
    EXERCISE("운동"),
    PARTY("파티"),
    WEDDING("결혼식");

    private final String displayName;

    Schedule(String displayName) {
        this.displayName = displayName;
    }

    public static Schedule fromDisplayName(String displayName) {
        for (Schedule schedule : Schedule.values()) {
            if (schedule.getDisplayName().equals(displayName)) {
                return schedule;
            }
        }
        throw new ScheduleNotFoundException(displayName);
    }
}
