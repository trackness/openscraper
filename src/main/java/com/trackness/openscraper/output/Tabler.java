package com.trackness.openscraper.output;

import com.trackness.openscraper.structure.*;
import de.vandermeer.asciitable.AT_Row;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import org.apache.commons.lang.StringUtils;

public class Tabler {

    public static void asciiTabler(Tournament tournament) {
        CWC_LongestLine cwc = new CWC_LongestLine();
        AsciiTable asciiTable = new AsciiTable();

        asciiTable.addRule();
        for (Category category : tournament.getCategories()) {
            asciiTable
                    .addRow(null, null, null, null, String.format("%s", category.getName()))
                    .setTextAlignment(TextAlignment.CENTER);
            asciiTable.addRule();
            for (Round round : category.getRounds()) {
                asciiTable
                        .addRow("Round", "Match", "Player 1", "Player 2", "Expected Winner")
                        .setTextAlignment(TextAlignment.CENTER);
                asciiTable.addRule();
                for (Match match : round.getMatches()) {
                    Player p1 = match.getPlayer1();
                    Player p2 = match.getPlayer2();
                    Player pw = match.getExpectedWinner();
                    AT_Row row = asciiTable
                            .addRow(
                                    round.getIndex() + 1,
                                    String.format("%s / %s",
                                            StringUtils.leftPad(String.valueOf(match.getIndex() + 1), 2),
                                            StringUtils.leftPad(String.valueOf(round.getMatches().size()), 2)
                                    ),
                                    String.format("%s %s %s",
                                            p1.getNationality(),
                                            StringUtils.leftPad(p1.tableSeed(), 2),
                                            p1.getNameFormal()
                                    ),
                                    String.format("%s %s %s",
                                            p2.getNationality(),
                                            StringUtils.leftPad(p2.tableSeed(), 2),
                                            p2.getNameFormal()
                                    ),
                                    String.format("%s %s %s",
                                            pw.getNationality(),
                                            StringUtils.leftPad(pw.tableSeed(), 2),
                                            pw.getNameFormal()
                                    )
                            );
                    row.getCells().get(0).getContext().setTextAlignment(TextAlignment.CENTER);
                    row.getCells().get(1).getContext().setTextAlignment(TextAlignment.CENTER);
                }
                asciiTable.addRule();
            }
        }
        asciiTable.getRenderer().setCWC(cwc);
        asciiTable.setPaddingLeftRight(1);
        System.out.println(asciiTable.render());
    }
}
