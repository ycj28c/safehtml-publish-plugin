package io.github.ycj28c;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;
import java.nio.charset.Charset;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractDescribableImpl;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.Descriptor;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import javax.annotation.Nonnull;
import jenkins.tasks.SimpleBuildStep;
import io.github.ycj28c.Messages;

import org.kohsuke.stapler.DataBoundConstructor;

public class SafeReportPublisher extends Publisher implements SimpleBuildStep {

    public final String archivedReportDirectory;
    public final String htmlIndexPage;

    public List<Classification> classifications = Collections.emptyList();

    public final Result buildStatus;
    
    public static final String PLUGIN_NAME = "safehtml-publish-plugin";
    public static final String BASE_DIRECTORY = "safeReport";
    public static final String WRAPPER_NAME = "safereport-wrapper.html";

	@DataBoundConstructor
	public SafeReportPublisher(String archivedReportDirectory, String htmlIndexPage, String buildStatus,
			List<Classification> classifications) {

		this.archivedReportDirectory = archivedReportDirectory;
		this.htmlIndexPage = htmlIndexPage;
		this.buildStatus = buildStatus == null ? null : Result.fromString(buildStatus);
		// don't store the classifications if there was no element provided
		if (classifications != null) {
			this.classifications = classifications;
		}
	}
	
    public List<Classification> getClassifications() {
        return classifications;
    }

    @Override
    public void perform(@Nonnull Run<?, ?> run, @Nonnull FilePath workspace, @Nonnull Launcher launcher, @Nonnull TaskListener listener)
            throws InterruptedException, IOException {

        generateReport(run, workspace, listener);

        SafeArchiveServingRunAction caa = new SafeArchiveServingRunAction(new File(run.getRootDir(), BASE_DIRECTORY),
              BASE_DIRECTORY, htmlIndexPage, SafeReportBaseAction.ICON_NAME, Messages.SidePanel_DisplayName());
        run.addAction(caa);
    }
    
    private void generateHTMLIndexPage(File path, @Nonnull TaskListener listener) throws IOException{
		listener.getLogger().println("[" + PLUGIN_NAME + "] Generating wrappedReport.html");
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), Charset.defaultCharset()));
		try {
			bw.write("<meta http-equiv=\"refresh\" content=\"0; url=" + htmlIndexPage + "\" />");
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

    private void generateReport(Run<?, ?> build, FilePath workspace, TaskListener listener) throws InterruptedException, IOException {
		log(listener, "[" + PLUGIN_NAME + "] Preparing HTML Reports");

        // source directory (possibly on slave)
        FilePath inputDirectory = new FilePath(workspace, archivedReportDirectory);
        if (!inputDirectory.exists()) {
        	throw new IllegalStateException("Could not find source directory: " + inputDirectory);
        }

        File directoryForReport = build.getRootDir();
        File directoryFilesCache = new File(directoryForReport, BASE_DIRECTORY);
        
        int copiedFiles = inputDirectory.copyRecursiveTo(new FilePath(directoryFilesCache));
        log(listener, String.format("Copied %d report files from workspace \"%s\" to reports directory \"%s\"",
                copiedFiles, inputDirectory.getRemote(), directoryFilesCache));
        
		log(listener, "[" + PLUGIN_NAME + "] directoryFilesCache: " + directoryFilesCache);
        generateHTMLIndexPage(new File(directoryFilesCache, WRAPPER_NAME),  listener);
        
    }

    private static void log(TaskListener listener, String message) {
		listener.getLogger().println("[" + PLUGIN_NAME + "]" + message);
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    @Override
    public Action getProjectAction(AbstractProject<?, ?> project) {
        return new SafeReportProjectAction(project);
    }

    public static class Classification extends AbstractDescribableImpl<Classification> {

        String key;
        String value;

        @DataBoundConstructor
        public Classification(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Extension
        public static class DescriptorImpl extends Descriptor<Classification> {

            @Override
            public String getDisplayName() {
                return "";
            }
        }
    }

    @Extension
	public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {
		@Override
		public String getDisplayName() {
			return Messages.Plugin_DisplayName();
		}

		@Override
		public boolean isApplicable(Class<? extends AbstractProject> jobType) {
			return true;
		}
	}
}
