package com.mgrubent.aoc2020;

import java.util.List;

public class Main {
    private static final PuzzleInputFetcher PUZZLE_INPUT_FETCHER = new PuzzleInputFetcher2020();
    private static final List<Puzzle> PUZZLES = List.of(
            new Day1(PUZZLE_INPUT_FETCHER.getPuzzleInput(1)),
            new Day2(PUZZLE_INPUT_FETCHER.getPuzzleInput(2)),
            new Day3(PUZZLE_INPUT_FETCHER.getPuzzleInput(3)),
            new Day4(PUZZLE_INPUT_FETCHER.getPuzzleInput(4)),
            new Day5(PUZZLE_INPUT_FETCHER.getPuzzleInput(5))
            );
    public static void main(String[] args) {
        PUZZLES.forEach(puzzle -> {
            System.out.println("Solving day " + puzzle.getDay());
            System.out.println(puzzle.solve1());
            System.out.println(puzzle.solve2());
        });
    }
}
