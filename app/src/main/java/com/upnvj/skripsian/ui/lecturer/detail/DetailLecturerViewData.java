package com.upnvj.skripsian.ui.lecturer.detail;

public class DetailLecturerViewData {

    private String countScheduleMonday;
    private String countScheduleTuesday;
    private String countScheduleWednesday;
    private String countScheduleThursday;
    private String countScheduleFriday;

    public DetailLecturerViewData() {
        countScheduleMonday = "-";
        countScheduleTuesday = "-";
        countScheduleWednesday = "-";
        countScheduleThursday = "-";
        countScheduleFriday = "-";
    }

    public DetailLecturerViewData(String countScheduleMonday, String countScheduleTuesday, String countScheduleWednesday, String countScheduleThursday, String countScheduleFriday) {
        this.countScheduleMonday = countScheduleMonday;
        this.countScheduleTuesday = countScheduleTuesday;
        this.countScheduleWednesday = countScheduleWednesday;
        this.countScheduleThursday = countScheduleThursday;
        this.countScheduleFriday = countScheduleFriday;
    }

    public String getCountScheduleMonday() {
        return countScheduleMonday;
    }

    public void setCountScheduleMonday(String countScheduleMonday) {
        this.countScheduleMonday = countScheduleMonday;
    }

    public String getCountScheduleTuesday() {
        return countScheduleTuesday;
    }

    public void setCountScheduleTuesday(String countScheduleTuesday) {
        this.countScheduleTuesday = countScheduleTuesday;
    }

    public String getCountScheduleWednesday() {
        return countScheduleWednesday;
    }

    public void setCountScheduleWednesday(String countScheduleWednesday) {
        this.countScheduleWednesday = countScheduleWednesday;
    }

    public String getCountScheduleThursday() {
        return countScheduleThursday;
    }

    public void setCountScheduleThursday(String countScheduleThursday) {
        this.countScheduleThursday = countScheduleThursday;
    }

    public String getCountScheduleFriday() {
        return countScheduleFriday;
    }

    public void setCountScheduleFriday(String countScheduleFriday) {
        this.countScheduleFriday = countScheduleFriday;
    }

    public enum ScheduleFilter {

        DAY_1(0), DAY_2(1), DAY_3(2), DAY_4(3), DAY_5(4);

        public int index;

        ScheduleFilter(int index) {
            this.index = index;
        }
    }

}
