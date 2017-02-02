package net.masterthought.jenkins;

import hudson.model.Action;

//import net.masterthought.cucumber.ReportBuilder;

public abstract class SafeReportBaseAction implements Action {

	protected static final String PLUGIN_NAME = "insight-benchmark-report-plugin";
    protected static final String ICON_NAME = "/plugin/"+PLUGIN_NAME+"/icon.png";

    @Override
    public String getUrlName() {
//        return ReportBuilder.HOME_PAGE;
//    	 final String HOME_PAGE = "index.html";
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
