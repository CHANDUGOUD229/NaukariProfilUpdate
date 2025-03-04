package runner;


import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "@target/failedRerun.txt",
        glue = {"stepDefinition"},
        monochrome = true,
        tags = "@tag",
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-pretty",
               "json:target/cucumber-reports/CucumberTestReport.json",
               "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "rerun:target/failedRerun.txt"
        }
)

public class FailedTestRunner {
}
