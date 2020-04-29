package at.qe.sepm.skeleton.rest;

public class Message {

    private Long id;
    private String macAddress;
    private String historyReadTime;
    private String history;

    private Message() {
        id = MessageService.getNextId();
    }

    public Message(String macAddress, String historyReadTime, String history) {
        this();
        this.macAddress = macAddress;
        this.historyReadTime = historyReadTime;
        this.history = history;
    }

    public Long getId() {
        return id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getHistoryReadTime() {
        return historyReadTime;
    }

    public String getHistory() {
        return history;
    }
}