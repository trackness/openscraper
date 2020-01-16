package com.trackness.openscraper.oddschecker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;

public class OddsScraper {

    private static HashMap<String, Float> playerOdds = new HashMap<String, Float>();

    public static void main(String[] args) {
        playerOdds = getOddsFromTable(getResultFromSearch());
    }

    private static HashMap<String, Float> getOddsFromTable(Element table) {



        return playerOdds;
    }

    private static Element getResultFromSearch() {
        System.out.println("--- JSoup from URL: Odds ---");
        Element rawOddsTable = null;
        try {
            Document rawOdds = Jsoup.connect("https://www.oddschecker.com/tennis/australian-open/womens/winner").userAgent("Mozilla").get();
            System.out.printf("%s\n", rawOdds.title());
            rawOddsTable = rawOdds.body().getElementById("t1");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rawOddsTable;
    }

}
