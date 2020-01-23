package com.trackness.openscraper.structure;

import java.util.ArrayList;

public class Tournament {

    private String name;
    private ArrayList<Category> categories;

    public String getName() { return name; }
    public ArrayList<Category> getCategories() { return categories; }

    public void setAllResults() {
        for (Category category : categories) {
            category.setAllResults();
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
