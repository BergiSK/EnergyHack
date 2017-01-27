package com.example.ideapad.testapp.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by IdeaPad on 26.1.2017.
 */

public class TimeMapObject {
    @JsonProperty("time")
    private String time;
    @JsonProperty("milliseconds_since_epoch")
    private Long millis;
    @JsonProperty("date")
    private String date;

    public TimeMapObject(String time, Long millis, String date) {
        this.time = time;
        this.millis = millis;
        this.date = date;
    }

    public TimeMapObject() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getMillis() {
        return millis;
    }

    public void setMillis(Long millis) {
        this.millis = millis;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
