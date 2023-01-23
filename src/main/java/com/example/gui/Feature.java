package com.example.gui;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class Feature {
    private String name;

    private List<Scenario> scenario;

    static Feature toFeature(io.cucumber.core.gherkin.Feature feature){
        return new Feature(feature.getName().get(),
                feature.getPickles().stream().map(pickle -> new Scenario(pickle.getName())).collect(Collectors.toList()));
    }
}
