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

    private static ArrayList<Match> matches;

    public static ArrayList<Match> getMatchesFromFile(String drawSourceString) throws IOException {
        matches = new ArrayList<>();
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
