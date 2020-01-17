package com.trackness.openscraper.handler;

import com.trackness.openscraper.ausopen.DrawScraper;
import com.trackness.openscraper.oddschecker.OddsScraper;
import com.trackness.openscraper.structure.Match;
import com.trackness.openscraper.structure.Tournament;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.trackness.openscraper.structure.StructureConfig.AUS_OPEN;

public class App {

    static HashMap<String, Float> odds = new HashMap<>();
    static ArrayList<Match> firstRoundMatchesWomen = new ArrayList<>();
    static final File drawSourceWomen = new File("src/main/resources/AO_Womens.html");

    public static void main(String[] args) throws IOException {
        odds = OddsScraper.getOdds();
        firstRoundMatchesWomen = DrawScraper.getFirstRoundMatches(drawSourceWomen);
        Tournament australianOpen = new Tournament.Builder()
                .withName(AUS_OPEN.getName())
                .withMens()
                .withWomens()
                .build()
                ;
    }

}
