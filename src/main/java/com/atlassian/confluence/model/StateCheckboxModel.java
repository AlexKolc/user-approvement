package com.atlassian.confluence.model;

import com.atlassian.confluence.ao.StateCheckbox;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StateCheckboxModel {
    @XmlElement
    private int id;
    @XmlElement
    private String pageId;
    @XmlElement
    private String username;
    @XmlElement
    private boolean state;

    private StateCheckboxModel() {}

    public StateCheckboxModel(StateCheckbox stateCheckbox) {
        this.id = stateCheckbox.getID();
        this.pageId = stateCheckbox.getPageId();
        this.username = stateCheckbox.getUsername();
        this.state = stateCheckbox.getState();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
