package com.atlassian.confluence.service;

import com.atlassian.confluence.model.StateCheckboxModel;

public interface StateCheckboxService {
    boolean getState(String pageId, String username);
    StateCheckboxModel[] getStatesByPageId(String pageId);
    void setState(StateCheckboxModel stateCheckboxModel);
    void addState(String pageId, String username);
    void deleteState(StateCheckboxModel stateCheckboxModel);
}
