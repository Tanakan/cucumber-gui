package com.example.gui;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Service
public class ExecutionService {
    private static Logger logger = LoggerFactory.getLogger(ExecutionService.class);

    public int execute(String workDirectory, Scenario scenario, String args[]){
        String[] commands = ArrayUtils.addAll(new String[]{"mvn", "test" ,"-Dcucumber.filter.name=" + scenario.getName()}, args);
        logger.info("workDirectory: {}", workDirectory);
        logger.info("Command: {}", StringUtils.join(commands, " "));
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.directory(new File(workDirectory));
        try {
            Process process= processBuilder.inheritIO().start();
            process.waitFor();
            return process.exitValue();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
