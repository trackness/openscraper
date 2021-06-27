package com.trackness.openscraper.structure;

import java.util.ArrayList;

public class Round {
    private int index;
    private ArrayList<Match> matches;

    public int getIndex() { return index; }
    public ArrayList<Match> getMatches() { return matches; }

    void setMatchList(ArrayList<Match> firstRoundMatchList) {
        for (Match match : firstRoundMatchList) {
            match.setExpectedWinner();
        }
        System.out.println(matches);
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
