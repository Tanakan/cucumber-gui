package com.example.gui;

import io.cucumber.core.gherkin.Pickle;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.maven.cli.MavenCli;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class ExecutionService {
    public int execute(String workDirectory, Scenario scenario, String ...args){
        ProcessBuilder processBuilder = new ProcessBuilder(ArrayUtils.addAll(new String[]{"mvn", "test", "-Dcucumber.filter.name=" + scenario.getName()}, args));
        processBuilder.directory(new File(workDirectory));
        try {
            Process process= processBuilder.start();
            process.waitFor();
            return process.exitValue();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
