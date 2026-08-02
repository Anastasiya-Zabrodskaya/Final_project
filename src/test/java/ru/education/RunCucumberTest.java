package ru.education;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;


@Suite

@IncludeEngines("cucumber")

@SelectClasspathResource("features")

@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports.html, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")

@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "ru.education.steps")
public class RunCucumberTest {

}