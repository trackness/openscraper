package com.trackness.openscraper.structure;

public enum StructureConfig {

    AUS_OPEN("Australian Open"),

    MENS("Mens"),
    WOMENS("Womens"),

    ROUND_1("Round 1"),
    ROUND_2("Round 2"),
    ROUND_3("Round 3"),
    ROUND_4("Round 4"),
    ROUND_5("Quarterfinals"),
    ROUND_6("Semifinals"),
    ROUND_7("Final"),
    ;

    private final String name;

    StructureConfig(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
