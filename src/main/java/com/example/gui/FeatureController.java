package com.example.gui;


import org.springframework.beans.factory.annotation.Autowired;
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

    private final String[] args;

    public FeatureController() throws IllegalArgumentException {
        this.cucumberProjectRootPath = System.getProperty("workspace");
        if(this.cucumberProjectRootPath == null){
            throw new IllegalArgumentException("Please Set Cucumber Pdoject Path. -Dworkspace=<filepath>");
        }
        this.args = System.getProperty("executeArgs", "").split(" ");
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
        int exitValue = executionService.execute(cucumberProjectRootPath, scenario, args);
        if(exitValue == 0){
            scenario.setSuccess();
            this.features.get(Integer.valueOf(featureIndex)).getScenario().set(Integer.valueOf(scenarioIndex), scenario);
        }
        return "redirect:";
    }

}
