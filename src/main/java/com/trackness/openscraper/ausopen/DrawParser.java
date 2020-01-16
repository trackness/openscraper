package com.trackness.openscraper.ausopen;

import com.trackness.openscraper.handler.ScraperJsoup;
import com.trackness.openscraper.structure.Match;
import com.trackness.openscraper.structure.Player;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class DrawParser {

    public static void main(String[] args) throws IOException {
        File wSource = new File("src/main/resources/AO_Womens.html");
        parse(ScraperJsoup.getResultFromFile(wSource));
    }

    public static void parse(Document rawDraw) {
        Elements foundMatches = rawDraw.getElementsByClass("score-card carousel-index-0 -first-round -full-draw");
        System.out.printf("Matches found: %s\n", foundMatches.size());
        ArrayList<Match> matches = new ArrayList<>();
        for (int i = 0; i < foundMatches.size(); i++) {
            Elements players = foundMatches.get(i).getElementsByClass("team-detail__players");
            matches.add(i, new Match.Builder()
                    .withPlayer1(playerHelper(players.get(0)))
                    .withPlayer2(playerHelper(players.get(1)))
                    .atIndex(i)
                    .inRound(1)
                    .build());
        }
        for (Match match: matches) {
            match.printDetails();
        }
    }

    private static Player playerHelper(Element rawPlayer) {
        Player player;
        if (rawPlayer.getElementsByClass("player -qualifier").size() != 0) {
            player = new Player.Qualifier().build();
        } else {
            player = new Player.Builder()
                    .setNameStandard(getText(rawPlayer, "player-full-name"))
                    .setNameFormal(getText(rawPlayer,"player-short-name"))
                    .setNationality(getText(rawPlayer,"player-nationality-text"))
                    .build();
            Elements seedElements = rawPlayer.getElementsByClass("player-seed");
            if (seedElements.size() != 0 && !seedElements.first().text().contains("WC")) {
                player.setSeed(parseInt(seedElements.first().toString().replaceAll("\\D", "")));
            }
        }
        return player;
    }

    private static String getText(Element rawPlayer, String classText) {
        return rawPlayer.getElementsByClass(classText).first().text();
    }
}
