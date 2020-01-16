package com.trackness.openscraper.structure;

public class Player {
    private String nameFirst;
    private String nameLast;
    private String nameStandard;
    private String nameFormal;
    private String nationality;
    private int seed = 0;
    private float odds;

    public String getNameFirst() { return nameFirst; }
    public String getNameLast() { return nameLast; }
    public String getNameStandard() { return nameStandard; }
    public String getNameFormal() { return nameFormal; }
    public String getNationality() { return nationality; }
    public int getSeed() { return seed; }
    public float getOdds() { return odds; }

    public void setSeed(int seed) { this.seed = seed; }
    public void setOdds(float odds) { this.odds = odds; }

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
