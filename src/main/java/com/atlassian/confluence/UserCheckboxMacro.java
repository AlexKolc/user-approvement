package com.atlassian.confluence;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
//import com.atlassian.confluence.service.UserService;
import com.atlassian.confluence.user.ConfluenceUser;
import com.atlassian.confluence.util.GeneralUtil;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.user.UserKey;
import com.atlassian.user.EntityException;
import com.atlassian.user.User;
import com.atlassian.user.UserManager;

import javax.inject.Inject;
import java.util.Map;

public class UserCheckboxMacro implements Macro {
//    @ComponentImport
//    private final UserManager userManager;

    private final ConfluenceActionSupport confluenceActionSupport;

    public UserCheckboxMacro(/*UserManager userManager, ConfluenceActionSupport confluenceActionSupport*/) {
//        this.userManager = userManager;
        this.confluenceActionSupport = GeneralUtil.newWiredConfluenceActionSupport();
    }

    public String execute(Map<String, String> params, String bodyContent, ConversionContext conversionContext) throws MacroExecutionException {
        final String username = params.get("Username");
        String path = GeneralUtil.getGlobalSettings().getBaseUrl() + "/display/~" + username;
        StringBuilder builder = new StringBuilder();
        String fullName = confluenceActionSupport.getUserFullName(username);//userManager.getUser(username);
        String currentUsername = confluenceActionSupport.getAuthenticatedUser().getName();
        UserKey key = confluenceActionSupport.getUserByName(username).getKey();
        //String curUsername = userService.getRemoteUsername();
        builder.append("<div>");
        if (!username.equals(currentUsername)) {
            builder.append("<input type=\"checkbox\" id=\"user-checkbox\" name=\"user-checkbox\" disabled>");
            builder.append("<a userkey=\"" + key.toString() + "\" data-linked-resource-default-alias=\"" + fullName + "\" " +
                    "class=\"confluence-userlink user-mention userlink-0\" data-username=\"" + username + "\" data-linked-resource-version=\"2\" " +
                    "data-linked-resource-type=\"userinfo\" href=\"/confluence/display/~" + username + "\" data-base-url=\"" + GeneralUtil.getGlobalSettings().getBaseUrl() + "\">" + fullName + "</a></li>");
        } else {
            builder.append("<input type=\"checkbox\" id=\"user-checkbox\" name=\"user-checkbox\">");
            builder.append("<label for=\"user-checkbox\"> ").append("<a userkey=\"" + key.toString() + "\" data-linked-resource-default-alias=\"" + fullName + "\" " +
                    "class=\"confluence-link confluence-userlink user-mention current-user-mention\" data-username=\"" + username + "\" data-linked-resource-version=\"2\" " +
                    "data-linked-resource-type=\"userinfo\" href=\"/confluence/display/~" + username + "\" data-base-url=\"" + GeneralUtil.getGlobalSettings().getBaseUrl() + "\">" + fullName + "</a>").append("</label>");
        }


        builder.append("<div>");

        return builder.toString();


    }

    public BodyType getBodyType() {
        return BodyType.NONE;
    }

    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }
}

