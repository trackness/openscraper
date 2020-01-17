package com.trackness.openscraper.oddschecker;

public class PlayerOddsList {
    private String name;
    private float odds;
    private int confidence;

    public String  getName() { return name; }
    public float  getOdds() { return odds; }
    public int  getConfidence() { return confidence; }

    public class Builder {
        private String name;
        private float odds;
        private int confidence;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setOdds(float odds) {
            this.odds = odds;
            return this;
        }

        public Builder setConfidence(int confidence) {
            this.confidence = confidence;
            return this;
        }

        public PlayerOddsList build() {
            PlayerOddsList playerOddsList = new PlayerOddsList();
            playerOddsList.name = this.name;
            playerOddsList.odds = this.odds;
            playerOddsList.confidence = this.confidence;
            return playerOddsList;
        }
    }
}
