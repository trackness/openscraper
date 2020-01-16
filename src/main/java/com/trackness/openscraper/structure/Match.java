package com.trackness.openscraper.structure;

public class Match {
    private String player1;
    private String player2;
    private Integer round;
    private Integer index;
    private Player expectedWinner;

    public String getPlayer1() { return player1; }
    public String getPlayer2() { return player2; }
    public Integer getRound() { return round; }
    public Integer getIndex() { return index; }

    public void printDetails() {
        System.out.printf("Round %s, Match %s: %s vs %s\n",
                player1,
                player2,
                round,
                index
        );
    }

    public static class Builder {
        private String player1;
        private String player2;
        private Integer round;
        private Integer index;

        public Builder withPlayer1(String player1) {
            this.player1 = player1;
            return this;
        }

        public Builder withPlayer2(String player2) {
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

//    Match match = new Match.Builder()
//            .withPlayer1(player1)
//            .withPlayer2(player2)
//            .inRound(0)
//            .atIndex(i)
//            .build();
//
//                match.printDetails();
