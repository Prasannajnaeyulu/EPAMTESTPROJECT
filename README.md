# EPAMTESTPROJECT
This is a sample test project to auto test MantisBT website. It's Created using Selenium webdrvier with maven as a build tool.

Please see the steps below on how to import this project into eclipse, how to build and run it from the console [DOS Prompt]

1. Download this project or clone it using git client from your local machine as follows.
$ git clone https://github.com/Prasannajnaeyulu/EPAMTESTPROJECT.git

2. Open command prompt and Goto project copied or cloned directory

Note: Below step 3 will only be required if you wanna import this project into eclipse and put the dependant jars into build path. Otherwise, we can skip this step.

3. Run the following command to download all the dependent jars [incase not available in your local maven repo. .m2/repository directory available in user home directory] and add them to eclipse build path Libraries.
Assume, you copied this project into c:\EPAMTESTPROJECT in our local directory
c:\EPAMTESTPROJECT> mvn eclipse:eclipse 

4. Run the following command to compile the code and copy the required files into classpath (classes) directory
c:\EPAMTESTPROJECT> mvn compile

5. Now, Run the following command to execute the tests configured in testng.xml file of this project
c:\EPAMTESTPROJECT> mvn test

Notes:
1. Once tests finishes execution, one can find the results in 
EPAMTESTPROJECT\test-output directory.
Launch index.html file from the above directory for the results to view in html.

2. For Project specific info, debug, error etc.. logs please see test.log file in your project directory say, c:\EPAMTESTPROJECT\test.log.

3. Screenshots can be found under Screenshots folder
   say, c:\EPAMTESTPROJECT\Screenshots
