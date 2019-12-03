package com.example.reservutt.Models;

import java.util.Date;

public class TimeSlot {

    private Long slot;

    public TimeSlot() {
    }



    private Boolean status;

    private String Id;

    private String user;

    public TimeSlot(long slot, String id) {
        this.slot = slot;
        Id = id;
    }

    public TimeSlot(long slot, Boolean status, String id, String user) {
        this.slot = slot;
        this.status = status;
        Id = id;
        this.user = user;
    }


    public Long getSlot() {
        return slot;
    }

    public void setSlot(Long slot) {
        this.slot = slot;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

}
