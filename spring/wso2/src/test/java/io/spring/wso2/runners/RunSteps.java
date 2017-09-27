package io.spring.wso2.runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
	format = { "pretty", "html:target/cucumber" }, 
	monochrome = false, 
	snippets = SnippetType.CAMELCASE,
	glue = "io.spring.wso2.steps", 
	features = {"classpath:features/throttlingtier.feature"}
) 
public class RunSteps {

}
