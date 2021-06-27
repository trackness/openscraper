package com.trackness.openscraper.structure;

import com.trackness.openscraper.odds.PlayerOdds;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<Round> rounds;
    private ArrayList<Match> drawSource;
    private ArrayList<PlayerOdds> oddsSource;

    void setAllResults() {
        System.out.println("### Category.setAllResults()");
        setPlayerOdds(drawSource, oddsSource);
        setFirstRound(drawSource);
        setRemainingRounds();
    }

    private void setPlayerOdds(ArrayList<Match> matches, ArrayList<PlayerOdds> oddsList) {
        System.out.println("### Category.setPlayerOdds()");

        for (Match match : matches) {
            boolean p1 = false;
            boolean p2 = false;
            for (PlayerOdds playerOdds : oddsList) {
                if (oddsMatchCheck(match.getPlayer1(), playerOdds)) { p1 = true; }
                if (oddsMatchCheck(match.getPlayer2(), playerOdds)) { p2 = true; }
            }
            if (!p1) { System.out.println(match.getPlayer1().getNameStandard()); }
            if (!p2) { System.out.println(match.getPlayer2().getNameStandard()); }
        }
    }

    private void setFirstRound(ArrayList<Match> matches) { rounds.get(0).setMatchList(matches); }

    private void setRemainingRounds() {
        for (int i = 1; i < rounds.size(); i++) {
            int priorRound = i-1;
            rounds.get(i).setMatchListFromPriorRound(rounds.get(priorRound).getMatches());
        }
    }

    private boolean oddsMatchCheck(Player player, PlayerOdds playerOdds) {
        String pName = player.getNameStandard().toLowerCase().replaceAll("\\-", " ");
        String oName = playerOdds.getName().toLowerCase().replaceAll("\\-", " ");
//        System.out.println("###### " + pName + " " + oName);
        if (pName.equals(oName)) {
//            System.out.println(player.getNameFormal() + "" + playerOdds.getOdds());
            player.setOdds(playerOdds.getOdds());
            return true;
        }
        return false;
    }

    public String getName() { return name; }

    public ArrayList<Round> getRounds() { return rounds; }

    public static class Builder {
        private String name;
        private ArrayList<Round> rounds;
        private ArrayList<Match> drawSource;
        private ArrayList<PlayerOdds> oddsSource;

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

        public Builder withDrawSource(ArrayList<Match> drawSource) {
            this.drawSource = drawSource;
            return this;
        }

        public Builder withOddsSource(ArrayList<PlayerOdds> oddsSource) {
            this.oddsSource = oddsSource;
            return this;
        }

        public Category build() {
            Category category = new Category();
            category.name = this.name;
            category.rounds = this.rounds;
            category.drawSource = this.drawSource;
            category.oddsSource = this.oddsSource;
            return category;
        }
    }
}
