package com.trackness.openscraper.structure;

import com.trackness.openscraper.oddschecker.PlayerOdds;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<Round> rounds;

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

    private void setFirstRound(ArrayList<Match> matches) { rounds.get(0).setMatchList(matches); }

    private void setRemainingRounds() {
        for (int i = 1; i < rounds.size(); i++) {
            int priorRound = i-1;
            rounds.get(i).setMatchListFromPriorRound(rounds.get(priorRound).getMatches());
        }
    }

    private void oddsMatchCheck(Player player, PlayerOdds playerOdds) {
        if (player.getNameStandard().equals(playerOdds.getName())) {
            player.setOdds(playerOdds.getOdds());
        }
    }

    public String getName() { return name; }

    public ArrayList<Round> getRounds() { return rounds; }

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
