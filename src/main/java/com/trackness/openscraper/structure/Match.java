package com.trackness.openscraper.structure;

public class Match {
    private Player player1;
    private Player player2;
    private Integer index;
    private Player expectedWinner;

    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public Player getExpectedWinner() { return expectedWinner; }

    public Integer getIndex() { return index; }

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

    public static class Builder {
        private Player player1;
        private Player player2;
        private Integer index;

        public Builder withPlayer1(Player player1) {
            this.player1 = player1;
            return this;
        }

        public Builder withPlayer2(Player player2) {
            this.player2 = player2;
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
            match.index = this.index;
            return match;
        }
    }
}