package com.trackness.openscraper.io.draws;

import com.trackness.openscraper.structure.Match;
import com.trackness.openscraper.structure.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class RolandGarros {

    private static Document jsoupGet(String url) throws IOException {
        int maxRetries = 5;
        Document document = null;

        for (int i = 1; i <= maxRetries; i++) {
            try{
                document = Jsoup.connect(url).get();
                break;
            }
            catch (SocketTimeoutException e) {
                System.out.println("jsoup Timeout occurred " + i + "/" + maxRetries + " time(s) for " + url);
            }
        }

        if (document == null) {
            System.out.println("Exiting..");
            System.exit(0);
        }
        return document;
    }

    public static ArrayList<Match> scrapeMatchData(String baseUrl, String genderTemplate) throws IOException {
        ArrayList<Match> matches = new ArrayList<>();
        for (int i = 64; i <= 127; i++) {
            String matchUrl = String.format("%s/%s", baseUrl, String.format(genderTemplate, i));
            System.out.println(matchUrl);
            Document matchHtml = jsoupGet(matchUrl);
            int matchIndex = i - 64;
            matches.add(matchIndex, generateMatch(baseUrl, matchIndex, matchHtml));
        }
        return matches;
    }

    private static Match generateMatch(String baseUrl, int matchIndex, Document matchHtml) throws IOException {
        Elements playerUrls = matchHtml.getElementsByClass("select-container");
        Elements playerNameBlocks = matchHtml.getElementsByClass("player-name");

        return new Match.Builder()
                .withPlayer1(generatePlayer(
                        baseUrl,
                        playerNameBlocks.get(0),
                        playerUrls.get(0).select("a").attr("href")
                ))
                .withPlayer2(generatePlayer(
                        baseUrl,
                        playerNameBlocks.get(1),
                        playerUrls.get(1).select("a").attr("href")
                ))
//                TODO : refactor this out in Tabler
                .atIndex(matchIndex)
                .build();
    }

    private static Player generatePlayer(String baseUrl, Element nameBlock, String playerUrl) throws IOException {
        String nameFormal = nameBlock.getElementsByClass("name avenir").get(0).text();
        Player player;
        if (nameFormal.equals("Qualifié")) {
            player = new Player.Qualifier().build();
        } else {
            Document playerHtml = jsoupGet(String.format("%s/%s", baseUrl, playerUrl));
            player = new Player.Builder()
                    .setNameStandard(
                            getTextByClass(playerHtml, "first-name") +
                            " " +
                            getTextByClass(playerHtml, "last-name")
                    )
                    .setNameFormal(nameFormal)
                    .setNationality(getTextByClass(playerHtml, "country"))
                    .build();
            Elements seedSpan = nameBlock.getElementsByClass("num");

            if (seedSpan.size() != 0) {
                String seedText = seedSpan.first().text();
                switch (seedText) {
                    case "(W)":
                        player.setNonSeed("W");
                        break;
                    case "(L)":
                        player.setNonSeed("L");
                        break;
                    case "(Q)":
                        player.setNonSeed("Q");
                        break;
                    default:
                        player.setSeed(
                                parseInt(seedText
                                        .replaceAll("\\)", "")
                                        .replaceAll("\\(", "")
                        ));
                        break;
                }
            }
        }
        return player;
    }

    private static String getTextByClass(Element element, String classText) {
        return element.getElementsByClass(classText).first().text();
    }
}
