package com.trackness.openscraper.oddschecker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class OddsScraper {

    private static boolean debug = false;
    private static ArrayList<PlayerOdds> playerOddsList = new ArrayList<>();

    public static void main(String[] args) {
        playerOddsList = getOddsFromTable(getTableFromSearch(
                "https://www.oddschecker.com/tennis/australian-open/womens/winner"
        ));
    }

    public static ArrayList<PlayerOdds> getOdds(boolean debugThis, String oddsSource) {
        debug = debugThis;
        return getOddsFromTable(getTableFromSearch(oddsSource));
    }

    private static Elements getTableFromSearch(String oddsSource) {
        System.out.println("--- Getting odds data from OddsChecker ---");
        Elements playerRows = null;
        try {
            Document rawOdds = Jsoup.connect(oddsSource).userAgent("Mozilla").get();
            System.out.println(String.format("%sSource title: %s%s", debug ? "\n" : "", rawOdds.title(), debug ? "\n" : ""));
            playerRows = rawOdds.body().getElementById("t1").getElementsByClass("diff-row evTabRow bc");
            System.out.println(String.format("%sRows found for %s players%s", debug ? "\n" : "", playerRows.size(), debug ? "\n" : ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return playerRows;
    }

    private static ArrayList<PlayerOdds> getOddsFromTable(Elements playerRows) {
        for (Element playerRow : playerRows) {
            String playerName = playerRow.getElementsByClass("popup selTxt").get(0).text();
            Elements playerOddsElements = playerRow.getElementsByAttribute("data-fodds");
            BigDecimal oddsSum = new BigDecimal("0");
            BigDecimal oddsTally = new BigDecimal("0");
            for (Element oddsElement : playerOddsElements) {
                String playerOddsElement = oddsElement.attributes().get("data-fodds");
                if (!playerOddsElement.equals("")) {
                    oddsSum = oddsSum.add(new BigDecimal(playerOddsElement));
                    oddsTally = oddsTally.add(new BigDecimal("1"));
                }
            }
            playerOddsList.add(new PlayerOdds.Builder()
                    .setName(playerName)
                    .setOdds(oddsSum.divide(oddsTally, RoundingMode.valueOf(2)))
                    .setConfidence(oddsTally.intValue())
                    .build());
        }
        if (debug) for (PlayerOdds playerOdds : playerOddsList) {
            System.out.println(String.format("%s: %s, confidence %s",
                playerOdds.getName(),
                playerOdds.getOdds(),
                playerOdds.getConfidence()
            ));
        }
        System.out.println(String.format("%sOdds prepared for %s players%s", debug ? "\n" : "", playerOddsList.size(), debug ? "\n" : ""));
        return playerOddsList;
    }
}
