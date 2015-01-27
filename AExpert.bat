@echo off
SET JAVA_CMD=jre\bin\javaw
SET CLASSPATH=lib\*
IF not exist %JAVA_CMD% set JAVA_CMD=javaw
start %JAVA_CMD%  -Xms128m -Xmx256m -classpath %CLASSPATH% com.tcl.wonder.adclient.view.AdFrame