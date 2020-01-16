package com.trackness.openscraper.handler;

import com.trackness.openscraper.ausopen.DrawParser;
import com.trackness.openscraper.structure.Tournament;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

import static com.trackness.openscraper.structure.StructureConfig.AUS_OPEN;

public class App {

    public static void main(String[] args) throws IOException {
        System.out.println( "--- OpenScraper ---" );
        System.out.println( "- building tournament structure.." );
        Tournament australianOpen = new Tournament.Builder()
                .withName(AUS_OPEN.getName())
                .withMens()
                .withWomens()
                .build()
                ;

        File wSource = new File("src/main/resources/AO_Womens.html");

        Document mDrawDocument = ScraperJsoup.getResultFromFile(wSource);
        DrawParser.parse(mDrawDocument);

    }

}
