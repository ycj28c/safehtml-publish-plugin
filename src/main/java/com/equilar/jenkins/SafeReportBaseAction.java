package com.equilar.jenkins;

import hudson.model.Action;
import com.equilar.jenkins.Messages;

public abstract class SafeReportBaseAction implements Action {

	protected static final String ICON_NAME = "/plugin/" + SafeReportPublisher.PLUGIN_NAME + "/icon.png";

    @Override
    public String getUrlName() {
    	 return SafeReportPublisher.WRAPPER_NAME;
    }

    @Override
    public String getIconFileName() {
        return ICON_NAME;
    }

    @Override
    public String getDisplayName() {
        return Messages.Plugin_DisplayName();
    }
}
