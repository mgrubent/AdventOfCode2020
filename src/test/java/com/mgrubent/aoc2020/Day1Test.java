package com.mgrubent.aoc2020;

import org.junit.Assert;
import org.junit.Test;

public class Day1Test extends BasePuzzleTest {
    final String example = """
                1721
                979
                366
                299
                675
                1456""";

    @Test
    public void testExample1() {

        Day1 day1 = new Day1(example);
        Assert.assertEquals("514579", day1.solve1());
    }

    @Test
    public void testPart1() throws Exception {
        Day1 day1 = new Day1(getStoredInput(1));
        Assert.assertEquals("290784",day1.solve1());
}

    @Test
    public void testExample2() {
        Day1 day1 = new Day1(example);
        Assert.assertEquals("241861950", day1.solve2());
    }

    @Test
    public void testPart2() throws Exception {
        Day1 day1 = new Day1(getStoredInput(1));
        Assert.assertEquals("177337980", day1.solve2());
    }
}
