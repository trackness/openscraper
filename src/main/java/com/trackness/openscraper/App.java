package com.trackness.openscraper;

import com.trackness.openscraper.io.draws.AusOpen;
import com.trackness.openscraper.io.ExtFile;
import com.trackness.openscraper.odds.OddsScraper;
import com.trackness.openscraper.output.Tabler;
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

    public static void main(String[] args) throws IOException {
        String tournament_name = "us_open";
        //    TODO - Provide string template for tournament name and date to be used
        final String FILE = String.format("data/%s/text.txt", tournament_name);
//        Config config = new Config();
        Tournament tournament = buildTournament("2020_uso.properties");
        tournament.setAllResults();
        ExtFile.serializeAndSave(FILE, tournament);
//        Printer.tournamentToText(tournament);
        Tabler.asciiTabler(tournament);
    }

    private static Tournament buildTournament(String propertiesSource) throws IOException {
        final Properties PROPERTIES = loadConfig(propertiesSource);
        return new Tournament.Builder()
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
