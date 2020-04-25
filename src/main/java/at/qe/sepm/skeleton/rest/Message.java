package at.qe.sepm.skeleton.rest;

public class Message {

    private Long id;

    private String content;

    private String sender;

    private Long answerTo;

    private Message() {
        id = MessageService.getNextId();
    }

    public Message(String content, String sender) {
        this();
        this.content = content;
        this.sender = sender;
    }

    public Message(String content, String sender, Long answerTo) {
        this(content, sender);
        this.answerTo = answerTo;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public Long getAnswerTo() {
        return answerTo;
    }

}