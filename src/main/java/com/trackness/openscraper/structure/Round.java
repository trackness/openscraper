package com.trackness.openscraper.structure;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

import static com.trackness.openscraper.App.DEBUG_DONE;
import static com.trackness.openscraper.App.MATCH_SETTING_DEBUG;
import static com.trackness.openscraper.structure.Tournament.PAD_MATCH;
import static com.trackness.openscraper.structure.Tournament.PAD_PLAYER;
import static com.trackness.openscraper.structure.Tournament.PAD_ROUND;
import static com.trackness.openscraper.structure.Tournament.PAD_WINNER;

class Round {
    private String name;
    private int index;
    private ArrayList<Match> matches;

    public String getName() { return name; }
    public int getIndex() { return index; }
    ArrayList<Match> getMatches() { return matches; }

    void setMatchList(ArrayList<Match> firstRoundMatchList) {
        if (MATCH_SETTING_DEBUG) System.out.println(String.format("- (ROUND) Setting %s results for round 1.. ", firstRoundMatchList.size()));
        for (int i = 0; i < firstRoundMatchList.size(); i++) {
            if (MATCH_SETTING_DEBUG) System.out.print(String.format(
                    "- Setting round %s match %s / %s %s",
                    index + 1,
                    i + 1,
                    firstRoundMatchList.size(),
                    String.format(
                            "- %s vs %s",
                            firstRoundMatchList.get(i).getPlayer1().getNameStandard(),
                            firstRoundMatchList.get(i).getPlayer2().getNameStandard()
                    )
            ));
            if (MATCH_SETTING_DEBUG) System.out.println(DEBUG_DONE);
            firstRoundMatchList.get(i).setExpectedWinner();
        }
        this.matches = firstRoundMatchList;
    }

    void setMatchListFromPriorRound(ArrayList<Match> matchListFromPriorRound) {
        if (MATCH_SETTING_DEBUG) System.out.println(String.format("- (ROUND) Setting %s results for round %s from round %s.. ", matchListFromPriorRound.size() / 2, index + 1, index));
        for (int i = 0; i < matchListFromPriorRound.size() / 2; i++) {
            String matchUp = String.format("- %s vs %s", matchListFromPriorRound.get(i*2).getExpectedWinner().getNameStandard(), matchListFromPriorRound.get(i*2+1).getExpectedWinner().getNameStandard());
            if (MATCH_SETTING_DEBUG) System.out.print(String.format("- Setting round %s match %s / %s %s", index + 1, i + 1, matchListFromPriorRound.size() / 2, matchUp));
            Match match = new Match.Builder()
                    .withPlayer1(matchListFromPriorRound.get(i*2).getExpectedWinner())
                    .withPlayer2(matchListFromPriorRound.get((i*2)+1).getExpectedWinner())
                    .atIndex(i)
                    .inRound(index)
                    .build();
            match.setExpectedWinner();
            matches.add(i, match);
            if (MATCH_SETTING_DEBUG) System.out.println(DEBUG_DONE);
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
        private String name;
        private int index;

        Builder withName(String name) {
            this.name = name;
            return this;
        }

        Builder withIndex(int index) {
            this.index = index;
            return this;
        }

        public Round build() {
            Round round = new Round();
            round.name = this.name;
            round.index = this.index;
            round.matches = new ArrayList<>();
            return round;
        }
    }

}
