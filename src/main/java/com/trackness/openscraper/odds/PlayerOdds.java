package com.trackness.openscraper.odds;

import java.math.BigDecimal;

public class PlayerOdds {
    private String name;
    private BigDecimal odds;
    private int confidence;

    public String  getName() { return name; }
    public BigDecimal getOdds() { return odds; }
    public int  getConfidence() { return confidence; }

    public static class Builder {
        private String name;
        private BigDecimal odds;
        private int confidence;

        Builder setName(String name) {
            this.name = name;
            return this;
        }

        Builder setOdds(BigDecimal odds) {
            this.odds = odds;
            return this;
        }

        Builder setConfidence(int confidence) {
            this.confidence = confidence;
            return this;
        }

        public PlayerOdds build() {
            PlayerOdds playerOdds = new PlayerOdds();
            playerOdds.name = this.name;
            playerOdds.odds = this.odds;
            playerOdds.confidence = this.confidence;
            return playerOdds;
        }
    }
}
