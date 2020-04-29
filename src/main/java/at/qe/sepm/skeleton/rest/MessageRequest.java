package at.qe.sepm.skeleton.rest;

public class MessageRequest {

    private String macAddress;
    private String historyReadTime;
    private String history;

    public MessageRequest() {
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getHistoryReadTime() {
        return historyReadTime;
    }

    public void setHistoryReadTime(String historyReadTime) {
        this.historyReadTime = historyReadTime;
    }


    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}