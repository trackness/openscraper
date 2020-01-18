package com.trackness.openscraper.ausopen;

import com.trackness.openscraper.structure.Match;
import com.trackness.openscraper.structure.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.trackness.openscraper.App.DEBUG;
import static com.trackness.openscraper.App.DEBUG_LINE;
import static com.trackness.openscraper.App.MATCH_DEBUG;
import static java.lang.Integer.parseInt;

public class DrawScraper {

    private static ArrayList<Match> matches = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        matches = getMatchesFromFile("src/main/resources/AO_Womens.html");
    }

    public static ArrayList<Match> getMatchesFromFile(String drawSourceString) throws IOException {
        if (DEBUG) System.out.println(DEBUG_LINE + "\n--- Getting first round match data from draw file ---\n" + DEBUG_LINE);
        return getMatchesFromElements(getElementsFromFile(drawSourceString));
    }

    private static Elements getElementsFromFile(String drawSourceFile) throws IOException {
        if (DEBUG) System.out.print("- Parsing file to Jsoup document.. ");
        Document rawDraw = Jsoup.parse(new File(drawSourceFile), null);
        if (DEBUG) System.out.println(String.format("Parsed to document with title: %s", rawDraw.title()));
        if (DEBUG) System.out.print("- Filtering document content for match data.. ");
        Elements foundMatches = rawDraw.getElementsByClass("score-card carousel-index-0 -first-round -full-draw");
        if (DEBUG) System.out.println(String.format("Data found for %s matches", foundMatches.size()));
        return foundMatches;
    }

    private static ArrayList<Match> getMatchesFromElements(Elements foundMatches) {
        if (DEBUG) System.out.print(String.format("- Preparing %s match objects.. ", foundMatches.size()));
        for (int i = 0; i < foundMatches.size(); i++) {
            Elements players = foundMatches.get(i).getElementsByClass("team-detail__players");
            matches.add(i, new Match.Builder()
                    .withPlayer1(playerHelper(players.get(0)))
                    .withPlayer2(playerHelper(players.get(1)))
                    .atIndex(i)
                    .inRound(1)
                    .build());
        }
        if (DEBUG) System.out.println(String.format("%s match objects prepared", matches.size()));
        if (MATCH_DEBUG) for (Match match: matches) match.printDetails();
        return matches;
    }

//    private static ArrayList<Match> updateWithOdds(ArrayList<Match> matchArrayList, ArrayList<PlayerOdds> oddsList) {
//        System.out.println("--- Applying odds data to first round match data ---");
//        System.out.println(String.format("%sSetting odds for matched players%s", DEBUG ? "\n" : "", DEBUG ? "\n" : ""));
//        if (MATCH_DEBUG) System.out.println(String.format("------------ Round %s ------------", 1));
//        int matched = 0;
//        for (Match match : matchArrayList) {
//            for (PlayerOdds playerOdds : oddsList) {
//                if (match.getPlayer1().getNameStandard().equals(playerOdds.getName())) {
//                    match.getPlayer1().setOdds(playerOdds.getOddsFromUrl());
//                    match.getPlayer1().setConfidence(playerOdds.getConfidence());
////                    if (debug) match.getPlayer1().printDetails();
//                    matched++;
//                }
//                if (match.getPlayer2().getNameStandard().equals(playerOdds.getName())) {
//                    match.getPlayer2().setOdds(playerOdds.getOddsFromUrl());
//                    match.getPlayer2().setConfidence(playerOdds.getConfidence());
////                    if (debug) match.getPlayer2().printDetails();
//                    matched++;
//                }
//            }
//            match.setExpectedWinner();
//            if (MATCH_DEBUG) match.printDetails();
//        }
//        System.out.println(String.format("%sPlayers matched to odds: %s / %s%s",
//                DEBUG ? "\n" : "",
//                matched,
//                oddsList.size(),
//                DEBUG ? "\n" : ""
//        ));
//        return matchArrayList;
//    }

    private static Player playerHelper(Element rawPlayer) {
        Player player;
        if (rawPlayer.getElementsByClass("player -qualifier").size() != 0) {
            player = new Player.Qualifier().build();
        } else {
            player = new Player.Builder()
                    .setNameStandard(getTextByClass(rawPlayer, "player-full-name"))
                    .setNameFormal(getTextByClass(rawPlayer,"player-short-name"))
                    .setNationality(getTextByClass(rawPlayer,"player-nationality-text"))
                    .build();
            Elements seedElements = rawPlayer.getElementsByClass("player-seed");
            if (seedElements.size() != 0 && !seedElements.first().text().contains("WC")) {
                player.setSeed(parseInt(seedElements.first().toString().replaceAll("\\D", "")));
            }
        }
        return player;
    }

    private static String getTextByClass(Element element, String classText) {
        return element.getElementsByClass(classText).first().text();
    }
}
