# Serenity BDD - FE Web seed
This seed is intended to fast track the creation and configuration of a **TAS** (Test Automation Solution) for **Web FrontEnd scenarios**.

In this seed you will also find documented Serenity BDD **execution options**, how to **execute your test scenarios over the adidas Test Infrastructure** and **how to integrate Serenity BDD in our CI/CD toolchain**.

![Serenity BDD - Test Report HTML Home](resources/images/01-serenitybdd-reports-home.png)
# Contents
1. References
2. Navigating this seed
3. Serenity BDD (Cucumber based) framework schema
4. Page object pattern implementation in Serenity BDD
5. Execution options
6. Executing over different screen resolutions
7. Executing over different platforms and browsers
8. TAS Code Quality
9. Enabling Video Recording
10. CI/CD - Integrating Serenity BDD TAS on Jenkins pipelines
11. Executing several Test Plans sequentially and aggregating reports
12. Executing several Test Plans in parallel and aggregating reports
13. Showing links to Jira Test Cases on Serenity BDD reports
14. Serenity BDD one page reports
15. Embedding Custom Data in Serenity Reports
16. Integration with reportportal.io

## Annexes
99. Throubleshooting
## 1. References
- [Serenity BDD Website](http://www.thucydides.info/#/)
- [Serenity BDD User Guide](http://www.thucydides.info/docs/serenity/)
- [Gherkin Syntax Reference](https://docs.cucumber.io/gherkin/)
- [Cucumber Reference](https://docs.cucumber.io/cucumber/)
- [Gradle Reference](https://docs.gradle.org/current/userguide/userguide.html)

## 2. Navigating this seed
![Navigating this seed](resources/images/02-navigating-this-seed.png)<br/><br/>

**1. resources** -> Resources folder. Images used on this help file are stored here inside /resources/images. <br/>
**2. jenkinsfiles** -> Jenkinsfiles seeds, showing multiples examples of execution and integration options, are stored here. See **Integrating Serenity BDD TAS on Jenkins pipelines** section for more information. <br/>
**3. serenityProperties** -> Files used to set  the different Serenity parameters used in each type of execution (local, using selenoid or  browserStack).<br/>
**4. browserstack** -> Package that contains classes to configure the web drivers and other necessary setup when using browserStack<br/>
**5. cucumber_steps** -> Package that constins the classes implementing the logic of each step. <br/>
**6. page_objects** -> Package that contains the classes for the page object pattern implementation in Serenity BDD.<br/>
**7. test_runners** -> Package that contains the classes configured to run Serenity with Cucumber tests.<br/>
**8. features** -> Package that contains feature files with the definition of the test cases (scenarios).<br/>
**9. gitignore** -> File used to prevent some unnecessary files or folders to be pushed to the repository.<br/>
**10. build.gradle** -> Configuration file for Gradle dependencies resolution and Gradle tasks.<br/>
**11. gladlew / gradlew.bat** -> Files used by Gradle to execute the tests without having installed on the destination machine.<br/>
**12. README.md** -> This document.<br/>
**13. serenity.properties** -> File used to set Serenity parameters.<br/>
**14. settings.gradle** -> File with some configuration (e.g. project name).<br/>

## 3. Serenity BDD framework schema
![Serenity BDD framework schema](resources/images/03-serenity-schema.png)

Serenity BDD introduces one layer on top of the 2 common layers used by Cucumber.

In **green** you can see highlighted the folders you should have on every Serenity BDD TAS.
1. Under /resources/features you will find Gherkin feature files.

            Feature: Lookup a definition
                In order to talk better
                As an English student
                I want to look up word definitions
        
            Scenario: Looking up the definition of 'apple'
                Given the user is on the Wiktionary home page
                When the user looks up the definition of the word 'apple'
            Then they should see the definition 'A common, round fruit produced by the tree Malus domestica, cultivated in temperate climates.'
2. Under /serenityDefinitions you will have the glue code for the Gherkin steps.

        @Given("the user is on the Wiktionary home page")
            public void givenTheUserIsOnTheWikionaryHomePage() {
                teststeps.is_the_home_page();
            }
   > Glue code path is defined inside Test Runner classes as a Cucumber option.

        @CucumberOptions(
                glue = {"com.adidas.pe.te.web_fe_serenitybdd_archetype.serenityDefinitions"}
        ) 

3. Under /cucumberSteps you will have the implementation of these steps.

        @Step
        public void is_the_home_page() {
            dictionaryPage.openAndMaximize();
        }

## 4. Page object pattern in Serenity BDD
In **blue** you can see highlighted the folder that is specific to a web application TAS with Serenity BDD.
1. Under /pageObjects you will find the classes implementing a Page Objects pattern in Serenity BDD.


        @FindBy(name="search")
        private WebElementFacade searchTerms;
    
        @FindBy(name="go")
        private WebElementFacade lookupButton;
    
        public void enter_keywords(String keyword) {
            searchTerms.type(keyword);
        }
    
        public void lookup_terms() {
            lookupButton.click();
        }


## 5. Execution options
### 5.1. Basic command
Serenity BDD typical execution command using Gradle as build system is:

    > gradle clean test aggregate
- **clean** will delete all build directories plus Serenity BDD reports previously generated.
- **test** will execute default test Gradle task. Inside build.gradle file you can see the code of this task:


    test {
        systemProperties System.getProperties()
        /* Show Serenity execution details: */
        test.testLogging.showStandardStreams = true
        /* Configuring Serenity reporting: */
        test.reports.getHtml().enabled = false
        test.reports.junitXml.enabled= false
        /* Setting the desired Test Runner for the task: */
        include 'com/adidas/pe/te/web_fe_serenitybdd_archetype/testRunners/TestRunner.class'
    }
> Notice the last line, when we are defining which is the testRunner class we want to run when executing Gradle **test** task.
- **aggregate** will tell Serenity BDD to generate the reports based on the test results obtained.
### 5.2. Default options
So, when we execute:

    > gradle clean test aggregate
What is happening?
1. Gradle daemon is executed **cleaning** build and reports folders.
2. Gradle **test** task is executed taking the properties defined on the task inside build.gradle and calling the testRunner class defined: TestRunner.class

        @RunWith(CucumberWithSerenity.class)
        @CucumberOptions(
                plugin = {"pretty", "html:target/cucumber", "json:target/cucumber-report.json"},
                features={"src/test/resources/features/wiktionary_search/LookupADefinition_Scenario.feature"},
                glue = {"com.adidas.pe.te.web_fe_serenitybdd_archetype.serenityDefinitions"}
        )
        public class TestRunner { }
In this file a couple of important defaults are set:
- serenity-cucumber options, defining that we want *pretty* console outputs; and that we want to generate both HTML and JSON standard Cucumber reports, indicating the paths we want these reports to be saved.

  **Both** are needed:
    - Cucumber standard HTML reports are published in Jenkins and allow us to generate **execution trends**, a capability that Serenity BDD does not provide for now.
    - Xray understands out-of-the-box Cucumber standard JSON results file, so this is the file we are importing to Xray to integrate the results of the automated executions in our Jira ecosystem.
  > We recommend **not to change reports paths**. Our Jenkins wrappers and all the examples for CI/CD pipelines used them, so you will save a lot of time if you keep using same paths.
- Path to the features file/s to be executed. This can be a file or a folder. If a folder is set it will execute **recursively** all the feature files inside.

3. Test scenarios are executed using by default the options defined in serenity.properties file:
    - Test scenarios will be executed over adidas Selenoid platform.
    - Google Chrome browser will be used.
    - 1920x1080 resolution will be used.
    - A new browser instance will be used for every **feature**.
    - Screenshots will be captured after every **step**.

            #Default properties file. FE executions will be executed on adidas TE Selenoid server.
            #MANDATORY FOR COMPATIBILITY BETWEEN EXECUTIONS PLATFORMS
            #Options: ("selenoid","browserstack","localwebdrivers")
            te.execution.platform=selenoid
            
            # Appears at the top of the reports
            serenity.project.name = Serenity BDD - FE Web Seed
            serenity.console.colors = true
            
            # Executing remotely (Selenoid or Selenium Hub)
            webdriver.remote.driver = chrome
            webdriver.remote.url = http://selenoid.tools.3stripes.net/wd/hub
            # NOTE: configure screenResolution accordingly with browser resolution
            serenity.driver.capabilities=name:Serenity BDD - FE Web Seed;enableVNC:true;unexpectedAlertBehaviour:ignore;screenResolution:1920x1080x24;
            # Setting browser resolution
            browser.width=1920
            browser.height=1080
            
            #Serenity Options
            # -> http://thucydides.info/docs/serenity-staging/#_serenity_system_properties_and_configuration
            serenity.restart.browser.for.each = feature
            # Screenshots options=(FOR_EACH_ACTION, BEFORE_AND_AFTER_EACH_STEP, FOR_FAILURES, DISABLED)
            # -> http://thucydides.info/docs/serenity-staging/#_configuring_when_screenshots_are_taken
            serenity.take.screenshots=FOR_EACH_ACTION
            
            # EXAMPLES OF GRADLE COMMANDS:
            # gradle clean test aggregate
4. Serenity BDD HTML reports are generated. By default, under /target/site/serenity.
> Again, we recommend **not to change reports paths**. Our Jenkins wrappers and all the examples for CI/CD pipelines used them, so you will save a lot of time if you keep using same paths.

### 5.3. Setting Executions Options
There are basically two ways of setting execution options:
#### 5.3.1. Through code files
As we have seen in previous sections, you can modify and set different options in:
- build.gradle file, inside the test task/s
- TestRunner classes
- serenity.properties files
#### 5.3.2. Through command line
In the following sections, we will see how we can replace most of the default options parametrising the **gradle** command to execute. This will allow us powerful capabilities to tune up our execution jobs.
Combining code files and command line options we will be able to tune our executions and CI/CD pìpelines up in order to adapt these runs to our needs. For instance, executing:


    > gradle clean test aggregate -Dproperties='resources/serenityProperties/local/serenityLocalFirefox.properties'
We will use the set of options defined in this properties file. This is a very good option to manage different sets of configurations.

## 6. Executing over different screen resolutions
Challenge here is that every webdriver manages differently browser size properties. Our proposed solution, the one applied on this seed, would be:
1. Use a customised function to open and maximize browser windows that conditionally could apply the appropriate method to apply resolution to the browser depending on the platform and browser.
   In this seed, our function is inside /pageObjects/WiktionaryPage.class

        public void openAndMaximize(){
        
                //Code to maximize browser windows on all available platforms
                EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
                String executionPlatform=environmentVariables.getProperty("te.execution.platform");
                String userAgent = (String) ((JavascriptExecutor) this.getDriver()).executeScript("return navigator.userAgent;");
                //Logging INFO
                System.out.println("\n INFO (Open and Maximize)");
                System.out.println("\n ------------------------");
                System.out.println("\n EXECUTION PLATFORM: " + executionPlatform);
                System.out.println("\n USER AGENT: " + userAgent);
        
                if (!executionPlatform.isEmpty()) {
                    if (executionPlatform.equals("selenoid")) {
                        //Reading resolution properties
                        int selenoidWidth = Integer.valueOf(environmentVariables.getProperty("browser.width"));
                        int selenoidHeight = Integer.valueOf(environmentVariables.getProperty("browser.height"));
                        this.getDriver().manage().window().setSize(new Dimension(selenoidWidth, selenoidHeight));
                    } else{
                        this.getDriver().manage().window().maximize();
                    }
                } else{
                    this.getDriver().manage().window().maximize();
                }
                open();
            }
2. Set the execution platform value in the serenity.properties file:

        #Options: ("selenoid","browserstack","localwebdrivers")
        te.execution.platform=selenoid
3. Set the desired values in the properties file:

        # Setting browser resolution
        browser.width=1920
        browser.height=1080
This function has been **tested successfully** in:
- Local webdrivers: Google Chrome and Internet Explorer
- Remote webdrivers:
    - Selenoid -> Google Chrome, PhantomJs
    - BrowserStack -> Google Chrome, Internet Explorer and Safari
> **Your contributions to this function would be very welcome.**

## 7. Executing over different platforms and browsers
> Please, check the included properties files in this seed. You will find working examples for local and remote executions. Execution commands examples are shown in every file.

### 7.1. Executing locally
*Please check `resources\\serenityProperties\\local` folder.*

You have to download the following drivers in order to execute the tests in your machine:
1. [Chrome Driver 79.0](https://chromedriver.storage.googleapis.com/79.0.3945.16/chromedriver_win32.zip)
2. [Firefox Driver](https://github.com/mozilla/geckodriver/releases/download/v0.26.0/geckodriver-v0.26.0-win64.zip)
3. [Internet Explorer Driver](https://github.com/mozilla/geckodriver/releases/download/v0.26.0/geckodriver-v0.26.0-win64.zip)

And then put all the drivers into `C:\\tools\\`. This path is the default path in properties. You can change the path in the serenity properties files if drivers were save in other location.

There are 3 properties files in `resources\\serenityProperties\\local` designed for executing locally on different browsers.

Example commands:


    > gradle clean test aggregate -Dproperties='resources/serenityProperties/local/serenityLocalFirefox.properties'
    > gradle clean test aggregate -Dproperties='resources/serenityProperties/local/serenityLocalChrome.properties'
    > gradle clean test aggregate -Dproperties='resources/serenityProperties/local/serenityLocalIExplorer.properties'

If you are using IntelliJ IDEA, you can execute select the properties file you want to use, following this steps:

1. On the top menu, click `Run -> Edit Configurations...`
2.  Click the + button, and select Gradle
3.  Select your Gradle Project
4.  Set Tasks: `clean test aggregate`
5.  For example, if you want to use the chrome properties file set Arguments: `-Dproperties=resources/serenityProperties/local/serenityLocalChrome.properties`

#### Common issues when testing agains IExplorer

When testing over Internet Explorer you need to set up some configuration in the browser. The most common error while running the tests is the following:

```
org.openqa.selenium.remote.SessionNotFoundException: Unexpected error launching Internet Explorer. Protected Mode settings are not the same for all zones. Enable Protected Mode must be set to the same value (enabled or disabled) for all zones. (WARNING: The server did not provide any stacktrace information)
Command duration or timeout: 1.15 seconds
Build info: version: '2.37.0', revision: 'a7c61cb', time: '2013-10-18 17:15:02'
System info: host: 'SAKIB-PC', ip: '192.168.10.70', os.name: 'Windows 7', os.arch: 'x86', os.version: '6.1', java.version: '1.7.0_25'
Driver info: org.openqa.selenium.ie.InternetExplorerDriver
```

To solve this issue you have to follow this steps:
1. Open Internet Explorer.
2. Go to `Internet Options` and click in `Security` tab.
3. Then Mark or Unmark the option: `Enable Protected Mode` for all the zones. This options should be mark or unmark in all zones.

### 7.2. Executing over adidas Selenoid infrastructure
*This is the default option. Please check serenity.properties file.*

Example commands:


    > gradle clean test aggregate
    > gradle clean test -Dproperties=serenitySelenoid_Chrome_1366x768.properties aggregate
### 7.3. Executing over BrowserStack
*Please check serenityBrowserStack_[browser] _[version] _[resolution].properties files.*

1. Ensure BrowserStack customised driver classes are present.
   Under /browserStack you should have both BrowserStackSerenityDriver and BrowserStackSerenityTest classes.
2. Ensure testBrowserStack gradle task exists inside your build.gradle file.


        //[browserstack] To configure BrowserStack custom driver a different Test Runner is needed. This task points to this specific runner.
        task testBrowserStack(type: Test) {
            systemProperties System.getProperties()
            /* Show Serenity execution details: */
            testLogging.showStandardStreams = true
            /* Configuring Serenity reporting: */
            reports.getHtml().enabled = false
            reports.junitXml.enabled= false
            /* Setting the desired Test Runner for the task: */
            include 'com/adidas/pe/te/web_fe_serenitybdd_archetype/testRunners/TestRunnerBrowserStack.class'
        }
3. Ensure  /testRunners/TestRunnerBrowserStack class is present:


        //BrowserStack Test Runner. Used by the Gradle "testBrowserStack" task. Notice this class extends BrowserStackSerenityTest. Needed to configure BrowserStack custom webdriver.
        @RunWith(CucumberWithSerenity.class)
        @CucumberOptions(
                plugin = {"pretty", "html:target/cucumber", "json:target/cucumber-report.json"},
                features={"src/test/resources/features"},
                glue = {"com.adidas.pe.te.web_fe_serenitybdd_archetype.serenityDefinitions"}
        )
        public class TestRunnerBrowserStack extends BrowserStackSerenityTest { }
4. Execute Serenity BDD using the desired properties file:
> Note **testBrowserStack gradle task is used**. **No** the default **test** task.

Example commands:


    > gradle clean testBrowserStack -Dproperties=resources/serenityProperties/browserstack/serenityBrowserStack_Safari_11_1920x1080.properties aggregate
    > gradle clean testBrowserStack -Dproperties=resources/serenityProperties/browserstack/serenityBrowserStack_IE_11_1920x1080.properties aggregate      
## 8. TAS Code Quality
### Sonarqube Integration
Add a gradle task into "build.gradle" file. Don't forget to replace the "sonar.projecName" and "sonar.projectKey" properties with desired values.

    sonarqube {
        properties {
            property "sonar.projectName", "My Project Name"
            property "sonar.projectKey", "my-project-name"
            property "sonar.host.url", "https://tools.adidas-group.com/sonar/"
            property "sonar.login", "352abed46261b316310fb8011fd50ef846278fa8"
            property "sonar.language", "java"
            property "sonar.profile", "QA Automation"
            property "sonar.report.export.path", "sonar-report.json"
            property "sonar.sources", "src"
            property "sonar.exclusions", "src/test/resources"
            property "sonar.test.inclusions", "src/test/java"
            property "sonar.java.binaries", "build"
        }
    }
Execute the code analysis calling the *sonarqube* gradle task.
Example commands:


    > gradle sonarqube
    > gradle --info sonarqube //extended logging
    > gradle clean sonarqube test aggregate

## 9. Enabling Video Recording
> Use video recording **only for DEMO or debugging** purposes. **Video recording capability is only enabled on our Selenoid PRE environment**.

> *Please check serenityPRESelenoid_VideoEnabled.properties.*

You should add the following capabilities to **serenity.driver.capabilities**

Mandatory:

    enableVideo:true;
Optional:

    videoName:videofile.mp4;
Example:

    serenity.driver.capabilities=name:Serenity BDD - FE Web Seed; screenResolution:1920x1080x24; enableVNC:true; enableVideo:true; videoName:seed-tas-serenitybdd-fe-web.mp4; logName:seed-tas-serenitybdd-fe-web.log
## 10. CI/CD - Integrating Serenity BDD TAS on Jenkins pipelines
>***Please check /resources/jenkinsfiles folder***. You will find there full working pipelines examples.

>***To complement this section, please check [adidas Test Infrastructure documentation](https://tools.adidas-group.com/confluence/display/GTTTAG/Test+Engineering+-+Infrastructure)***

### 10.0. General Requirements
We will use adidas Platform Jenkins wrappers as much as possible to simplify pipelines maintenance.
- Ensure common libraries are properly configured in your Jenkins folder.
- Import them in your pipeline script

        #Deprecated
        @Library(['global-jenkins-library@master', 'gtt-jenkins-library@master']) _
        
        #New
        @Library(['global-jenkins-library@master', 'taas-jenkins-wrapper@master']) _
### 10.1. Pulling Automation Code
#### Stage Target
Here we are going to pull our TAS code from repository.
#### Stage Requirements

- TAS code must be on adidas BitBucket.
- Platform wrappers are used, please check general requirements.
- svc_icinga service account is used by default. You can grant access to this user to your repo or use the "credentials" parameter in the get.project command.

#### Stage Samples

       def gitRepo = 'https://tools.adidas-group.com/bitbucket/scm/te/seed-tas-serenitybdd-fe-web'
       def gitBranch = 'master'
       
       stage('Pulling Automation Code') {
           get.project repo: gitRepo, branch: gitBranch
       }
### 10.2. Checking Code Quality
#### Stage Target
Here, we are going to execute code quality checks over our TAS code.
#### Stage Requirements

- Check **8. TAS Code Quality** section.
#### Stage Samples

       stage('Checking Code Quality') {
           withSonarQubeEnv('SonarQube') {
               sh "gradle --info sonarqube"
           }
       }
### 10.3. Pulling Cucumber Features
#### Stage Target
Here, we are going to call [Xray API](https://confluence.xpand-it.com/display/XRAY/Importing+Cucumber+Tests+-+REST) to get the set of features files corresponding with the test scenarios we want to execute and download them to /resources/features/jenkins folder
#### Stage Requirements

- All **Test Cases must be linked to, at least, one User Story**. If they are not, the Cucumber feature files Xray will compound will have a blank name and won't be executed.
- Test Cases, Test Sets, Test Plans must exist in Xray.
- Platform wrappers are used, please check general requirements.
- svc_icinga service account is used by default. You can grant access to this user to your JIRA project or use the "credentials" parameter in the features.export command.

> Our wrapper manages to, in case response from Xray contains a zip file with several feature files, to unzip them automatically.
#### Stage Samples
For a TestCase

       def testCase=TED-18
       
       stage('Pulling Cucumber Features') {
           features.export key: testCase
       }
For a TestSet

        def testSet=TED-21
        
        stage('Pulling Cucumber Features') {
            features.export key: testSet
        }       
For a TestPlan

        def testPlan=TED-23
        
        stage('Pulling Cucumber Features') {
            features.export key: testPlan
        }       


### 10.4. Executing Test Scenarios
#### Stage Target
Here we are going to execute the feature files inside /resources/features/jenkins.
#### Stage Requirements

- Check **##5. Execution options** section for detailed explanation.

#### Stage Samples
In order to avoid pipeline to be interrupted in case of failed tests, which we don't want because the target is, at any case, to get the corresponding reports, results and notifications, execution command possible build error must be controlled. We will mark the build as unstable.

- Inside a variable:

       stage('Executing Test Scenarios') {
           def err = sh 'gradle clean test -Dcucumber.options="src/test/resources/features/jenkins" aggregate'
           if (err) {
               currentBuild.result = 'UNSTABLE'
           }
       }
- Or, inside a try-catch block (use this method in **parallel multinode pipelines**):

       stage('Executing Test Scenarios over Google Chrome') {
           try {
               sh 'gradle clean test -Dcucumber.options="src/test/resources/features/jenkins" aggregate'
           } catch (Exception e) {
               currentBuild.result = 'UNSTABLE'
           }
       } 
### 10.5. Publishing Test Results
#### Stage Target
Here, we are going to publish Serenity and Cucumber HTML Test Reports into Jenkins.
#### Stage Requirements

- Platform wrappers are used, please check general requirements.
- Default reports paths are used, please check **###5.2. Default options** section for detailed info.
#### Stage Samples

       stage('Publishing Test Results') {
           report.serenity()
           report.cucumber()
       }
### 10.6. Exporting Test Results
#### Stage Target
Here, we are going to call Xray API again to post Cucumber results JSON file. A new Test Execution will be created in Jira and results will be linked to corresponding test cases, sets and plans.
#### Stage Requirements

- Default reports paths are used, please check **###5.2. Default options** section for detailed info.
#### Stage Samples
For a Test Set or Test Case:

       stage('Exporting Test Results') {
           withCredentials([usernameColonPassword(credentialsId: 'service_account', variable: 'USERPASS')]) {
               def header ='-H "Content-Type: application/json" -H "Cache-Control: no-cache"'
               def url = 'https://tools.adidas-group.com/jira/rest/raven/1.0/import/execution/cucumber'
               def data = '@target/cucumber-report.json'
               sh "curl -X POST $url $header -u $USERPASS -d $data"
           }
       }
For a Test Plan (an also a more elaborated example as we are going to additionally, customise the ***summary(name)*** and ***environment*** field of the Test Execution that will be created in Jira):

       def testPlan = 'TED-23'
       def jiraEnvironment='PROD'
       def jiraSummary='SerenityBDD FULL Pipeline TestPlan'
       
       stage('Exporting Test Results') {
           withCredentials([usernameColonPassword(credentialsId: 'service_account', variable: 'USERPASS')]) {
               def header ='-H "Content-Type: application/json" -H "Cache-Control: no-cache"'
               def url = 'https://tools.adidas-group.com/jira/rest/raven/1.0/import/execution/cucumber'
               def data = '@target/cucumber-report.json'
               def output = 'target/xray-response.json' //Writing Xray response to file to get created execution Key.
               sh "curl -X POST $url $header -u $USERPASS -d $data -o $output"
           }
           //Getting created execution key
           def props = readJSON file: 'target/xray-response.json'
           def executionKey = props.testExecIssue.key
           echo '[Xray] Test Execution created succesfully: ' + executionKey
           //Linking test execution to test plan
           //Creating json data for Xray
           sh "echo '{\"add\": [\"$executionKey\"]}' > target/data.json"
           withCredentials([usernameColonPassword(credentialsId: 'service_account', variable: 'USERPASS')]) {
               def header ='-H "Content-Type: application/json" -H "Cache-Control: no-cache"'
               def url = 'https://tools.adidas-group.com/jira/rest/raven/1.0/api/testplan/' + testPlan + '/testexecution'
               def data = '@target/data.json'
               sh "curl -X POST $url $header -u $USERPASS -d $data"
               def urlExecution = 'https://tools.adidas-group.com/jira/rest/api/2/issue/' + executionKey
               sh "curl -X PUT $urlExecution $header -u $USERPASS -d  \"{ \\\"fields\\\": { \\\"customfield_11405\\\":[\\\"$jiraEnvironment\\\"] , \\\"summary\\\":\\\"$jiraSummary\\\"}}\""
           }
       }
### 10.7. Sending Slack notifications
#### Stage Target
Here we will send notifications to a Slack channel with direct links to console logs, Jenkins HTML reports and Jira corresponding Test Case, Set or Plan.
#### Stage Requirements
- Platform wrappers are used, please check general requirements.
- You need to have your Slack channel token credentials in the Jenkins folder.

#### Stage Samples

       def testPlan = 'TED-23'
       
       def credentials = 'slack-pe-token'
       def team = 'adidas-te'
       def channel = 'notifications'
       def level='good'
       
       stage('Sending Email notifications'){
           emailext body: '''Kindly find below the results of the automated tests executed. <BR>
               <BR>
               <ul>Job: <strong>${PROJECT_NAME}</strong></ul>
               <ul>Build: <strong>${BUILD_NUMBER}</strong></ul>
               <ul>Status: <strong>${BUILD_STATUS}</strong></ul>
               <BR>
               Check <strong>Serenity BDD - Test Results</strong> at ${BUILD_URL}Serenity_20BDD_20Report.<BR>
               Check <strong>Cucumber Test Report</strong> at ${BUILD_URL}cucumber-html-reports.<BR>
               Check <strong>console</strong> output at ${BUILD_URL}console.<BR>
               Check Xray Test Plan: <a href=\'https://tools.adidas-group.com/jira/browse/''' + testPlan + '''\'>''' + testPlan + '''</a>
               <BR>
               <BR>
               Best regards,<BR>
               <BR>
               Platform Engineering - adidas Group''',
                   subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!',
                   to: 'pea.test_engineering@adidas.com',
                   mimeType: 'text/html'
       }
### 10.8. Sending Email notifications
#### Stage Target
Can you imagine? We are going to send an email notification :-)

#### Stage samples

       def testPlan = 'TED-23'
       
       stage('Sending Email notifications'){
           emailext body: '''Kindly find below the results of the automated tests executed. <BR>
               <BR>
               <ul>Job: <strong>${PROJECT_NAME}</strong></ul>
               <ul>Build: <strong>${BUILD_NUMBER}</strong></ul>
               <ul>Status: <strong>${BUILD_STATUS}</strong></ul>
               <BR>
               Check <strong>Serenity BDD - Test Results</strong> at ${BUILD_URL}Serenity_20BDD_20Report.<BR>
               Check <strong>Cucumber Test Report</strong> at ${BUILD_URL}cucumber-html-reports.<BR>
               Check <strong>console</strong> output at ${BUILD_URL}console.<BR>
               Check Xray Test Plan: <a href=\'https://tools.adidas-group.com/jira/browse/''' + testPlan + '''\'>''' + testPlan + '''</a>
               <BR>
               <BR>
               Best regards,<BR>
               <BR>
               Platform Engineering - adidas Group''',
                   subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!',
                   to: 'JoseManuel.Sampayo@adidas-group.com'
       }
### 10.9. Sending Notifications in case of failure
#### Stage Target
Here we want also to be notified in case of a build failure during the execution of our test automation pipeline.

#### Stage Requirements
- Platform wrappers are used, please check general requirements.
- You need to have your Slack channel token credentials in the Jenkins folder.
#### Stage Samples

    #!groovy
    def credentials = 'slack-pe-token'
    def team = 'adidas-te'
    def channel = 'notifications'
    def level='good'
    
    @Library(['global-jenkins-library@master', 'taas-jenkins-wrapper@master']) _
    
    //Pipeline
    node('serenity') {
        try{
            currentBuild.result = 'SUCCESS' 
            //Stages code would be here   
        }       
        catch(err){
           echo "Caught: ${err}"
           currentBuild.result = 'FAILURE'
        
           slackUtils.notify message: """\n
               Test Automation job FAILED! \n
               Console log: ${BUILD_URL}console \n
               """,
                   credentials: credentials,
                   team: team,
                   channel: channel,
                   level: "error"
        
           emailext body: '''<ul>Job: <strong>${PROJECT_NAME}</strong></ul>
                   <ul>Build: <strong>${BUILD_NUMBER}</strong></ul>
                   <ul>Status: <strong>${BUILD_STATUS}</strong></ul>
                   <BR>
                   Check <strong>console</strong> output at ${BUILD_URL}console.<BR>
                   <BR>
                   Best regards,<BR>
                   <BR>
                   Platform Engineering - adidas Group''',
                   subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!',
                   to: 'JoseManuel.Sampayo@adidas-group.com'
        }
    }
## 11. Executing several Test Plans sequentially and aggregating reports
> Please refer to [/resources/jenkinsfiles/01-serenity-full-sequential-aggregated-testplans-multibrowser.groovy](https://tools.adidas-group.com/bitbucket/projects/te/repos/seed-tas-serenitybdd-fe-web/browse/resources/jenkinsfiles/01-serenity-full-sequential-aggregated-testplans-multibrowser.groovy) for a full working example

![Serenity BDD Aggregated report](resources/images/04-serenity-aggregated-report.png)
### Pipeline structure
Only differences with the general structure described on **10. CI/CD - Integrating Serenity BDD TAS on Jenkins pipelines** are highlighted.

![Serenity BDD Aggregated pipeline](resources/images/05-serenity-aggregated-pipeline.png)

    #!groovy

    //DEFINITIONS
    def testPlan = ''
    def jiraSummary = ''

    //WRAPPERS
    @Library(['global-jenkins-library@master', 'taas-jenkins-wrapper@master']) _


    //Pipeline
    node(testNode) {
        try{
            currentBuild.result = 'SUCCESS'

            stage('Pulling Automation Code') {}

            stage('Checking Code Quality') {}

            stage('Cleaning existing reports') {
                //As we want to aggregate results of different test runs we will clean the workspace only at this stage and no later.
                sh 'gradle clean'
            }

            stage('Pulling Cucumber Features') {
                //Assigning first Tets Plan key to execute
                testPlan = 'TED-23'
                features.export key: testPlan
            }

            stage('Executing Test Scenarios over Google Chrome') {
                //Assigning summary for the first Test Execution to be created in Jira
                jiraSummary='[Chrome] TestPlanDemo Execution'
                
                //EXECUTION COMMAND. 
                //NOTE NO "aggregate" TASK IS EXECUTED.
                //USE A DIFFERENT CONTEXT for each execution TO AGGREGATE THE RESULTS LATER. Use injected tags to make these contexts clickable on reports.
                def err = sh 'gradle test -Dcucumber.options="src/test/resources/features/jenkins" -Dcontext=chrome -Dinjected.tags="browser:Google Chrome"'
                if (err) {
                    currentBuild.result = 'UNSTABLE'
                }
            }

            stage('Publishing Test Results') {
                //Only Cucumber reports are published now at this stage to feed trends statistics.
                report.cucumber()
            }

            stage('Exporting Test Results') {
                //Importing results for the first Test Plan in Jira and customising Test Execution to be created
                withCredentials([usernameColonPassword(credentialsId: 'service_account', variable: 'USERPASS')]) {
                    def header ='-H "Content-Type: application/json" -H "Cache-Control: no-cache"'
                    def url = 'https://tools.adidas-group.com/jira/rest/raven/1.0/import/execution/cucumber'
                    def data = '@target/cucumber-report.json'
                    def output = 'target/xray-response.json' //Writing Xray response to file to get created execution Key.
                    sh "curl -X POST $url $header -u $USERPASS -d $data -o $output"
                }
                //Getting created execution key
                def props = readJSON file: 'target/xray-response.json'
                def executionKey = props.testExecIssue.key
                echo '[Xray] Test Execution created succesfully: ' + executionKey
                //Linking test execution to test plan
                //Creating json data for Xray
                sh "echo '{\"add\": [\"$executionKey\"]}' > target/data.json"
                withCredentials([usernameColonPassword(credentialsId: 'service_account', variable: 'USERPASS')]) {
                    def header ='-H "Content-Type: application/json" -H "Cache-Control: no-cache"'
                    def url = 'https://tools.adidas-group.com/jira/rest/raven/1.0/api/testplan/' + testPlan + '/testexecution'
                    def data = '@target/data.json'
                    sh "curl -X POST $url $header -u $USERPASS -d $data"
                    def urlExecution = 'https://tools.adidas-group.com/jira/rest/api/2/issue/' + executionKey
                    sh "curl -X PUT $urlExecution $header -u $USERPASS -d  \"{ \\\"fields\\\": { \\\"customfield_11405\\\":[\\\"$jiraEnvironment\\\"] , \\\"summary\\\":\\\"$jiraSummary\\\"}}\""
                }
            }

            stage('Pulling Cucumber Features') {
                //Assigning second Tets Plan key to execute
                testPlan = 'TED-25'
                features.export key: testPlan
            }

            stage('Executing Test Scenarios over Internet Explorer') {
                //Assigning summary for the second Test Execution to be created in Jira
                jiraSummary='[IE] TestPlanDemo Execution'
                
                //EXECUTION COMMAND. 
                //NOTE NO "aggregate" TASK IS EXECUTED.
                //USE A DIFFERENT CONTEXT for each execution TO AGGREGATE THE RESULTS LATER. Use injected tags to make these contexts clickable on reports.
                def err = sh 'gradle testBrowserStack -Dcucumber.options="src/test/resources/features/jenkins" -Dcontext=ie -Dinjected.tags="browser:Internet Explorer" -Dproperties="resources/serenityProperties/browserstack/serenityBrowserStack_IE_11_1920x1080.properties"'
                if (err) {
                    currentBuild.result = 'UNSTABLE'
                }
            }

            stage('Publishing Test Results') {
                //Only Cucumber reports are published now at this stage to feed trends statistics.
                report.cucumber()
            }

            stage('Exporting Test Results') {
                //Importing results for the second Test Plan in Jira and customising Test Execution to be created
                withCredentials([usernameColonPassword(credentialsId: 'service_account', variable: 'USERPASS')]) {
                    def header ='-H "Content-Type: application/json" -H "Cache-Control: no-cache"'
                    def url = 'https://tools.adidas-group.com/jira/rest/raven/1.0/import/execution/cucumber'
                    def data = '@target/cucumber-report.json'
                    def output = 'target/xray-response.json' //Writing Xray response to file to get created execution Key.
                    sh "curl -X POST $url $header -u $USERPASS -d $data -o $output"
                }
                //Getting created execution key
                def props = readJSON file: 'target/xray-response.json'
                def executionKey = props.testExecIssue.key
                echo '[Xray] Test Execution created succesfully: ' + executionKey
                //Linking test execution to test plan
                //Creating json data for Xray
                sh "echo '{\"add\": [\"$executionKey\"]}' > target/data.json"
                withCredentials([usernameColonPassword(credentialsId: 'service_account', variable: 'USERPASS')]) {
                    def header ='-H "Content-Type: application/json" -H "Cache-Control: no-cache"'
                    def url = 'https://tools.adidas-group.com/jira/rest/raven/1.0/api/testplan/' + testPlan + '/testexecution'
                    def data = '@target/data.json'
                    sh "curl -X POST $url $header -u $USERPASS -d $data"
                    def urlExecution = 'https://tools.adidas-group.com/jira/rest/api/2/issue/' + executionKey
                    sh "curl -X PUT $urlExecution $header -u $USERPASS -d  \"{ \\\"fields\\\": { \\\"customfield_11405\\\":[\\\"$jiraEnvironment\\\"] , \\\"summary\\\":\\\"$jiraSummary\\\"}}\""
                }
            }

            stage('Aggregating results') {
                //NOW THAT WE HAVE ALL THE RESULTS, WE CAN EXECUTE "aggregate" TASK.
                sh 'gradle aggregate'
            }

            stage('Publishing Aggregated Test Results') {
                //NOW WE CAN PUBLISHED THE AGGREGATED HTML REPORT
                report.serenity()
            }

            stage('Sending Slack notifications'){}

            stage('Sending Email notifications'){}
        }
        catch(err){}
    }   

## 12. Executing several Test Plans in parallel and aggregating reports
> Please refer to [/resources/jenkinsfiles/01-serenity-full-parallel-aggregated-testplans-multibrowser-multinode-3.groovy](https://tools.adidas-group.com/bitbucket/projects/te/repos/seed-tas-serenitybdd-fe-web/browse/resources/jenkinsfiles/01-serenity-full-parallel-aggregated-testplans-multibrowser-multinode-3.groovy) for a full working example

![Serenity BDD Aggregated report](resources/images/06-serenity-parallel-aggregated-report.png)
### Pipeline structure
Only differences with the general structure described on **10. CI/CD - Integrating Serenity BDD TAS on Jenkins pipelines** are highlighted.

![Serenity BDD Aggregated pipeline](resources/images/07-serenity-parallel-aggregated-pipeline.png)

    #!groovy
    
    //DEFINITIONS
    //TEST PLAN VARIABLES SHOULD GO INSIDE PARALLEL NODES CODE
    
    //WRAPPERS
    @Library(['global-jenkins-library@master', 'gtt-jenkins-library@master']) _

    //Pipeline
    node(testNode) {
        try{
            currentBuild.result = 'SUCCESS'

            stage('Pulling Automation Code') {
                get.project repo: gitRepo, branch: gitBranch
                //MAKING AUTOMATION CODE AVAILABLE FOR ALL NODES
                stash includes: '**/*', name: 'tas'
            }

            stage('Checking Code Quality') {}

            stage('Cleaning existing reports') {
                //As we want to aggregate results of different test runs we will clean the workspace only at this stage and no later.
                sh 'gradle clean'
            }

            stage('Parallel Tets Executions') {
                parallel SelenoidChrome: {
                    node(testNode) {
                        //WE HAVE TO DEFINE HERE THE TEST PLAN AND EXECUTION VARIABLES
                        def testPlan = 'TED-23'
                        def jiraEnvironment='PROD'
                        def jiraSummary='[Chrome] TestPlanDemo Execution'

                        stage('Pulling Cucumber Features') {
                            //MAKING AUTOMATION CODE AVAILABLE ON THIS NODE
                            unstash 'tas'
                            features.export key: testPlan
                        }
                        stage('Executing Test Scenarios over Google Chrome') {
                            //EXECUTION COMMAND.
                            //NOTE NO "aggregate" TASK IS EXECUTED.
                            //USE A DIFFERENT CONTEXT for each execution TO AGGREGATE THE RESULTS LATER. Use injected tags to make these contexts clickable on reports.
                            try {
                                sh 'gradle test -Dcucumber.options="src/test/resources/features/jenkins" -Dcontext=chrome -Dinjected.tags="browser:Google Chrome"'
                            } catch (Exception e) {
                                currentBuild.result = 'UNSTABLE'
                            }
                        }
                        stage('Publishing Test Results') {
                            ////Only Cucumber reports are published now at this stage to feed trends statistics.
                            report.cucumber()
                        }
                        stage('Exporting Test Results') {
                            //Importing results for the first Test Plan in Jira and customising Test Execution to be created
                            withCredentials([usernameColonPassword(credentialsId: 'service_account', variable: 'USERPASS')]) {
                                def header = '-H "Content-Type: application/json" -H "Cache-Control: no-cache"'
                                def url = 'https://tools.adidas-group.com/jira/rest/raven/1.0/import/execution/cucumber'
                                def data = '@target/cucumber-report.json'
                                def output = 'target/xray-response.json'
                                //Writing Xray response to file to get created execution Key.
                                sh "curl -X POST $url $header -u $USERPASS -d $data -o $output"
                            }
                            //Getting created execution key
                            def props = readJSON file: 'target/xray-response.json'
                            def executionKey = props.testExecIssue.key
                            echo '[Xray] Test Execution created succesfully: ' + executionKey
                            //Linking test execution to test plan
                            //Creating json data for Xray
                            sh "echo '{\"add\": [\"$executionKey\"]}' > target/data.json"
                            withCredentials([usernameColonPassword(credentialsId: 'service_account', variable: 'USERPASS')]) {
                                def header = '-H "Content-Type: application/json" -H "Cache-Control: no-cache"'
                                def url = 'https://tools.adidas-group.com/jira/rest/raven/1.0/api/testplan/' + testPlan + '/testexecution'
                                def data = '@target/data.json'
                                sh "curl -X POST $url $header -u $USERPASS -d $data"
                                def urlExecution = 'https://tools.adidas-group.com/jira/rest/api/2/issue/' + executionKey
                                sh "curl -X PUT $urlExecution $header -u $USERPASS -d  \"{ \\\"fields\\\": { \\\"customfield_11405\\\":[\\\"$jiraEnvironment\\\"] , \\\"summary\\\":\\\"$jiraSummary\\\"}}\""
                            }
                        }
                        stage('Sharing Serenity reports for later aggregation') {
                            //MAKING SERENITY REPORTS AVAILABLE TO THE REST OF THE NODES
                            stash includes: 'target/site/serenity/**/*', name: 'serenityReportsChrome'
                        }
                    }
                },
                BrowserStackIE: {
                    node(testNode) {
                        //WE HAVE TO DEFINE HERE THE TEST PLAN AND EXECUTION VARIABLES
                        def testPlan = 'TED-25'
                        def jiraEnvironment='PROD'
                        def jiraSummary = '[IE] TestPlanDemo Execution'

                        stage('Pulling Cucumber Features') {
                            //MAKING AUTOMATION CODE AVAILABLE ON THIS NODE
                            unstash 'tas'
                            features.export key: testPlan
                        }
                        stage('Executing Test Scenarios over Internet Explorer') {
                            //EXECUTION COMMAND.
                            //NOTE NO "aggregate" TASK IS EXECUTED.
                            //USE A DIFFERENT CONTEXT for each execution TO AGGREGATE THE RESULTS LATER. Use injected tags to make these contexts clickable on reports.
                            try {
                                sh 'gradle testBrowserStack -Dcucumber.options="src/test/resources/features/jenkins" -Dcontext=ie -Dinjected.tags="browser:Internet Explorer" -Dproperties="resources/serenityProperties/browserstack/serenityBrowserStack_IE_11_1920x1080.properties"'
                            } catch (Exception e) {
                                currentBuild.result = 'UNSTABLE'
                            }
                        }
                        stage('Publishing Test Results') {
                            //Only Cucumber reports are published now at this stage to feed trends statistics.
                            report.cucumber()
                        }
                        stage('Exporting Test Results') {
                            //Importing results for the second Test Plan in Jira and customising Test Execution to be created
                            withCredentials([usernameColonPassword(credentialsId: 'service_account', variable: 'USERPASS')]) {
                                def header = '-H "Content-Type: application/json" -H "Cache-Control: no-cache"'
                                def url = 'https://tools.adidas-group.com/jira/rest/raven/1.0/import/execution/cucumber'
                                def data = '@target/cucumber-report.json'
                                def output = 'target/xray-response.json'
                                //Writing Xray response to file to get created execution Key.
                                sh "curl -X POST $url $header -u $USERPASS -d $data -o $output"
                            }
                            //Getting created execution key
                            def props = readJSON file: 'target/xray-response.json'
                            def executionKey = props.testExecIssue.key
                            echo '[Xray] Test Execution created succesfully: ' + executionKey
                            //Linking test execution to test plan
                            //Creating json data for Xray
                            sh "echo '{\"add\": [\"$executionKey\"]}' > target/data.json"
                            withCredentials([usernameColonPassword(credentialsId: 'service_account', variable: 'USERPASS')]) {
                                def header = '-H "Content-Type: application/json" -H "Cache-Control: no-cache"'
                                def url = 'https://tools.adidas-group.com/jira/rest/raven/1.0/api/testplan/' + testPlan + '/testexecution'
                                def data = '@target/data.json'
                                sh "curl -X POST $url $header -u $USERPASS -d $data"
                                def urlExecution = 'https://tools.adidas-group.com/jira/rest/api/2/issue/' + executionKey
                                sh "curl -X PUT $urlExecution $header -u $USERPASS -d  \"{ \\\"fields\\\": { \\\"customfield_11405\\\":[\\\"$jiraEnvironment\\\"] , \\\"summary\\\":\\\"$jiraSummary\\\"}}\""
                            }
                        }
                        stage('Sharing Serenity reports for later aggregation') {
                            //MAKING SERENITY REPORTS AVAILABLE TO THE REST OF THE NODES
                            stash includes: 'target/site/serenity/**/*', name: 'serenityReportsIe'
                        }
                    }
                },
                BrowserStackSafari: {
                    node(testNode) {
                        //WE HAVE TO DEFINE HERE THE TEST PLAN AND EXECUTION VARIABLES
                        def testPlan = 'TED-26'
                        def jiraEnvironment='PROD'
                        def jiraSummary='[Safari] TestPlanDemo Execution'

                        stage('Pulling Cucumber Features') {
                            //MAKING AUTOMATION CODE AVAILABLE ON THIS NODE
                            unstash 'tas'
                            features.export key: testPlan
                        }
                        stage('Executing Test Scenarios over Safari') {
                            //EXECUTION COMMAND.
                            //NOTE NO "aggregate" TASK IS EXECUTED.
                            //USE A DIFFERENT CONTEXT for each execution TO AGGREGATE THE RESULTS LATER. Use injected tags to make these contexts clickable on reports.
                            try {
                                sh 'gradle testBrowserStack -Dcucumber.options="src/test/resources/features/jenkins" -Dcontext=safari -Dinjected.tags="browser:Safari" -Dproperties="resources/serenityProperties/browserstack/serenityBrowserStack_Safari_11_1920x1080.properties"'
                            } catch (Exception e) {
                                currentBuild.result = 'UNSTABLE'
                            }
                        }
                        stage('Publishing Test Results') {
                            //Only Cucumber reports are published now at this stage to feed trends statistics.
                            report.cucumber()
                        }
                        stage('Exporting Test Results') {
                            //Importing results for the third Test Plan in Jira and customising Test Execution to be created
                            withCredentials([usernameColonPassword(credentialsId: 'service_account', variable: 'USERPASS')]) {
                                def header = '-H "Content-Type: application/json" -H "Cache-Control: no-cache"'
                                def url = 'https://tools.adidas-group.com/jira/rest/raven/1.0/import/execution/cucumber'
                                def data = '@target/cucumber-report.json'
                                def output = 'target/xray-response.json'
                                //Writing Xray response to file to get created execution Key.
                                sh "curl -X POST $url $header -u $USERPASS -d $data -o $output"
                            }
                            //Getting created execution key
                            def props = readJSON file: 'target/xray-response.json'
                            def executionKey = props.testExecIssue.key
                            echo '[Xray] Test Execution created succesfully: ' + executionKey
                            //Linking test execution to test plan
                            //Creating json data for Xray
                            sh "echo '{\"add\": [\"$executionKey\"]}' > target/data.json"
                            withCredentials([usernameColonPassword(credentialsId: 'service_account', variable: 'USERPASS')]) {
                                def header = '-H "Content-Type: application/json" -H "Cache-Control: no-cache"'
                                def url = 'https://tools.adidas-group.com/jira/rest/raven/1.0/api/testplan/' + testPlan + '/testexecution'
                                def data = '@target/data.json'
                                sh "curl -X POST $url $header -u $USERPASS -d $data"
                                def urlExecution = 'https://tools.adidas-group.com/jira/rest/api/2/issue/' + executionKey
                                sh "curl -X PUT $urlExecution $header -u $USERPASS -d  \"{ \\\"fields\\\": { \\\"customfield_11405\\\":[\\\"$jiraEnvironment\\\"] , \\\"summary\\\":\\\"$jiraSummary\\\"}}\""
                            }
                        }
                        stage('Sharing Serenity reports for later aggregation') {
                            //MAKING SERENITY REPORTS AVAILABLE TO THE REST OF THE NODES
                            stash includes: 'target/site/serenity/**/*', name: 'serenityReportsSafari'
                        }
                    }
                }
            }
            stage('Aggregating results') {
                //MAKING ALL REPORTS AVAILABLE ON THE INITIAL TEST NODE
                unstash 'serenityReportsChrome'
                unstash 'serenityReportsIe'
                unstash 'serenityReportsSafari'

                //GENERATING AGGREGATED REPORTS
                sh 'gradle aggregate'
            }

            stage('Publishing Aggregated Test Results') {
                    //PUBLISHING AGGREGATED SERENITY HTML REPORT
                    report.serenity()
            }

            stage('Sending Slack notifications'){}

            stage('Sending Email notifications'){}
        }
        catch(error){}
    }

## 13. Showing links to Jira Test Cases on Serenity BDD reports
There are 2 requirements to get the links to Jira tickets displayed on the test reports.
#### 1. Add the following property on your serenity.properties file.

    # Making links to Jira issue to be displayed on Serenity BDD reports.
    serenity.issue.tracker.url = https://tools.adidas-group.com/jira/browse/{0}

#### 2. Label your scenarios with @issue:JIRA-ID

###### Manually

    Feature: Lookup a definition
      In order to talk better
      As an English student
      I want to look up word definitions
    
      @issue:TED-18
      Scenario: Looking up the definition of 'apple'
        Given the user is on the Wiktionary home page
        When the user looks up the definition of the word 'apple'
        Then they should see the definition 'A common, round fruit produced by the tree Malus domestica, cultivated in temperate climates.'
    
      @issue:TED-19
      Scenario: Looking up the definition of 'pear'
        Given the user is on the Wiktionary home page
        When the user looks up the definition of the word 'pear'
        Then they should see the definition 'An edible fruit produced by the pear tree, similar to an apple but elongated towards the stem.'

###### Automatically
Label Xray tests with "issue:JIRA-ID". When exporting Xray tests to Cucumber feature files, the label will be transform in a Cucumber tag with the format @issue:JIRA-ID.

    issue:TED-18

![Xray Test label](resources/images/08-xray-label-for-linking-reports.png)

#### What you get
![Serenity BDD links to Xray tests](resources/images/09-serenity-link-to-xray-test.png)

## 14. Serenity BDD one page reports
Serenity BDD produces rich reports that act both as test reports and living documentation. But often is useful to be able to send a short summary of the test outcomes.
Serenity allows you to generate a single-page, self-contained HTML summary report, containing an overview of the test results, and a configurable breakdown of the status of different areas of the aplication.
You can see a sample of such report here:

![A summary report generated by Serenity](resources/images/10-serenity-summary-of-reports.PNG)

#### 1. Setting up Gradle
If you're using Gradle, you can use the reports task to generate any configured extended reports. First of all, you need to add theese dependencies in your `build.gradle` file:

    buildscript {
        repositories {
            mavenLocal()
            jcenter()
        }
        dependencies {
            classpath "net.serenity-bdd:serenity-gradle-plugin:2.0.4"
            classpath "net.serenity-bdd:serenity-emailer:1.9.36"
        }
    }
Next, you need to configure the report you want to generate in your `build.gradle` file using the _`serenity`_ section, as shown here:

    serenity {
        reports = ["email"]
    }
Now you can generate these reports by invoking the **reports** task:

        $gradle reports
    
    > Task :reports
    Generating Serenity Reports for bdd-bank to directory /Users/john/Projects/SerenityDojo/bdd-bank/target/site/serenity
    PROCESSING EXTENDED REPORTS: [email]
    
    BUILD SUCCESSFUL in 2s
    1 actionable task: 1 executed
The `serenity-summary.html` file has been created at the following address:  `target\site\serenity\serenity-summary.html`
#### 2. Custom field Reporting
Custom fields can be included in the reports, using values that are either taken from the environment variables or passed into
the build via system properties. This can be useful if you want yo include the product or build version, the environment the tests
were run on, or the test run date.

You can add custom fields to your report by setting system properties with the special prefix `report.customfields.`. These can go in your `serenity.properties` file in the root directory of your project, or you can pass them in as command-line options.
For example, you could add the following properties to your `serenity.properties` file:

         report.customfields.environment = Integration
         report.customfields.ApplicationVersion = 1.2.3
#### 3. Pipeline Structure
You have to modify the pipeline structure to be able to show the new summary and establish an integration with Jenkins. The content you have to add is the following (next to stage with name `stage('Aggregating results')`):

    stage('Generating One Page Reports'){
              sh 'gradle reports'
    }
    
    stage('Publishing Summary Report') {
              publishHTML([allowMissing: false, alwaysLinkToLastBuild: true,
              keepAll: false, reportDir: './target/site/serenity', reportFiles: 'serenity-summary.html',
              reportName: 'Summary HTML Report', reportTitles: ''])
    }

## 15. Embedding Custom Data in Serenity Reports
Sometimes, our acceptance tests produce data that we need to store, but that does not naturally fit into a BDD-style Given/When/Then format.
This might include evidence of test execution such as generated reports or log files, or simple some more detailed information about the test results.

For example, suppose you have a feature related to generating an XML report like the following:

        Feature: Exporting to XML
        
          In order to see my todo items in another application
          As a todo list enthusiast
          I want to export my todo items in a standardized format
        
          Scenario: Export todo items in XML
            Given that Jane has a todo list containing the following items:
              | Buy some milk |
              | Walk the cat  |
              | Feed the goat |
            When she exports the list in XML
            Then the XML report should contain:
              | title         | status |
              | Buy some milk | Active |
              | Walk the cat  | Active |
              | Feed the goat | Active |

This feature is quite simple to use. At any point during test execution, you can include data from a file like this:

        Path generatedReport = Paths.get(...);
        
        Serenity.recordReportData().withTitle("Exported XML report")
                                   .fromFile(generatedReport);
If you have the contents of the report as a String, you can pass the String directly like this:

        String testData = "...";
        Serenity.recordReportData().withTitle("Exported XML report")
                                   .andContent(testData);

When you run this scenario, a button with the label “Exported XML report” will appear next to the step where you called this method:
![Exported XML report](resources/images/11-exported-XML-report.png)

If you click on this button, you will see your data:
![View of data](resources/images/12-view-of-data.png)

This feature is available in Serenity version 1.9.16 onwards.

## 16. Integration with reportportal.io
For the integration with reportportal.io you should configure the values of the mandatories fields in the reportportal.properties file:

    rp.endpoint = http://reportportalurl:8080 #address and port of reportportal.io server
    rp.launch = launch-fe-web                 #launch name
    rp.project = seed-tas-serenitybdd-fe-web  #project name. This name should match with a existing project name in reportportal.io

You should add rp.uuid value to the command. You can find the uuid vale of your user in the profile section in reportportal.io
Example commands:

    > gradle clean test aggregate -Dproperties=resources/serenityProperties/local/serenityLocalFirefox.properties -Drp.uuid=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
    > gradle clean test aggregate -Dproperties=resources/serenityProperties/local/serenityLocalChrome.properties -Drp.uuid=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
    > gradle clean test aggregate -Dproperties=resources/serenityProperties/local/serenityLocalIExplorer.properties -Drp.uuid=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx

## 99. Troubleshooting

- "On IntelliJ I can't go to the Step definitions of any step in a feature file, it doesn't detect the definition and suggests me to create one"
> Answer: Make sure you have the `Cucumber for Java` plugin installed and deactivate the `Substeps IntelliJ Plugin`. Restart IntelliJ and you should now be able to go to step definition of steps.

- "Running a single scenario from IntelliJ"
> Answer: To run a specific scenario from IntelliJ, for example faced to staging environment, don't forget to add `-Dproperties=serenity_stg.properties` to the `VM Options` to that scenario Run Configuration.
