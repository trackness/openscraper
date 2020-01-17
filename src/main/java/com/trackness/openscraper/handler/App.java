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

    private static final File drawSourceMen = new File("src/main/resources/AO_Mens.html");
    private static final File drawSourceWomen = new File("src/main/resources/AO_Womens.html");
    private static final String oddsSourceMen = "https://www.oddschecker.com/tennis/australian-open/mens/mens-australian-open/winner";
    private static final String oddsSourceWomen = "https://www.oddschecker.com/tennis/australian-open/womens/winner";

    public static void main(String[] args) throws IOException {
//        genderAusOpen(oddsSourceMen, drawSourceMen);
        genderAusOpen(oddsSourceWomen, drawSourceWomen);
        Tournament australianOpen = new Tournament.Builder()
                .withName(AUS_OPEN.getName())
                .withMens()
                .withWomens()
                .build()
                ;
    }

    private static void genderAusOpen(String oddsSource, File drawSource) throws IOException {
        ArrayList<PlayerOdds> oddsList = OddsScraper.getOdds(debug, oddsSource);
        ArrayList<Match> firstRoundMatches = DrawScraper.getFirstRoundMatches(debug, drawSourceWomen, oddsList);
    }

}
