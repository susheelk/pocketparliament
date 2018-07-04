package com.susheel.pocketparliament.model.legislation;

import java.util.Date;

/**
 * @author Susheel Kona
 */

public class Event {
    private String status;
    private int id;
    private Date date;
    private String chamber;

    public Event(String status, int id, Date date, String chamber) {
        this.status = status;
        this.id = id;
        this.date = date;
        this.chamber = chamber;
    }

    public Event(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }
}
