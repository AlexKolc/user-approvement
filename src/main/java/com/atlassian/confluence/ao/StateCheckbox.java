package com.atlassian.confluence.ao;

import net.java.ao.Entity;
import net.java.ao.schema.Default;

public interface StateCheckbox extends Entity {
    String getPageId();
    void setPageId(String username);

    String getUsername();
    void setUsername(String username);

    @Default("false")
    boolean getState();
    void setState(boolean state);
}