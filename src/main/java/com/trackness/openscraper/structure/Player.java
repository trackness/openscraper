package com.trackness.openscraper.structure;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

import static com.trackness.openscraper.structure.Tournament.PAD_PLAYER;

public class Player {
    private String nameFirst;
    private String nameLast;
    private String nameStandard;
    private String nameFormal;
    private String nationality;
    private int seed = 0;
    private BigDecimal odds = new BigDecimal("999");
    private int confidence = 0;
    private boolean qualifier = false;

    public String getNameFirst() { return nameFirst; }
    public String getNameLast() { return nameLast; }
    String getNameStandard() { return nameStandard; }
    String getNameFormal() { return nameFormal; }
    public String getNationality() { return nationality; }
    int getSeed() { return seed; }
    BigDecimal getOdds() { return odds; }
    public int getConfidence() { return confidence; }
    public boolean getQualifier() { return qualifier; }

    public void setSeed(int seed) { this.seed = seed; }
    void setOdds(BigDecimal odds) { this.odds = odds; }
    void setConfidence(int confidence) { this.confidence = confidence; }

    String printAll() {
        return String.format(
                "%s%s",
                StringUtils.rightPad(String.format("%s %s", nationality, nameFormal), PAD_PLAYER - 6),
                seed != 0 ? StringUtils.leftPad(String.format("(%s)", seed), 4) : ""
        );
    }

    public static class Qualifier{
        public Player build() {
            Player player = new Player();
            player.nameFirst = "Q";
            player.nameLast = "Q";
            player.nameStandard = "Q";
            player.nameFormal = "Q";
            player.nationality = "(QAL)";
            player.qualifier = true;
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
            player.nameLast = this.nameFormal.split(". ")[1];
            player.nameFirst = this.nameStandard.split(String.format(" %s", player.nameLast))[0];
            player.nationality = this.nationality;
            return player;
        }
    }
}
