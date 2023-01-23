package com.example.gui;

import io.cucumber.core.feature.FeatureParser;
import io.cucumber.core.resource.Resource;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FeaturesBuilder {
    private static final String[] FILTER_EXPRESSION = {"feature"};

    static List<Feature> getFeatures(final String rootDirectoryPath){
        Collection<File> files = FileUtils.listFiles(new File(rootDirectoryPath), FILTER_EXPRESSION, true);
        List<io.cucumber.core.gherkin.Feature> featureList = files.stream().map(file -> parseFeature("file://" + file.getPath())).collect(Collectors.toList());
        List<Feature> features = featureList.stream().map(feature -> Feature.toFeature(feature)).collect(Collectors.toList());
        return features;
    }

    private static io.cucumber.core.gherkin.Feature parseFeature(final String path) {
        return parseFeature(URI.create(path));
    }

    private static io.cucumber.core.gherkin.Feature parseFeature(final URI path) {
        return new FeatureParser(UUID::randomUUID).parseResource(new Resource() {
            @Override
            public URI getUri() {
                return path;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                String source = Files.readString(Paths.get(path));
                return new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
            }

        }).orElse(null);
    }

}
