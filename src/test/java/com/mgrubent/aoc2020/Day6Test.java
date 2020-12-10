package com.mgrubent.aoc2020;

import org.testng.Assert;

public class Day6Test extends BasePuzzleTest {
    private static final String EXAMPLE = """
            abc
                        
            a
            b
            c
                        
            ab
            ac
                        
            a
            a
            a
            a
                        
            b""";

    @Override
    public void testExample1() {
        var day6 = new Day6(EXAMPLE);
        Assert.assertEquals(day6.solve1(), "11");
    }

    @Override
    public void testPart1() throws Exception {
        var day6 = new Day6(getStoredInput(6));
        Assert.assertEquals(day6.solve1(), "6387");
    }

    @Override
    public void testExample2() {
        var day6 = new Day6(EXAMPLE);
        Assert.assertEquals(day6.solve2(), "6");
    }

    @Override
    public void testPart2() throws Exception {
        var day6 = new Day6(getStoredInput(6));
        Assert.assertEquals(day6.solve2(), "3039");
    }
}
