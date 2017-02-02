package net.masterthought.jenkins;

import hudson.model.Action;

//import net.masterthought.cucumber.ReportBuilder;

public abstract class SafeReportBaseAction implements Action {

    protected static final String ICON_NAME = "/plugin/cucumber-reports/icon.png";

    @Override
    public String getUrlName() {
//        return ReportBuilder.HOME_PAGE;
    	 final String HOME_PAGE = "overview-features.html";
    	 return HOME_PAGE;
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
