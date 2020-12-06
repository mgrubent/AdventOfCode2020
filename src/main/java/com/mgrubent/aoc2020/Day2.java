package com.mgrubent.aoc2020;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day2 extends Puzzle {
    private final static String MIN_OCCURRENCES = "minOccurrences";
    private final static String MAX_OCCURRENCES = "maxOccurrences";
    private final static String REQUIRED_LETTER = "requiredLetter";
    private final static String PASSWORD = "password";

    private final List<PasswordEntry> _entries;

    /**
     * Constructor which accepts the puzzle input to be solved
     *
     * @param input the puzzle input
     */
    Day2(String input) {
        super(input);
        _entries = input.lines().map(Day2::parseLine).collect(Collectors.toList());
    }

    static PasswordEntry parseLine(String line) {
        Pattern linePattern = Pattern.compile("(?<" + MIN_OCCURRENCES + ">[0-9]+)-(?<" + MAX_OCCURRENCES +
                ">[0-9]+) (?<" + REQUIRED_LETTER + ">[a-z]): (?<" + PASSWORD + ">[a-zA-Z]+)");
        Matcher m = linePattern.matcher(line);

        if (m.find()) {
            int minOccurrences = Integer.parseInt(m.group(MIN_OCCURRENCES));
            int maxOccurrences = Integer.parseInt(m.group(MAX_OCCURRENCES));
            char requiredLetter = m.group(REQUIRED_LETTER).charAt(0);
            String password = m.group(PASSWORD);

            return new PasswordEntry(new PasswordPolicy(requiredLetter, minOccurrences, maxOccurrences), password);
        }
        else {
            throw new IllegalArgumentException("Passed string \"" + line + "\", but this is not a valid password entry");
        }
    }

    static boolean isEntryValid1(PasswordEntry entry) {
        long charOccurrence = entry.password().chars().filter(ch -> ch == entry.policy().requiredLetter()).count();
        return (charOccurrence >= entry.policy().minOccurrences() && charOccurrence <= entry.policy().maxOccurrences());
    }

    static boolean isEntryValid2(PasswordEntry entry) {
        return (entry.password().charAt(entry.policy().minOccurrences() - 1) == entry.policy().requiredLetter() ^
                entry.password().charAt(entry.policy().maxOccurrences() - 1) == entry.policy().requiredLetter());
    }

    @Override
    int getDay() {
        return 2;
    }

    @Override
    String solve1() {
        return Long.toString(_entries.stream().filter(Day2::isEntryValid1).count());
    }

    @Override
    String solve2() {
        return Long.toString(_entries.stream().filter(Day2::isEntryValid2).count());
    }
}

record PasswordPolicy(char requiredLetter, int minOccurrences, int maxOccurrences) {

}

record PasswordEntry(PasswordPolicy policy, String password) {

}