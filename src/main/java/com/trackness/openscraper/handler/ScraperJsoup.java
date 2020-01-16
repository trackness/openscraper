package com.trackness.openscraper.handler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class ScraperJsoup {

    private static Document drawDocument;

    public static void main(String[] args) {
        getResultFromSearch("https://ausopen.com/draws");
    }

    public static Document getResultFromSearch(String searchUrl) {
        System.out.println("--- JSoup from URL ---");
        try {
            drawDocument = Jsoup.connect(searchUrl).userAgent("Mozilla").get();
            System.out.printf("%s\n", drawDocument.title());

//            Elements matches = doc.getElementsByClass("match-teams");
//            System.out.printf("Matches found: %s\n", matches.size());
//
//            for (int i = 0; i < matches.size(); i++) {
//
//                Elements players = matches.get(i).getElementsByClass("score-card");
//
//                String player1 = players.first().getElementsByClass("team-0").text();
//                String player2 = players.last().getElementsByClass("team-1").text();
//
//                Match match = new Match.Builder()
//                        .withPlayer1(player1)
//                        .withPlayer2(player2)
//                        .inRound(0)
//                        .atIndex(i)
//                        .build();
//
//                match.printDetails();
//            }
            // In case of any IO errors, we want the messages written to the console

        } catch (IOException e) {
            e.printStackTrace();
        }

        return drawDocument;

    }


    public static Document getResultFromFile(File drawSource) throws IOException {
        System.out.println( "--- JSoup from file---" );
        Document drawDocument = Jsoup.parse(drawSource, null);
        System.out.printf("Parsed document: %s\n", drawDocument.title());
        return drawDocument;
    }
}
