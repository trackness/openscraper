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

    private static ArrayList<PlayerOdds> playerOddsList = new ArrayList<>();

    public static ArrayList<PlayerOdds> getOddsFromUrl(String oddsSource) throws IOException {
        return getOddsFromElements(getElementsFromUrl(oddsSource));
    }

    private static Elements getElementsFromUrl(String oddsSourceUrl) throws IOException {
        Document rawOdds = Jsoup.connect(oddsSourceUrl).userAgent("Mozilla").get();
        return rawOdds.body().getElementById("t1").getElementsByClass("diff-row evTabRow bc");
    }

    private static ArrayList<PlayerOdds> getOddsFromElements(Elements foundPlayers) {
        for (Element foundPlayer : foundPlayers) {
            Elements playerOddsElements = foundPlayer.getElementsByAttribute("data-fodds");
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
                    .setName(foundPlayer.getElementsByClass("popup selTxt").get(0).text())
                    .setOdds(oddsSum.divide(oddsTally, RoundingMode.valueOf(2)))
                    .setConfidence(oddsTally.intValue())
                    .build());
        }
        return playerOddsList;
    }
}
