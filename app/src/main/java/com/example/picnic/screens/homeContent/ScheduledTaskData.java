package com.example.picnic.screens.homeContent;

public class ScheduledTaskData {

    private final String heading;
    private final String time;
    private final String description;

    public ScheduledTaskData(String heading, String time, String description) {
        this.heading = heading;
        this.time = time;
        this.description = description;
    }

    public String getHeading() {
        return heading;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }
}