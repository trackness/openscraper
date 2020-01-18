package com.trackness.openscraper;

import com.trackness.openscraper.ausopen.DrawScraper;
import com.trackness.openscraper.oddschecker.OddsScraper;
import com.trackness.openscraper.structure.Category;
import com.trackness.openscraper.structure.Tournament;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static java.lang.Integer.parseInt;


public class App {

    public static final String DEBUG_LINE = "-----------------------------------------------------";
    public static final String DEBUG_DONE = " - Done.";
    public static final boolean DEBUG = false;
    public static final boolean MATCH_DEBUG = false;
    public static final boolean ODDS_DEBUG = false;
    public static final boolean MATCH_SETTING_DEBUG = true;
    public static final Properties PROPERTIES = loadConfig();

    public static void main(String[] args) throws IOException {
        Tournament tournament = new Tournament.Builder()
                .withName(PROPERTIES.getProperty("tournament.name"))
                .withCategories(new ArrayList<>(
                        Arrays.asList(
                                new Category.Builder()
                                        .withName(PROPERTIES.getProperty("category.mens.name"))
                                        .withRounds(parseInt(PROPERTIES.getProperty("tournament.rounds")))
                                        .build(),
                                new Category.Builder()
                                        .withName(PROPERTIES.getProperty("category.womens.name"))
                                        .withRounds(parseInt(PROPERTIES.getProperty("tournament.rounds")))
                                        .build()
                        )
                ))
                .build();

//        tournament.getCategories().get(0)
//                .setAllResults(
//                        DrawScraper.getMatchesFromFile(PROPERTIES.getProperty("category.mens.file.players")),
//                        OddsScraper.getOddsFromUrl(PROPERTIES.getProperty("category.mens.url.odds"))
//                );
        tournament.getCategories().get(1)
                .setAllResults(
                        DrawScraper.getMatchesFromFile(PROPERTIES.getProperty("category.womens.file.players")),
                        OddsScraper.getOddsFromUrl(PROPERTIES.getProperty("category.womens.url.odds"))
                        );
    }

    private static Properties loadConfig() {
        File configFile = new File("src/main/resources/application.properties");
        Properties props = new Properties();
        try {
            FileReader reader = new FileReader(configFile);
            props.load(reader);
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("fuck");
            // file does not exist
        } catch (IOException ex) {
            // I/O error
        }
        return props;
    }

}
