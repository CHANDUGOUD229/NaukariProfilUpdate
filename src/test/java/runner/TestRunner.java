package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        features = "./src/test/java/features",
        glue = {"stepDefinition"},
        monochrome = true,//Display the console more readability
//      tags = "@retesting",//"@tag or @tag2","@tag and @tag2" we can also assign feature level
        dryRun = false,//true: Checks if all the Steps have the StepDefinition
        plugin = {"pretty",
                "json:target/cucumber-reports/Cucumber.json",  // Generates JSON report
                "html:target/cucumber-reports/Cucumber.html", // Generates HTML report
                "com.aventstack.chaintest.plugins.ChainTestCucumberListener:",
                "rerun:target/failedRerun.txt" // it will create the file for failed scenarios
        })
public class TestRunner extends AbstractTestNGCucumberTests {

}
