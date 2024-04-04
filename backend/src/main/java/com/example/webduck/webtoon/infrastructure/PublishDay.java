package com.example.webduck.webtoon.infrastructure;

public enum PublishDay {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY,
    DAILY,
    FINISHED;

    public static PublishDay fromString(String day) {
        switch (day.toLowerCase()) {
            case "mon":
                return MONDAY;
            case "tue":
                return TUESDAY;
            case "wed":
                return WEDNESDAY;
            case "thu":
                return THURSDAY;
            case "fri":
                return FRIDAY;
            case "sat":
                return SATURDAY;
            case "sun":
                return SUNDAY;
            case "finished":
                return FINISHED;
            case "naverdaily" :
                return DAILY;
            default:
                throw new IllegalArgumentException("Unknown day: " + day); // todo
        }
    }

}
