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

import static java.lang.Integer.parseInt;

public class DrawScraper {

    private static ArrayList<Match> matches;

    public static ArrayList<Match> getMatchesFromFile(String drawSourceString) throws IOException {
        matches = new ArrayList<>();
        return getMatchesFromElements(getElementsFromFile(drawSourceString));
    }

    private static Elements getElementsFromFile(String drawSourceFile) throws IOException {
        Document rawDraw = Jsoup.parse(new File(drawSourceFile), null);
        return rawDraw.getElementsByClass("score-card carousel-index-0 -first-round -full-draw");
    }

    private static ArrayList<Match> getMatchesFromElements(Elements foundMatches) {
        for (int i = 0; i < foundMatches.size(); i++) {
            Elements players = foundMatches.get(i).getElementsByClass("team-detail__players");
            matches.add(i, new Match.Builder()
                    .withPlayer1(playerHelper(players.get(0)))
                    .withPlayer2(playerHelper(players.get(1)))
                    .atIndex(i)
                    .build());
        }
        return matches;
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
            if (seedElements.size() != 0
                    && !seedElements.first().text().contains("WC")
                    && !seedElements.first().text().contains("Q")
                    && !seedElements.first().text().contains("LL")
            ) {
                player.setSeed(parseInt(seedElements.first().toString().replaceAll("\\D", "")));
            }
        }
        return player;
    }

    private static String getTextByClass(Element element, String classText) {
        return element.getElementsByClass(classText).first().text();
    }
}
