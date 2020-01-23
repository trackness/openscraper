package com.trackness.openscraper.output;

import com.trackness.openscraper.structure.Category;
import com.trackness.openscraper.structure.Match;
import com.trackness.openscraper.structure.Player;
import com.trackness.openscraper.structure.Round;
import com.trackness.openscraper.structure.Tournament;
import org.apache.commons.lang.StringUtils;

public class Printer {

    private static final int COLUMN_COUNT = 5;
    private static final int PAD_COLUMNS = 4;
    private static final int PAD_INNER = 1;

    private static final int PAD_R = 5;
    private static final int PAD_M = 7;
    private static final int PAD_P_NATIONALITY = 6;
    private static final int PAD_P_NAME = 20;
    private static final int PAD_P_SEED = 5;
    private static final int PAD_P_FULL = PAD_P_NATIONALITY + PAD_P_NAME + PAD_P_SEED;

    private static final int PAD_R_HEADER = PAD_R + PAD_INNER * 2;
    private static final int PAD_M_HEADER = PAD_M + PAD_INNER * 2;
    private static final int PAD_P_NAME_HEADER = PAD_P_NAME + PAD_INNER * 2;
    private static final int PAD_P_FULL_HEADER = PAD_P_FULL + PAD_INNER * 2;

    private static final int PAD_C_HEADER = PAD_R_HEADER + PAD_M_HEADER + PAD_P_FULL_HEADER * 2 + PAD_P_NAME_HEADER + PAD_COLUMNS;

    private static final String T_P_FULL = "%s%s%s";
    private static final String T_LINE = "%s".repeat(COLUMN_COUNT * 2 + 1);

    private static void dividingLine(String c0, String c1, String c2, String c3, String c4, String c5) {
        System.out.println(String.format(T_LINE,
                c0, "═".repeat(PAD_R_HEADER),
                c1, "═".repeat(PAD_M_HEADER),
                c2, "═".repeat(PAD_P_FULL_HEADER),
                c3, "═".repeat(PAD_P_FULL_HEADER),
                c4, "═".repeat(PAD_P_NAME_HEADER),
                c5
        ));
    }

    public static void tournamentToText(Tournament tournament) {
        for (Category category : tournament.getCategories()) {
            printCategoryHeader(tournament.getName(), category);
            for (Round round : category.getRounds()) {
                printRoundHeader();
                for (Match match : round.getMatches()) {
                    printMatchLine(round, match);
                }
                printRoundFooter(round.getIndex(), category.getRounds().size());
            }
        }
    }

    private static void printCategoryHeader(String tournamentName, Category category) {
        dividingLine("╔", "═", "═", "═", "═", "╗");
        System.out.println(String.format("║%s║", StringUtils.center(tournamentName + " - " + category.getName(), PAD_C_HEADER)));
        dividingLine("╠", "╤", "╦", "╤", "╦", "╣");
    }

    private static void printRoundHeader() {
        System.out.println(
                String.format("║ %s ", StringUtils.center("Round", PAD_R)) +
                String.format("│ %s ", StringUtils.center("Match", PAD_M)) +
                String.format("║ %s ", StringUtils.center("Player 1", PAD_P_FULL)) +
                String.format("│ %s ", StringUtils.center("Player 2", PAD_P_FULL)) +
                String.format("║ %s ", StringUtils.center("Expected Winner", PAD_P_NAME)) +
                "║");
        dividingLine("╠", "╪", "╬", "╪", "╬", "╣");
    }

    private static void printMatchLine(Round round, Match match) {
        System.out.println(
                String.format("║   %s   ", round.getIndex() + 1) +
                String.format("│ %2s / %2s ", match.getIndex() + 1, round.getMatches().size()) +
                String.format("║ %s ", printPlayerFull(match.getPlayer1())) +
                String.format("│ %s ", printPlayerFull(match.getPlayer2())) +
                String.format("║ %s ", printPlayerFormal(match.getExpectedWinner())) +
                "║");
    }

    private static String  printPlayerFull(Player player) {
        return String.format(T_P_FULL,
                StringUtils.rightPad(player.getNationality(), PAD_P_NATIONALITY),
                StringUtils.rightPad(player.getNameFormal(), PAD_P_NAME),
                StringUtils.leftPad(player.getSeed() != 0 ? String.format("(%s)", player.getSeed()) : "", PAD_P_SEED));
    }

    private static String printPlayerFormal(Player player) {
        return StringUtils.rightPad(player.getNameFormal(), PAD_P_NAME);
    }

    private static void printRoundFooter(int index, int size) {
        if (index + 1 != size) dividingLine("╠", "╪", "╬", "╪", "╬", "╣");
        else dividingLine("╚", "╧", "╩", "╧", "╩", "╝");
    }

}
