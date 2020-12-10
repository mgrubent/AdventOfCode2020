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

    }

    @Override
    public void testExample2() {

    }

    @Override
    public void testPart2() throws Exception {

    }
}
