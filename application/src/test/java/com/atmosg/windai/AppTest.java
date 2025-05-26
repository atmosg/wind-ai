package com.atmosg.windai;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
  features = "src/test/resources",
  plugin = {"pretty", "html:target/cucumber-result"}
)
public class AppTest {

}
 