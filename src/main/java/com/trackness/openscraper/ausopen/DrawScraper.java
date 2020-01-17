package com.trackness.openscraper.ausopen;

import com.trackness.openscraper.oddschecker.PlayerOdds;
import com.trackness.openscraper.structure.Match;
import com.trackness.openscraper.structure.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class DrawScraper {

    private static boolean debug = false;
    private static ArrayList<Match> matches = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        matches = getMatchesFromFile(new File("src/main/resources/AO_Womens.html"));
    }

    public static ArrayList<Match> getFirstRoundMatches(
            boolean debugThis,
            File drawSource,
            ArrayList<PlayerOdds> oddsList
    ) throws IOException {
        debug = debugThis;
        return updateWIthOdds(getMatchesFromFile(drawSource), oddsList);
    }

    private static ArrayList<Match> getMatchesFromFile(File drawSource) throws IOException {
        System.out.println("--- Getting first round match data from draw file ---");
        Document rawDraw = Jsoup.parse(drawSource, null);
        System.out.println(String.format("%sSource title: %s%s", debug ? "\n" : "", rawDraw.title(), debug ? "\n" : ""));
        Elements foundMatches = rawDraw.getElementsByClass("score-card carousel-index-0 -first-round -full-draw");
        System.out.println(String.format("%sData found for %s matches %s", debug ? "\n" : "", foundMatches.size(), debug ? "\n" : ""));
        for (int i = 0; i < foundMatches.size(); i++) {
            Elements players = foundMatches.get(i).getElementsByClass("team-detail__players");
            matches.add(i, new Match.Builder()
                    .withPlayer1(playerHelper(players.get(0)))
                    .withPlayer2(playerHelper(players.get(1)))
                    .atIndex(i)
                    .inRound(1)
                    .build());
        }
        System.out.println(String.format("%s%s matches prepared%s", debug ? "\n" : "", matches.size(), debug ? "\n" : ""));
        if (debug) for (Match match: matches) match.printDetails();
        return matches;
    }

    private static ArrayList<Match> updateWIthOdds(ArrayList<Match> matchArrayList, ArrayList<PlayerOdds> oddsList) {
        System.out.println("--- Applying odds data to first round match data ---");
        System.out.println(String.format("%sSetting odds for matched players%s", debug ? "\n" : "", debug ? "\n" : ""));
        int matched = 0;
        for (Match match : matchArrayList) {
            for (PlayerOdds playerOdds : oddsList) {
                if (match.getPlayer1().getNameStandard().equals(playerOdds.getName())) {
                    match.getPlayer1().setOdds(playerOdds.getOdds());
                    match.getPlayer1().setConfidence(playerOdds.getConfidence());
//                    if (debug) match.getPlayer1().printDetails();
                    matched++;
                }
                if (match.getPlayer2().getNameStandard().equals(playerOdds.getName())) {
                    match.getPlayer2().setOdds(playerOdds.getOdds());
                    match.getPlayer2().setConfidence(playerOdds.getConfidence());
//                    if (debug) match.getPlayer2().printDetails();
                    matched++;
                }
            }
            match.setExpectedWinner();
            if (debug) match.printDetails();
        }
        System.out.println(String.format("%sPlayers matched to odds: %s / %s%s",
                debug ? "\n" : "",
                matched,
                oddsList.size(),
                debug ? "\n" : ""
        ));
        return matchArrayList;
    }

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
