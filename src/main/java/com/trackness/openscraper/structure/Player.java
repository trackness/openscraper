package com.trackness.openscraper.structure;

import java.math.BigDecimal;

public class Player {
    private String nameStandard;
    private String nameFormal;
    private String nationality;
    private int seed = 0;
    private BigDecimal odds = new BigDecimal("999");

    String getNameStandard() { return nameStandard; }
    public String getNameFormal() { return nameFormal; }
    public String getNationality() { return nationality; }
    public int getSeed() { return seed; }
    BigDecimal getOdds() { return odds; }

    public void setSeed(int seed) { this.seed = seed; }
    void setOdds(BigDecimal odds) { this.odds = odds; }

    public static class Qualifier{
        public Player build() {
            Player player = new Player();
            player.nameStandard = "Q";
            player.nameFormal = "Q";
            player.nationality = "(QAL)";
            return player;
        }
    }

    public static class Builder {

        private String nameStandard;
        private String nameFormal;
        private String nationality;

        public Builder setNameStandard(String nameStandard) {
            this.nameStandard = nameStandard;
            return this;
        }

        public Builder setNameFormal(String nameFormal) {
            this.nameFormal = nameFormal;
            return this;
        }

        public Builder setNationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public Player build() {
            Player player = new Player();
            player.nameStandard = this.nameStandard;
            player.nameFormal = this.nameFormal;
            player.nationality = this.nationality;
            return player;
        }
    }
}
