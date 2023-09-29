package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features",
		glue = "steps",
		tags = "@Regression",
		monochrome = false,
		plugin = {
			"pretty",
			"json:target/cucumber-reports/report.json",
			"html:target/cucumber-reports/report.html",
		}
)

public class CucumberRunner {

}
