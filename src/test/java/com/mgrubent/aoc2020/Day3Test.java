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
        Assert.assertEquals(day3.solve1(), "7");
    }

    @Override
    public void testPart1() throws Exception {
        var day3 = new Day3(getStoredInput(3));
        Assert.assertEquals(day3.solve1(), "247");
    }

    @Override
    public void testExample2() {
        var day3 = new Day3(EXAMPLE);
        Assert.assertEquals(day3.solve2(), "336");
    }

    @Override
    public void testPart2() throws Exception {
        var day3 = new Day3(getStoredInput(3));
        Assert.assertEquals(day3.solve2(), "2983070376");
    }
}
