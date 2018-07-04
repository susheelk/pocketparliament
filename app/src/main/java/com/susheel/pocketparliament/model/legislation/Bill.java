package com.susheel.pocketparliament.model.legislation;




import com.fasterxml.jackson.annotation.JsonFilter;
import com.susheel.pocketparliament.model.Person;

import java.util.Date;
import java.util.List;

/**
 * @author Susheel Kona
 */
@JsonFilter("includer")
public class Bill {
    private int id;
    private String session; // ie 42-1
    private String number; //ie C-2

    private String title;
    private String shortTitle;
    private String billType;

    private Date dateIntroduced;
    private Date dateLastUpdated;
    private Event lastMajorEvent; // Only includes major events, unlike the events list
    private List<Event> events;

    private List<Publication> publications;
    private Person sponsor;

    private boolean law;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSession() {
        return session;

    }

    public void setSession(String session) {
        this.session = session;
    }


    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public Date getDateIntroduced() {
        return dateIntroduced;
    }

    public void setDateIntroduced(Date dateIntroduced) {
        this.dateIntroduced = dateIntroduced;
    }

    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Event getLastMajorEvent() {
        return lastMajorEvent;
    }

    public void setLastMajorEvent(Event lastMajorEvent) {
        this.lastMajorEvent = lastMajorEvent;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    public Person getSponsor() {
        return sponsor;
    }

    public void setSponsor(Person sponsor) {
        this.sponsor = sponsor;
    }

    public boolean isLaw() {
        return law;
    }

    public void setLaw(boolean law) {
        this.law = law;
    }
}
