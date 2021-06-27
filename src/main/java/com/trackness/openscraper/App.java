package com.trackness.openscraper;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import com.trackness.openscraper.io.ExtFile;
import com.trackness.openscraper.io.draws.RolandGarros;
import com.trackness.openscraper.odds.OddsScraper;
import com.trackness.openscraper.output.Tabler;
import com.trackness.openscraper.structure.Category;
import com.trackness.openscraper.structure.Tournament;

public class App {

    private static final String AUSTRALIAN_OPEN = "Australian Open";
    private static final String ROLAND_GARROS = "Roland Garros";
    private static final String WIMBLEDON = "Wimbledon";
    private static final String US_OPEN = "US Open";

    public static void main(String[] args) throws IOException {
        Properties config = loadConfig("2021_rg.properties");
//        String cache_template = config.getProperty("tournament.cache");
        //    TODO - Provide string template for tournament name and date to be used
        Tournament tournament = buildTournament(config);
//        ExtFile.serializeAndSave(String.format(cache_template, "0"), tournament);
        tournament.setAllResults();
//        ExtFile.serializeAndSave(String.format(cache_template, "1"), tournament);
//        Printer.tournamentToText(tournament);
        Tabler.asciiTabler(tournament);
    }

    private static Tournament buildTournament(Properties properties) throws IOException {

//        Tournament tournament;
//
//        switch (properties.getProperty("tournament.name")) {
//            case ROLAND_GARROS:
//                tournament = new RolandGarros(properties);
//                break;
//            default:
//                System.exit(0);
//        }

        return new Tournament.Builder()
                .withName(properties.getProperty("tournament.name"))
                .withCategories(new ArrayList<>(Arrays.asList(
                        new Category.Builder()
                                .withName(properties.getProperty("category.mens.name"))
                                .withRounds(parseInt(properties.getProperty("tournament.rounds")))
                                .withDrawSource(RolandGarros.scrapeMatchData(
                                        properties.getProperty("tournament.url.base"),
                                        properties.getProperty("category.mens.url")
                                ))
                                .withOddsSource(OddsScraper.getOddsFromUrl(properties.getProperty("category.mens.url.odds")))
                                .build(),
                        new Category.Builder()
                                .withName(properties.getProperty("category.womens.name"))
                                .withRounds(parseInt(properties.getProperty("tournament.rounds")))
                                .withDrawSource(RolandGarros.scrapeMatchData(
                                        properties.getProperty("tournament.url.base"),
                                        properties.getProperty("category.womens.url")
                                ))
                                .withOddsSource(OddsScraper.getOddsFromUrl(properties.getProperty("category.womens.url.odds")))
                                .build()
                )))
                .build();
    }

    private static Properties loadConfig(String propertiesSource) {
        final File configFile = new File("src/main/resources/" + propertiesSource);
        Properties properties = new Properties();
        try {
            FileReader reader = new FileReader(configFile);
            properties.load(reader);
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("fuck");
        } catch (IOException ex) {
            // I/O error
        }
        return properties;
    }
}
