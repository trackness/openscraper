package com.trackness.openscraper.structure;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

import static com.trackness.openscraper.structure.Tournament.PAD_MATCH;
import static com.trackness.openscraper.structure.Tournament.PAD_PLAYER;
import static com.trackness.openscraper.structure.Tournament.PAD_ROUND;
import static com.trackness.openscraper.structure.Tournament.PAD_WINNER;

class Round {
    private int index;
    private ArrayList<Match> matches;

    public int getIndex() { return index; }
    ArrayList<Match> getMatches() { return matches; }

    void setMatchList(ArrayList<Match> firstRoundMatchList) {
        for (Match match : firstRoundMatchList) {
            match.setExpectedWinner();
        }
        this.matches = firstRoundMatchList;
    }

    void setMatchListFromPriorRound(ArrayList<Match> matchListFromPriorRound) {
        for (int i = 0; i < matchListFromPriorRound.size() / 2; i++) {
            Match match = new Match.Builder()
                    .withPlayer1(matchListFromPriorRound.get(i*2).getExpectedWinner())
                    .withPlayer2(matchListFromPriorRound.get((i*2)+1).getExpectedWinner())
                    .atIndex(i)
                    .build();
            match.setExpectedWinner();
            matches.add(i, match);
        }

    }

    void printAll() {
        System.out.println(String.format(
                "%s%s%s%s%s%s%s%s%s%s%s",
                "║", StringUtils.center("Round", PAD_ROUND, " "),
                "│", StringUtils.center("Match", PAD_MATCH, " "),
                "║", StringUtils.center("Player 1", PAD_PLAYER, " "),
                "│", StringUtils.center("Player 2", PAD_PLAYER, " "),
                "║", StringUtils.center("Winner", PAD_WINNER, " "),
                "║"
        ));
        System.out.println(String.format(
                "%s%s%s%s%s%s%s%s%s%s%s",
                "╠", StringUtils.center("", PAD_ROUND, "═"),
                "╪", StringUtils.center("", PAD_MATCH, "═"),
                "╬", StringUtils.center("", PAD_PLAYER, "═"),
                "╪", StringUtils.center("", PAD_PLAYER, "═"),
                "╬", StringUtils.center("", PAD_WINNER, "═"),
                "╣"
        ));
        for (Match match : matches) {
            match.printAll(index + 1, matches.size());
        }
    }

    public static class Builder {
        private int index;

        Builder withIndex(int index) {
            this.index = index;
            return this;
        }

        public Round build() {
            Round round = new Round();
            round.index = this.index;
            round.matches = new ArrayList<>();
            return round;
        }
    }

}
