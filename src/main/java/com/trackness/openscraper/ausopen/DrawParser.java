package com.trackness.openscraper.ausopen;

import com.trackness.openscraper.handler.ScraperJsoup;
import com.trackness.openscraper.structure.Player;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class DrawParser {

    public static void main(String[] args) throws IOException {
        File wSource = new File("src/main/resources/AO_Womens.html");
        parse(ScraperJsoup.getResultFromFile(wSource));
    }

    public static void parse(Document rawDraw) {
        Elements foundMatches = rawDraw.getElementsByClass("score-card carousel-index-0 -first-round -full-draw");
        System.out.printf("Matches found: %s\n", foundMatches.size());
//        ArrayList<Match> matches = new ArrayList<>();
        for (int i = 0; i < foundMatches.size(); i++) {
//            Elements players = foundMatches.get(i).getElementById("match-teams").child(0).children();
            Elements players = foundMatches.get(i).getElementsByClass("player");

            Element rawPlayer1 = players.get(0);
            Element rawPlayer2 = players.get(1);
//            System.out.println(String.format("Match %s: P1: %s || P2: %s", i + 1, rawPlayer1.text(), rawPlayer2.text()));
            Player player1 = playerHelper(rawPlayer1);
//
//            Match match = new Match.Builder()
//                    .withPlayer1()
//                    .withPlayer2()
//                    .atIndex(i)
//                    .inRound()
//                    .build();
//            matches.add(i, match);
        }
    }

    private static Player playerHelper(Element rawPlayer) {
//        final String nameStandard = ;
//        final String nameFormal = ;
//        final String nationality = ;
        Elements seedElements = rawPlayer.getElementsByClass("player-seed");
        int seed = seedElements.size() == 0 ? 0 : parseInt(seedElements.first().toString().replaceAll("\\D", ""));

        Player player = new Player.Builder()
                .setNameStandard(rawPlayer.getElementsByClass("player-full-name").first().text())
                .setNameFormal(rawPlayer.getElementsByClass("player-short-name").first().text())
                .setNationality(rawPlayer.getElementsByClass("player-nationality-text").first().text())
                .build();

        if (seed != 0) player.setSeed(seed);
        System.out.println(player.getNameFirst());
        return player;
    }
}
