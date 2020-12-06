package com.mgrubent.aoc2020;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    private static final PuzzleInputFetcher PUZZLE_INPUT_FETCHER = new PuzzleInputFetcher2020();
    private static final List<Puzzle> PUZZLES = List.of(
            new Day1(PUZZLE_INPUT_FETCHER.getPuzzleInput(1))
            );
    public static void main(String[] args) throws FileNotFoundException {
        PUZZLES.forEach(puzzle -> {
            System.out.println("Solving day " + puzzle.getDay());
            System.out.println(puzzle.solve1());
            System.out.println(puzzle.solve2());
        });
    }
}
