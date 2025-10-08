package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    @Override
    public void initialize(Bootstrap<HoenScannerConfiguration> bootstrap) {
    }

    @Override
    public void run(HoenScannerConfiguration configuration, Environment environment) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<SearchResult> searchResults = new ArrayList<>();

        try (InputStream carStream = getClass().getResourceAsStream("/rental_cars.json")) {
            searchResults.addAll(mapper.readValue(carStream, new TypeReference<List<SearchResult>>() {}));
        }

        try (InputStream hotelStream = getClass().getResourceAsStream("/hotels.json")) {
            searchResults.addAll(mapper.readValue(hotelStream, new TypeReference<List<SearchResult>>() {}));
        }

        environment.jersey().register(new SearchResource(searchResults));
    }

    public static void main(String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }
}
