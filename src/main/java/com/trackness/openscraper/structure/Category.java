package com.trackness.openscraper.structure;

import com.trackness.openscraper.oddschecker.PlayerOdds;

import java.util.ArrayList;

import static com.trackness.openscraper.App.*;

public class Category {
    private String name;
    private ArrayList<Round> rounds;

    public String getName() { return name; }
    public ArrayList<Round> getRounds() { return rounds; }

    public void setAllResults(ArrayList<Match> matches, ArrayList<PlayerOdds> oddsList) {
        if (DEBUG) System.out.println(DEBUG_LINE + "\n--- Setting all results for the " + name + " category ---\n" + DEBUG_LINE);
        setPlayerOdds(matches, oddsList);
        setFirstRound(matches);
        setRemainingRounds();
    }
    private void setPlayerOdds(ArrayList<Match> matches, ArrayList<PlayerOdds> oddsList) {
        if (DEBUG) System.out.println(String.format("- Setting odds for %s players from %s playerOdds.. ", matches.size()*2, oddsList.size()));
        int matchedCounter = 0;
        for (Match match : matches) {
            for (PlayerOdds playerOdds : oddsList) {
                if (oddsMatchCheck(match.getPlayer1(), playerOdds)) matchedCounter++;
                if (oddsMatchCheck(match.getPlayer2(), playerOdds)) matchedCounter++;
            }
            match.setExpectedWinner();
        }
        if (DEBUG) System.out.println(String.format("- Odds set for %s / %s players", matchedCounter, matches.size()*2));
    }

    private void setFirstRound(ArrayList<Match> matches) {
        if (DEBUG) System.out.print(String.format("- (CATEGORY) Setting %s results for round 1.. ", matches.size()));
        rounds.get(0).setMatchList(matches);
        if (DEBUG) System.out.println(String.format("(CATEGORY) %s results set", matches.size()));

    }

    private void setRemainingRounds() {
        if (DEBUG) System.out.print("- Setting results for remaining rounds.. ");
        for (int i = 1; i < rounds.size(); i++) {
            int priorRound = i-1;
            if (DEBUG) System.out.print(String.format("- (CATEGORY) Setting %s results for round %s from round %s.. ", rounds.get(priorRound).getMatches().size() / 2, i + 1, priorRound + 1));
            rounds.get(i).setMatchListFromPriorRound(rounds.get(priorRound).getMatches());
        }
    }

    private boolean oddsMatchCheck(Player player, PlayerOdds playerOdds) {
        if (ODDS_DEBUG) System.out.println(String.format("Checking %s against %s", player.getNameStandard(), playerOdds.getName()));
        boolean matched = false;
        if (player.getNameStandard().equals(playerOdds.getName())) {
            player.setOdds(playerOdds.getOdds());
            player.setConfidence(playerOdds.getConfidence());
            matched = true;
            if (MATCH_DEBUG && matched) System.out.println(String.format("Matched from playerOdds: %s", player.getNameStandard()));
        }
        return matched;
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
