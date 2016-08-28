package com.diptika.webyog.cloudmagicassignment.ui.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diptika.s on 27/08/16.
 */
public class Message {
    @SerializedName("subject")
    private String subject;
    @SerializedName("participants")
    private List<String> participants = new ArrayList<>();
    @SerializedName("preview")
    private String preview;
    @SerializedName("isRead")
    private boolean isRead;
    @SerializedName("isStarred")
    private boolean isStarred;
    @SerializedName("ts")
    private String timeStamp;
    @SerializedName("id")
    private int id;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
