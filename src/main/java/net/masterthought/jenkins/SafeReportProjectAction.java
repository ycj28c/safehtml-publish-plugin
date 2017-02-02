package net.masterthought.jenkins;

import org.jfree.util.Log;

import hudson.model.AbstractProject;
import hudson.model.ProminentProjectAction;
import hudson.model.Run;

//import net.masterthought.cucumber.ReportBuilder;

public class SafeReportProjectAction extends SafeReportBaseAction implements ProminentProjectAction {

    private final AbstractProject<?, ?> project;
    
//	public static final String BASE_DIRECTORY = "benchmarkReport";
    
    public static final String HOME_PAGE = "network/NetworkIndex.html";

    public SafeReportProjectAction(AbstractProject<?, ?> project) {
        this.project = project;
    }

    @Override
    public String getUrlName() {
        Run<?, ?> run = this.project.getLastCompletedBuild();
        if (run != null) {
//            return extractBuildNumber(run.getUrl()) + "/" + ReportBuilder.BASE_DIRECTORY + "/" + ReportBuilder.HOME_PAGE;
        	Log.info("********* SafeReportProjectAction: " + run.getUrl());
        	return extractBuildNumber(run.getUrl()) + "/" + SafeReportPublisher.BASE_DIRECTORY + "/" + HOME_PAGE;
        }

        // none build was completed, report is yet not available
        return "";
    }

    private String extractBuildNumber(String url) {
        // basic url format -> job/cucumber/125/
        // view url format  -> view/myview/job/cucumber/126/
        String buildNumber = url.substring(0, url.length() - 1);
        buildNumber = buildNumber.substring(buildNumber.lastIndexOf("/") + 1);
        return buildNumber;
    }
}