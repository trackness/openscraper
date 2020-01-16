package com.trackness.openscraper.structure;

import static com.trackness.openscraper.structure.StructureConfig.MENS;
import static com.trackness.openscraper.structure.StructureConfig.WOMENS;

public class Tournament {
    private String name;
    private Gender mens;
    private Gender womens;

    public String getName() { return name; }
    public Gender getMens() { return mens; }
    public Gender getWomens() { return womens; }

    public static class Builder {
        private String name;
        private Gender mens;
        private Gender womens;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withMens() {
            this.mens = new Gender.Builder()
                    .withName(MENS.getName())
                    .build();
            return this;
        }

        public Builder withWomens() {
            this.womens = new Gender.Builder()
                    .withName(WOMENS.getName())
                    .build();
            return this;
        }

        public Tournament build() {
            Tournament tournament = new Tournament();
            tournament.name = this.name;
            tournament.mens = this.mens;
            tournament.womens = this.womens;
            return tournament;
        }
    }

}
