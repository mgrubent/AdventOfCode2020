package com.mgrubent.aoc2020;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day2 extends Puzzle {
    private final static String FIRST_NUMBER = "firstNumber";
    private final static String SECOND_NUMBER = "secondNumber";
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
        Pattern linePattern = Pattern.compile("(?<" + FIRST_NUMBER + ">[0-9]+)-(?<" + SECOND_NUMBER +
                ">[0-9]+) (?<" + REQUIRED_LETTER + ">[a-z]): (?<" + PASSWORD + ">[a-zA-Z]+)");
        Matcher m = linePattern.matcher(line);

        if (m.find()) {
            int firstNumber = Integer.parseInt(m.group(FIRST_NUMBER));
            int secondNumber = Integer.parseInt(m.group(SECOND_NUMBER));
            char requiredLetter = m.group(REQUIRED_LETTER).charAt(0);
            String password = m.group(PASSWORD);

            return new PasswordEntry(new PasswordPolicy(requiredLetter, firstNumber, secondNumber), password);
        }
        else {
            throw new IllegalArgumentException("Passed string \"" + line + "\", but this is not a valid password entry");
        }
    }

    static boolean isEntryValid1(PasswordEntry entry) {
        // We want to ensure that the required letter occurs at least firstNumber times, and at most secondNumber times
        long charOccurrence = entry.password().chars().filter(ch -> ch == entry.policy().requiredLetter()).count();
        return (charOccurrence >= entry.policy().firstNumber() && charOccurrence <= entry.policy().secondNumber());
    }

    static boolean isEntryValid2(PasswordEntry entry) {
        // We want to ensure that exactly one of the 1-indexed positions contains the required letter
        return (entry.password().charAt(entry.policy().firstNumber() - 1) == entry.policy().requiredLetter() ^
                entry.password().charAt(entry.policy().secondNumber() - 1) == entry.policy().requiredLetter());
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

record PasswordPolicy(char requiredLetter, int firstNumber, int secondNumber) {

}

record PasswordEntry(PasswordPolicy policy, String password) {

}