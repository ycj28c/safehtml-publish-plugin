package io.github.ycj28c;

import hudson.model.Action;
import io.github.ycj28c.Messages;

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
