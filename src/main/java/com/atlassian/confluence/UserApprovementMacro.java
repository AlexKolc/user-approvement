package com.atlassian.confluence;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.content.render.xhtml.Renderer;
import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.core.ContentEntityObject;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.service.StateCheckboxService;
import com.atlassian.confluence.util.GeneralUtil;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.webresource.api.assembler.PageBuilderService;

import javax.inject.Inject;
import java.util.Map;

public class UserApprovementMacro implements Macro {

    private final ConfluenceActionSupport confluenceActionSupport;
    private final StateCheckboxService stateCheckboxService;
    private final PageBuilderService pageBuilderService;

    @Inject
    public UserApprovementMacro(StateCheckboxService stateCheckboxService, @ComponentImport PageBuilderService pageBuilderService) {
        this.stateCheckboxService = stateCheckboxService;
        this.confluenceActionSupport = GeneralUtil.newWiredConfluenceActionSupport();
        this.pageBuilderService = pageBuilderService;
    }

    public String execute(Map<String, String> params, String bodyContent, ConversionContext conversionContext) throws MacroExecutionException {

        String username = params.get("Username");
        String baseUrl = GeneralUtil.getGlobalSettings().getBaseUrl();
        String path = GeneralUtil.getGlobalSettings().getBaseUrl() + "/display/~" + username;
        String fullname = confluenceActionSupport.getUserFullName(username);
        String currentUsername = confluenceActionSupport.getAuthenticatedUser().getName();
        String userkey = confluenceActionSupport.getUserByName(username).getKey().toString();

        ContentEntityObject contentEntityObject = conversionContext.getEntity();
        Long contentId = contentEntityObject.getId();
        String pageId = contentId.toString();

        stateCheckboxService.addState(pageId, username);

        Map<String, Object> veloContext = MacroUtils.defaultVelocityContext();
        veloContext.put("username", username);
        veloContext.put("baseUrl", baseUrl);
        veloContext.put("path", path);
        veloContext.put("fullname", fullname);
        veloContext.put("currentUsername", currentUsername);
        veloContext.put("userkey", userkey);

        pageBuilderService.assembler().resources().requireWebResource("com.atlassian.confluence.modified-checkbox:user-approvement-resources");

        return VelocityUtils.getRenderedTemplate("templates/user-approvement.vm", veloContext);
    }

    public BodyType getBodyType() {
        return BodyType.NONE;
    }

    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }
}

