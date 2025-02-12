package com.thinkingtesters.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.thinkingtesters.steps", "com.thinkingtesters.hooks"},
    plugin = {
        "pretty",
        "json:target/cucumber-reports/cucumber.json",
        "html:target/cucumber-reports/cucumber-html",
        "junit:target/cucumber-reports/cucumber.xml",
        "rerun:target/cucumber-reports/rerun.txt"
    },
    monochrome = true,
    publish = true
)
public class TestRunner {
}
