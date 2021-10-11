package com.atlassian.confluence.rest;

import com.atlassian.confluence.ao.StateCheckbox;
import com.atlassian.confluence.model.StateCheckboxModel;
import com.atlassian.confluence.service.StateCheckboxService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/state")
public class StateCheckboxRest {
    private final StateCheckboxService stateCheckboxService;

    @Inject
    public StateCheckboxRest(StateCheckboxService stateCheckboxService) {
        this.stateCheckboxService = stateCheckboxService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getState")
    public boolean getState(@Context HttpServletRequest request) {
        String pageId = request.getParameter("page_id");
        String username = request.getParameter("username");
        return stateCheckboxService.getState(pageId, username);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getStatesByPageId")
    public StateCheckboxModel[] getStatesByPageId(@Context HttpServletRequest request) {
        String pageId = request.getParameter("page_id");
        return stateCheckboxService.getStatesByPageId(pageId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/setState")
    public void setState(StateCheckboxModel stateCheckboxModel) {
        stateCheckboxService.setState(stateCheckboxModel);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addState")
    public void addState(@Context HttpServletRequest request) {
        String pageId = request.getParameter("page_id");
        String username = request.getParameter("username");
        stateCheckboxService.addState(pageId, username);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/deleteState")
    public void deleteLending(StateCheckboxModel stateCheckboxModel) {
        stateCheckboxService.deleteState(stateCheckboxModel);
    }
}