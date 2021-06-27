package com.trackness.openscraper.structure;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class Player {
    private String nameStandard;
    private String nameFormal;
    private String nationality;
    private int seed = 0;
    private String nonSeed = "";
    private BigDecimal odds = new BigDecimal("999");

    String getNameStandard() { return nameStandard; }
    public String getNameFormal() { return nameFormal; }
    public String getNationality() { return nationality; }
    public int getSeed() { return seed; }
    BigDecimal getOdds() { return odds; }

    public void setSeed(int seed) { this.seed = seed; }
    public void setNonSeed(String nonSeed) { this.nonSeed = nonSeed; }
    void setOdds(BigDecimal odds) { this.odds = odds; }

    public String tableSeed() {
        String seedString  = "";
        if (!nonSeed.equals("")) seedString = nonSeed;
        if (seed != 0) seedString = String.valueOf(seed);
        return seedString;
    }

    public static class Qualifier{
        public Player build() {
            Player player = new Player();
            player.nameStandard = "Q";
            player.nameFormal = "Q";
            player.nationality = "-Q-";
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
