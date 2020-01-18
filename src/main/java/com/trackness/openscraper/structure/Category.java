package com.trackness.openscraper.structure;

import com.trackness.openscraper.oddschecker.PlayerOdds;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

import static com.trackness.openscraper.structure.Tournament.PAD_MATCH;
import static com.trackness.openscraper.structure.Tournament.PAD_PLAYER;
import static com.trackness.openscraper.structure.Tournament.PAD_ROUND;
import static com.trackness.openscraper.structure.Tournament.PAD_TOTAL;
import static com.trackness.openscraper.structure.Tournament.PAD_WINNER;
import static com.trackness.openscraper.structure.Tournament.pad;

public class Category {
    private String name;
    private ArrayList<Round> rounds;

    public String getName() { return name; }
    public ArrayList<Round> getRounds() { return rounds; }

    public void setAllResults(ArrayList<Match> matches, ArrayList<PlayerOdds> oddsList) {
        setPlayerOdds(matches, oddsList);
        setFirstRound(matches);
        setRemainingRounds();
    }
    private void setPlayerOdds(ArrayList<Match> matches, ArrayList<PlayerOdds> oddsList) {
        for (Match match : matches) { for (PlayerOdds playerOdds : oddsList) {
            oddsMatchCheck(match.getPlayer1(), playerOdds);
            oddsMatchCheck(match.getPlayer2(), playerOdds);
        }}
    }

    private void setFirstRound(ArrayList<Match> matches) {
        rounds.get(0).setMatchList(matches);

    }

    private void setRemainingRounds() {
        for (int i = 1; i < rounds.size(); i++) {
            int priorRound = i-1;
            rounds.get(i).setMatchListFromPriorRound(rounds.get(priorRound).getMatches());
        }
    }

    private void oddsMatchCheck(Player player, PlayerOdds playerOdds) {
        if (player.getNameStandard().equals(playerOdds.getName())) {
            player.setOdds(playerOdds.getOdds());
            player.setConfidence(playerOdds.getConfidence());
        }
    }

    void printAll(String tournamentName) {
        System.out.println(String.format("%s%s%s", "╔", pad(PAD_TOTAL), "╗"));
        System.out.println(String.format(
                "%s%s%s",
                "║",
                StringUtils.center(
                        String.format("%s - %s", tournamentName, name),
                        PAD_TOTAL
                ),
                "║"
        ));

        for (int i = 0; i < rounds.size(); i++) {
            if (i == 0) System.out.println(String.format(
                    "%s%s%s%s%s%s",
                    StringUtils.rightPad("╠", PAD_ROUND + 1, "═"),
                    StringUtils.rightPad("╤", PAD_MATCH + 1, "═"),
                    StringUtils.rightPad("╦", PAD_PLAYER + 1, "═"),
                    StringUtils.rightPad("╤", PAD_PLAYER + 1, "═"),
                    StringUtils.rightPad("╦", PAD_WINNER + 1, "═"),
                    "╣"
            ));
            if (i != 0) System.out.println(String.format(
                    "%s%s%s%s%s%s%s%s%s%s%s",
                    "╠", StringUtils.center("", PAD_ROUND, "═"),
                    "╪", StringUtils.center("", PAD_MATCH, "═"),
                    "╬", StringUtils.center("", PAD_PLAYER, "═"),
                    "╪", StringUtils.center("", PAD_PLAYER, "═"),
                    "╬", StringUtils.center("", PAD_WINNER, "═"),
                    "╣"
            ));
            rounds.get(i).printAll();
        }
        System.out.println(String.format(
                "%s%s%s%s%s%s%s%s%s%s%s",
                "╚", pad(PAD_ROUND),
                "╧", pad(PAD_MATCH),
                "╩", pad(PAD_PLAYER),
                "╧", pad(PAD_PLAYER),
                "╩", pad(PAD_WINNER),
                "╝"
        ));
    }

    public static class Builder {
        private String name;
        private ArrayList<Round> rounds;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withRounds(int numberOfRounds) {
            ArrayList<Round> rounds = new ArrayList<>();
            for (int i = 0; i < numberOfRounds; i++) {
                rounds.add(i, new Round.Builder()
                        .withName("a")
                        .withIndex(i)
                        .build());
            }
            this.rounds = rounds;
            return this;
        }

        public Category build() {
            Category category = new Category();
            category.name = this.name;
            category.rounds = this.rounds;
            return category;
        }
    }

}
