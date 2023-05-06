package com.example.picnic.screens.homeContent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GetHomeTaskContentUseCase {

    public List<ScheduledTaskData> get() {
        List<ScheduledTaskData> dataList = new ArrayList<>();

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String ampm = "am";

        if (hour > 12) {
            hour -= 12;
            ampm = "pm";
        }

        for (int i = hour; i <= 12; i++) {

            int ithHour = hour + i;
            String scheduledTime = (ithHour + 1) + ampm;

            dataList.add(new ScheduledTaskData("", scheduledTime, ""));
        }

        return dataList;
    }
}
