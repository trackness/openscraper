package com.trackness.openscraper.structure;

import org.apache.commons.lang.StringUtils;

import static com.trackness.openscraper.structure.Tournament.PAD_MATCH;
import static com.trackness.openscraper.structure.Tournament.PAD_PLAYER;
import static com.trackness.openscraper.structure.Tournament.PAD_ROUND;
import static com.trackness.openscraper.structure.Tournament.PAD_WINNER;

public class Match {
    private Player player1;
    private Player player2;
    private Integer round;
    private Integer index;
    private Player expectedWinner;

    Player getPlayer1() { return player1; }
    Player getPlayer2() { return player2; }
    Player getExpectedWinner() { return expectedWinner; }
    public Integer getRound() { return round; }
    public Integer getIndex() { return index; }

    public void printDetails() {
        System.out.println(String.format(
                "Round %s, Match %s: %s%s vs %s%s%s",
                round,
                index + 1,
                player1.getNameStandard(),
                player1.getSeed() != 0 ? String.format(" (%s)", player1.getSeed()) : "",
                player2.getNameStandard(),
                player2.getSeed() != 0 ? String.format(" (%s)", player2.getSeed()) : "",
                expectedWinner != null ? String.format(" - expected winner: %s", expectedWinner.getNameStandard()) : ""
        ));
    }

    void setExpectedWinner() {
        switch (player1.getOdds().compareTo(player2.getOdds())) {
            case -1:
                this.expectedWinner = player1;
                break;
            case 0:
                if (Math.random() > 0.5 ) this.expectedWinner = player1;
                else this.expectedWinner = player2;
                break;
            case 1:
                this.expectedWinner = player2;
                break;
        }
    }

    void printAll(int round, int matches) {
        System.out.println(String.format(
                "%s%s%s%s%s%s%s%s%s%s%s",
                "║", StringUtils.center(String.valueOf(round), PAD_ROUND),
                "│", StringUtils.center(String.format(" %2s / %2s ", index + 1, matches), PAD_MATCH),
                "║", StringUtils.rightPad(String.format(" %s ", player1.printAll()), PAD_PLAYER),
                "│", StringUtils.rightPad(String.format(" %s ", player2.printAll()), PAD_PLAYER),
                "║", StringUtils.rightPad(String.format(" %s ", expectedWinner.getNameFormal()), PAD_WINNER),
                "║"
                ));
    }

    public static class Builder {
        private Player player1;
        private Player player2;
        private Integer round;
        private Integer index;

        public Builder withPlayer1(Player player1) {
            this.player1 = player1;
            return this;
        }

        public Builder withPlayer2(Player player2) {
            this.player2 = player2;
            return this;
        }

        public Builder inRound(Integer round) {
            this.round = round;
            return this;
        }

        public Builder atIndex(Integer index) {
            this.index = index;
            return this;
        }

        public Match build() {
            Match match = new Match();
            match.player1 = this.player1;
            match.player2 = this.player2;
            match.round = this.round;
            match.index = this.index;
            return match;
        }
    }
}