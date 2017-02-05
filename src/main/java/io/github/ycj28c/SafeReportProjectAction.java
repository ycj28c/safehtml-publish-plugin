package io.github.ycj28c;

import hudson.model.AbstractProject;
import hudson.model.ProminentProjectAction;
import hudson.model.Run;

public class SafeReportProjectAction extends SafeReportBaseAction implements ProminentProjectAction {

    private final AbstractProject<?, ?> project;

    public SafeReportProjectAction(AbstractProject<?, ?> project) {
        this.project = project;
    }

	@Override
	public String getUrlName() {
		Run<?, ?> run = this.project.getLastCompletedBuild();
		if (run != null) {
			return extractBuildNumber(run.getUrl()) + "/" + SafeReportPublisher.BASE_DIRECTORY + "/"
					+ SafeReportPublisher.WRAPPER_NAME;
		}

		// none build was completed, report is yet not available
		return "";
	}

    private String extractBuildNumber(String url) {
        String buildNumber = url.substring(0, url.length() - 1);
        buildNumber = buildNumber.substring(buildNumber.lastIndexOf("/") + 1);
        return buildNumber;
    }
}