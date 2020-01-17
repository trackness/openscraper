package com.trackness.openscraper.oddschecker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

import static java.lang.Float.parseFloat;

public class OddsScraper {

    private static HashMap<String, Float> playerOdds = new HashMap<>();

    public static void main(String[] args) {
        playerOdds = getOddsFromTable(getTableFromSearch());
    }

    public static HashMap<String, Float> getOdds() {
        return getOddsFromTable(getTableFromSearch());
    }

    private static Element getTableFromSearch() {
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

    private static HashMap<String, Float> getOddsFromTable(Element table) {
        Elements playerRows = table.getElementsByClass("diff-row evTabRow bc");
        for (Element playerRow : playerRows) {
            String playerName = playerRow.getElementsByClass("popup selTxt").get(0).text();
            Elements playerOddsElements = playerRow.getElementsByAttribute("data-fodds");
            float oddsAverage;
            float oddsSum = 0F;
            int oddsTally = 0;
            for (Element oddsElement : playerOddsElements) {
                String playerOddsElement = oddsElement.attributes().get("data-fodds");
                if (!playerOddsElement.equals("")) {
                    oddsSum += parseFloat(playerOddsElement);
                    oddsTally += 1;
                }
            }
            oddsAverage = oddsSum / oddsTally;
            System.out.println(playerName + ": " + oddsAverage + ", confidence: " + oddsTally);
            playerOdds.put(playerName, oddsAverage);
        }
        System.out.printf("Player rows: %s", playerRows.size());
        return playerOdds;
    }

}
