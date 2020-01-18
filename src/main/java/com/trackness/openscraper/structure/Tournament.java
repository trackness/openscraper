package com.trackness.openscraper.structure;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

public class Tournament {

    static final int PAD_ROUND = 7;
    static final int PAD_MATCH = 9;
    static final int PAD_PLAYER = 31;
    static final int PAD_WINNER = PAD_PLAYER - 9;
    static final int PAD_TOTAL = PAD_ROUND + PAD_MATCH + PAD_PLAYER * 2 + PAD_WINNER + 4;

    private String name;
    private ArrayList<Category> categories;

    public String getName() { return name; }
    public ArrayList<Category> getCategories() { return categories; }

    static String pad(int count) {
        return String.format("%s",StringUtils.center("", count, "═"));
    }

    public void printAll() {
        for (Category category: categories) {
            category.printAll(String.format("%s", name));
            System.out.println();
        }
    }

    public static class Builder {
        private String name;
        private ArrayList<Category> categories;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCategories(ArrayList<Category> categories) {
            this.categories = categories;
            return this;
        }

        public Tournament build() {
            Tournament tournament = new Tournament();
            tournament.name = this.name;
            tournament.categories = this.categories;
            return tournament;
        }
    }

}
