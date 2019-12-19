package com.example.reservutt.Models;

import com.google.firebase.Timestamp;

public class BookingInformation {
    private String user, userId, salleName, salleId, time;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    private Long slot;
    private Boolean done;
    private Timestamp timestamp;

    public Boolean getDone() {
        return done = false;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public BookingInformation() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSalleName() {
        return salleName;
    }

    public void setSalleName(String salleName) {
        this.salleName = salleName;
    }

    public String getSalleId() {
        return salleId;
    }

    public void setSalleId(String salleId) {
        this.salleId = salleId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getSlot() {
        return slot;
    }

    public void setSlot(Long slot) {
        this.slot = slot;
    }

    public BookingInformation(String user, String userId, String salleName, String salleId, String time, Long slot) {
        this.user = user;
        this.userId = userId;
        this.salleName = salleName;
        this.salleId = salleId;
        this.time = time;
        this.slot = slot;
    }
}
