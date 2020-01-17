package com.trackness.openscraper.oddschecker;

public class PlayerOdds {
    private String name;
    private float odds;
    private int confidence;

    String  getName() { return name; }
    float  getOdds() { return odds; }
    int  getConfidence() { return confidence; }

    public static class Builder {
        private String name;
        private float odds;
        private int confidence;

        Builder setName(String name) {
            this.name = name;
            return this;
        }

        Builder setOdds(float odds) {
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
