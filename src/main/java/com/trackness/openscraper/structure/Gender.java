package com.trackness.openscraper.structure;

import static com.trackness.openscraper.structure.StructureConfig.ROUND_1;

public class Gender {
    private String name;
    private Round first;
    private Round second;
    private Round third;
    private Round fourth;
    private Round quarters;
    private Round semis;
    private Round finals;

    public String getName() { return name; }

    public static class Builder {
        private String name;
        private Round first;
        private Round second;
        private Round third;
        private Round fourth;
        private Round quarters;
        private Round semis;
        private Round finals;

        Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withFirstRound() {
            this.first = new Round.Builder()
                    .withName(ROUND_1.getName())
                    .build();
            return this;
        }

        public Builder withSecondRound(Round second) {
            this.second = second;
            return this;
        }

        public Builder withThirdRound(Round third) {
            this.third = third;
            return this;
        }

        public Builder withFourthRound(Round fourth) {
            this.fourth = fourth;
            return this;
        }

        public Builder withQuartersRound(Round quarters) {
            this.quarters = quarters;
            return this;
        }

        public Builder withSemisRound(Round semis) {
            this.semis = semis;
            return this;
        }

        public Builder withFinalsRound(Round finals) {
            this.finals = finals;
            return this;
        }



        Gender build() {
            Gender gender = new Gender();
            gender.name = this.name;
            return gender;
        }
    }

}
