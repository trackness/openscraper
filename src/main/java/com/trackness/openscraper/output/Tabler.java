package com.trackness.openscraper.output;

import com.trackness.openscraper.structure.Category;
import com.trackness.openscraper.structure.Match;
import com.trackness.openscraper.structure.Round;
import com.trackness.openscraper.structure.Tournament;
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
                    AT_Row row = asciiTable
                            .addRow(
                                    round.getIndex() + 1,
                                    String.format("%s / %s",
                                            StringUtils.leftPad(String.valueOf(match.getIndex() + 1), 2),
                                            StringUtils.leftPad(String.valueOf(round.getMatches().size()), 2)),
                                    "p1",
                                    "p2",
                                    match.getExpectedWinner().getNameFormal());
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

//    private static String  printPlayerFull(Player player) {
//        // TODO Pad based name + seed combined
//        return String.format(T_P_FULL,
//                StringUtils.rightPad(player.getNationality(), PAD_P_NATIONALITY),
//                StringUtils.rightPad(player.getNameFormal(), PAD_P_NAME),
//                StringUtils.leftPad(player.getSeed() != 0 ? String.format("(%s)", player.getSeed()) : "", PAD_P_SEED));
//    }

}
