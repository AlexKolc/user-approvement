package com.atlassian.confluence.service.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.confluence.ao.StateCheckbox;
import com.atlassian.confluence.model.StateCheckboxModel;
import com.atlassian.confluence.service.StateCheckboxService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import net.java.ao.Query;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class StateCheckboxServiceImpl implements StateCheckboxService {
    @ComponentImport
    private final ActiveObjects ao;

    @Inject
    public StateCheckboxServiceImpl(ActiveObjects ao) {
        this.ao = ao;
    }

    @Override
    public boolean getState(String pageId, String username) {
        StateCheckbox[] states = ao.find(StateCheckbox.class, Query.select().where("PAGE_ID LIKE ? AND USERNAME LIKE ?", pageId, username));
//        if (states.length == 0)
        return states[0].getState();
    }

    @Override
    public StateCheckboxModel[] getStatesByPageId(String pageId) {
        StateCheckbox[] states = ao.find(StateCheckbox.class, Query.select().where("PAGE_ID LIKE ?", pageId));
//        StateCheckbox[] states = ao.find(StateCheckbox.class);
        StateCheckboxModel[] stateModels = new StateCheckboxModel[states.length];
        for (int i = 0; i < states.length; i++) {
            stateModels[i] = new StateCheckboxModel(states[i]);
        }
        return stateModels;
    }

    @Override
    public void setState(StateCheckboxModel stateCheckboxModel) {
        StateCheckbox[] findState = ao.find(StateCheckbox.class, Query.select().where("PAGE_ID LIKE ? AND USERNAME LIKE ?", stateCheckboxModel.getPageId(), stateCheckboxModel.getUsername()));

        StateCheckbox stateCheckbox;
        if (findState.length != 0) {
            stateCheckbox = findState[0];
            stateCheckbox.setState(stateCheckboxModel.getState());
        } else {
            stateCheckbox = ao.create(StateCheckbox.class);
            stateCheckbox.setPageId(stateCheckboxModel.getPageId());
            stateCheckbox.setUsername(stateCheckboxModel.getUsername());
            stateCheckbox.setState(false);
        }
        stateCheckbox.save();
    }

    @Override
    public void addState(String pageId, String username) {
        StateCheckbox[] findState = ao.find(StateCheckbox.class, Query.select().where("PAGE_ID LIKE ? AND USERNAME LIKE ?", pageId, username));

        StateCheckbox stateCheckbox;
        if (findState.length != 0) {
            return;
        } else {
            stateCheckbox = ao.create(StateCheckbox.class);
            stateCheckbox.setPageId(pageId);
            stateCheckbox.setUsername(username);
            stateCheckbox.setState(false);
        }
        stateCheckbox.save();
    }

    @Override
    public void deleteState(StateCheckboxModel stateCheckboxModel) {
        StateCheckbox[] stateCheckbox = ao.find(StateCheckbox.class, Query.select().where("PAGE_ID LIKE ? AND USERNAME LIKE ?", stateCheckboxModel.getPageId(), stateCheckboxModel.getUsername()));
        if (stateCheckbox.length != 0) {
            ao.delete(stateCheckbox[0]);
        }
    }
}
