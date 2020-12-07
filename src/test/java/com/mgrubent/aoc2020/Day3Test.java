package com.mgrubent.aoc2020;

import org.testng.Assert;

public class Day3Test extends BasePuzzleTest {
    private static final String EXAMPLE = """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#""";

    @Override
    public void testExample1() {
        var day3 = new Day3(EXAMPLE);
        Assert.assertEquals("7", day3.solve1());
    }

    @Override
    public void testPart1() {

    }

    @Override
    public void testExample2() {

    }

    @Override
    public void testPart2() {

    }
}
