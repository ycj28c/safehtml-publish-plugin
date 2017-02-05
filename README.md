# safehtml-publish-plugin
jenkins benchmark report plugin embody insight benchmark test html report

# How to build
```
mvn clean package
```
It will generate "insight-benchmark-report-plugin.hpi" in "\benchmark-report-plugin\target"

# How to install
+ Manage Jenkins -> plugin Manager -> upload the "insight-benchmark-report-plugin.hpi" 

![alt tag](.README/1.png)


+ In your jenkins job, Add Post-build action 

![alt tag](.README/2.png)


+ Setup report setting

![alt tag](.README/3.png)


+ The new report button will show up in your jenkins jobs

![alt tag](.README/4.png)



# Reference:
https://github.com/jenkinsci/cucumber-reports-plugin
