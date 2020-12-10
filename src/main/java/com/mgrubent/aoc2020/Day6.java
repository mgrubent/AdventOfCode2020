package com.mgrubent.aoc2020;

import java.util.*;
import java.util.stream.Collectors;

public class Day6 extends Puzzle {
    private List<List<Set<Character>>> _groupsPeopleAnswers = new LinkedList<>();
    /**
     * Constructor which accepts the puzzle input to be solved
     *
     * @param input the puzzle input
     */
    Day6(String input) {
        super(input);

        List<Set<Character>> tmpGroup = new LinkedList<>();

        for (String line : input.split("\\n")) {
            if (line.isEmpty()) {
                // Defensive copy to avoid mutation of already-added groups
                _groupsPeopleAnswers.add(new LinkedList<>(tmpGroup));
                tmpGroup.clear();
            } else {
                // https://stackoverflow.com/a/31641308/11280049
                Set<Character> answers = line.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
                tmpGroup.add(answers);
            }
        }
        _groupsPeopleAnswers.add(tmpGroup);
    }



    @Override
    int getDay() {
        return 6;
    }

    @Override
    String solve1() {
        int sum = 0;
        for (List<Set<Character>> group : _groupsPeopleAnswers) {
            // Stream all the individual people answers we have, then merge them into a single set
            Set<Character> combinedAnswers = group.stream()
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
            sum += combinedAnswers.size();
        }
        return Integer.toString(sum);
    }

    @Override
    String solve2() {
        return null;
    }

}
