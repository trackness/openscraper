package com.trackness.openscraper.oddschecker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Float.parseFloat;

public class OddsScraper {

    private static boolean debug = false;
    private static ArrayList<PlayerOdds> playerOddsList = new ArrayList<>();

    public static void main(String[] args) {
        playerOddsList = getOddsFromTable(getTableFromSearch());
    }

    public static ArrayList<PlayerOdds> getOdds(boolean debugThis) {
        debug = debugThis;
        return getOddsFromTable(getTableFromSearch());
    }

    private static Elements getTableFromSearch() {
        Elements playerRows = null;
        try {
            Document rawOdds = Jsoup.connect("https://www.oddschecker.com/tennis/australian-open/womens/winner").userAgent("Mozilla").get();
            if (debug) System.out.println(String.format("\n%s", rawOdds.title()));
            playerRows = rawOdds.body().getElementById("t1").getElementsByClass("diff-row evTabRow bc");
            if (debug) System.out.println(String.format("Players found: %s\n", playerRows.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return playerRows;
    }

    private static ArrayList<PlayerOdds> getOddsFromTable(Elements playerRows) {
        for (Element playerRow : playerRows) {
            String playerName = playerRow.getElementsByClass("popup selTxt").get(0).text();
            Elements playerOddsElements = playerRow.getElementsByAttribute("data-fodds");
            float oddsSum = 0F;
            int oddsTally = 0;
            for (Element oddsElement : playerOddsElements) {
                String playerOddsElement = oddsElement.attributes().get("data-fodds");
                if (!playerOddsElement.equals("")) {
                    oddsSum += parseFloat(playerOddsElement);
                    oddsTally += 1;
                }
            }
            playerOddsList.add(new PlayerOdds.Builder()
                    .setName(playerName)
                    .setOdds(oddsSum / oddsTally)
                    .setConfidence(oddsTally)
                    .build());
        }
        if (debug) for (PlayerOdds playerOdds : playerOddsList) {
             System.out.println(String.format(
                    "%s: %s, confidence %s",
                    playerOdds.getName(),
                    playerOdds.getOdds(),
                    playerOdds.getConfidence()
            ));
        }
        return playerOddsList;
    }
}
