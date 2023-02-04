package com.example.gui;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FeatureController {

    @Autowired
    private ExecutionService executionService;
    private final List<Feature> features;
    private final String cucumberProjectRootPath;

    private final String[] cmdArgs;


    public FeatureController(ApplicationArguments applicationArguments) throws IllegalArgumentException {
        this.cmdArgs = applicationArguments.getSourceArgs();
        this.cucumberProjectRootPath = System.getProperty("workspace");
        if(this.cucumberProjectRootPath == null){
            throw new IllegalArgumentException("Please Set Cucumber Pdoject Path. -Dworkspace=<filepath>");
        }
        this.features = FeaturesBuilder.getFeatures(cucumberProjectRootPath);
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute("features", features);
        return "list";
    }

    @PostMapping
    public String execute(String featureIndex, String scenarioIndex){
        Scenario scenario = this.features.get(Integer.valueOf(featureIndex)).getScenario().get(Integer.valueOf(scenarioIndex));
        int exitValue = executionService.execute(cucumberProjectRootPath, scenario, cmdArgs);
        if(exitValue == 0){
            scenario.setSuccess(true);
            this.features.get(Integer.valueOf(featureIndex)).getScenario().set(Integer.valueOf(scenarioIndex), scenario);
        }else{
            scenario.setSuccess(false);
        }
        return "redirect:";
    }
}
