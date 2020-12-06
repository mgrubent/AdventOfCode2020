package com.mgrubent.aoc2020;

/**
 * Abstract class that represents the anticipated way we will deal with puzzles during Advent of Code
 */
abstract class Puzzle {
    private final String _input;

    /**
     * Constructor which accepts the puzzle input to be solved
     * @param input the puzzle input
     */
    Puzzle(String input) {
        _input = input;
    }

    /**
     * The day number of the puzzle.
     * @return the day number of the puzzle
     */
    abstract int getDay();

    /**
     * Returns the solution to part 1 of the puzzle.
     * @return the solution to part 1 of the puzzle
     */
    abstract String solve1();

    /**
     * Returns the solution to part 2 of the puzzle.
     * @return the solution to part 2 of the puzzle
     */
    abstract String solve2();
}
