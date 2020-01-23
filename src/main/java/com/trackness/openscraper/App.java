package com.trackness.openscraper;

import com.trackness.openscraper.io.DrawScraper.AusOpen;
import com.trackness.openscraper.io.ExtFile;
import com.trackness.openscraper.oddschecker.OddsScraper;
import com.trackness.openscraper.output.Printer;
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
//    TODO - Provide string template for tournament name and date to be used
    private static final String FILE = "text.txt";

    private static final Properties PROPERTIES = loadConfig();

    public static void main(String[] args) throws IOException {
        ausOpen();
    }

    private static void ausOpen() throws IOException {
        Tournament tournament = new Tournament.Builder()
                .withName(PROPERTIES.getProperty("tournament.name"))
                .withCategories(new ArrayList<>(Arrays.asList(
                        new Category.Builder()
                                .withName(PROPERTIES.getProperty("category.mens.name"))
                                .withRounds(parseInt(PROPERTIES.getProperty("tournament.rounds")))
                                .withDrawSource(AusOpen.getMatchesFromFile(PROPERTIES.getProperty("category.mens.file.players")))
                                .withOddsSource(OddsScraper.getOddsFromUrl(PROPERTIES.getProperty("category.mens.url.odds")))
                                .build(),
                        new Category.Builder()
                                .withName(PROPERTIES.getProperty("category.womens.name"))
                                .withRounds(parseInt(PROPERTIES.getProperty("tournament.rounds")))
                                .withDrawSource(AusOpen.getMatchesFromFile(PROPERTIES.getProperty("category.womens.file.players")))
                                .withOddsSource(OddsScraper.getOddsFromUrl(PROPERTIES.getProperty("category.womens.url.odds")))
                                .build()
                )))
                .build();

        tournament.setAllResults();

        ExtFile.serializeAndSave(FILE, tournament);
//        tournament = deserialize(FILE);
        Printer.tournamentToText(tournament);
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
        } catch (IOException ex) {
            // I/O error
        }
        return props;
    }
}
