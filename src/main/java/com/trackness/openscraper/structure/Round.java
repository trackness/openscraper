package com.trackness.openscraper.structure;

class Round {
    private String name;

    public static class Builder {
        private String name;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Round build() {
            Round round = new Round();
            round.name = this.name;
            return round;
        }
    }

}
