package at.qe.sepm.skeleton.model;


import java.util.ArrayList;
import java.util.List;

public enum RequestEnum {

    ACCEPTED ("Accepted"),
    DECLINED ("Declined"),
    OPEN ("Open");

    private final String description;

    RequestEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.getDescription();
    }
}
