package com.trackness.openscraper.handler;

import com.trackness.openscraper.ausopen.DrawScraper;
import com.trackness.openscraper.oddschecker.OddsScraper;
import com.trackness.openscraper.oddschecker.PlayerOdds;
import com.trackness.openscraper.structure.Match;
import com.trackness.openscraper.structure.Tournament;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.trackness.openscraper.structure.StructureConfig.AUS_OPEN;

public class App {

    private static boolean debug = true;

    private static ArrayList<PlayerOdds> odds;
    private static ArrayList<Match> firstRoundMatchesWomen;
    private static final File drawSourceWomen = new File("src/main/resources/AO_Womens.html");

    public static void main(String[] args) throws IOException {
        odds = OddsScraper.getOdds(debug);
        firstRoundMatchesWomen = DrawScraper.getFirstRoundMatches(debug, drawSourceWomen);
        Tournament australianOpen = new Tournament.Builder()
                .withName(AUS_OPEN.getName())
                .withMens()
                .withWomens()
                .build()
                ;
    }

}
