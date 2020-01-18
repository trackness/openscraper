package com.trackness.openscraper.oddschecker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static com.trackness.openscraper.App.DEBUG;
import static com.trackness.openscraper.App.DEBUG_LINE;
import static com.trackness.openscraper.App.MATCH_DEBUG;

public class OddsScraper {

    private static ArrayList<PlayerOdds> playerOddsList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        playerOddsList = getOddsFromElements(getElementsFromUrl(
                "https://www.oddschecker.com/tennis/australian-open/womens/winner"
        ));
    }

    public static ArrayList<PlayerOdds> getOddsFromUrl(String oddsSource) throws IOException {
        if (DEBUG) System.out.println(DEBUG_LINE + "\n--- Getting odds data from OddsChecker ---\n" + DEBUG_LINE);
        return getOddsFromElements(getElementsFromUrl(oddsSource));
    }

    private static Elements getElementsFromUrl(String oddsSourceUrl) throws IOException {
        if (DEBUG) System.out.print("- Parsing url to Jsoup document.. ");
        Document rawOdds = Jsoup.connect(oddsSourceUrl).userAgent("Mozilla").get();
        if (DEBUG) System.out.println(String.format("Parsed to document with title: %s", rawOdds.title()));
        if (DEBUG) System.out.print("- Filtering document content for player data.. ");
        Elements foundPlayers = rawOdds.body().getElementById("t1").getElementsByClass("diff-row evTabRow bc");
        if (DEBUG) System.out.println(String.format("Data found for %s players", foundPlayers.size()));
        return foundPlayers;
    }

    private static ArrayList<PlayerOdds> getOddsFromElements(Elements foundPlayers) {
        if (DEBUG) System.out.print(String.format("- Preparing %s player odds objects.. ", foundPlayers.size()));
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
        if (DEBUG) System.out.println(String.format("%s player odds objects prepared", playerOddsList.size()));
        if (MATCH_DEBUG) for (PlayerOdds playerOdds : playerOddsList) playerOdds.printDetails();
        return playerOddsList;
    }
}
