package com.trackness.openscraper.structure;

public class Match {
    private Player player1;
    private Player player2;
    private Integer round;
    private Integer index;
    private Player expectedWinner;

    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public Integer getRound() { return round; }
    public Integer getIndex() { return index; }

    public void printDetails() {
        System.out.printf("Round %s, Match %s: %s%s vs %s%s\n",
                round,
                index + 1,
                player1.getNameStandard(),
                player1.getSeed() != 0 ? String.format(" (%s)", player1.getSeed()) : "",
                player2.getNameStandard(),
                player2.getSeed() != 0 ? String.format(" (%s)", player2.getSeed()) : ""
        );
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