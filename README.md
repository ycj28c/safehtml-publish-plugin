# safehtml-publish-plugin [![Build Status](https://travis-ci.org/ycj28c/safehtml-publish-plugin.svg?branch=master)](https://travis-ci.org/ycj28c/safehtml-publish-plugin)
jenkins report plugin which able to display HTML report to skip the security block for jenkin > 1.6

# How to build
```
mvn clean package
```
It will generate "safehtml-publish-plugin.hpi" in "\safehtml-publish-plugin\target"

# How to install
+ Manage Jenkins -> plugin Manager -> upload the "safehtml-publish-plugin.hpi" 

![alt tag](.README/1.png)


+ In your jenkins job, Add Post-build action 

![alt tag](.README/2.png)


+ Setup report setting

![alt tag](.README/3.png)


+ The new report button will show up in your jenkins jobs

![alt tag](.README/4.png)



# Reference:
https://github.com/jenkinsci/cucumber-reports-plugin
