package com.profiling.consul.config;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources" , glue="com.cox.cap.cucumber.steps")
public class consulconfigCucumberTest {

}
