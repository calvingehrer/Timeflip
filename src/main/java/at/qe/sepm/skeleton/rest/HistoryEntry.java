package at.qe.sepm.skeleton.rest;

import java.util.Date;

public class HistoryEntry {
    public int battery;
    private Long id;
    private String macAddress;
    private int facet;
    private Date start;
    private Date end;
    private int seconds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getFacet() {
        return facet;
    }

    public void setFacet(int facet) {
        this.facet = facet;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    int getSeconds() {
        return seconds;
    }

    void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }
}
