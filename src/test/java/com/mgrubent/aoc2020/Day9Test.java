package com.mgrubent.aoc2020;

import org.testng.Assert;

public class Day9Test extends BasePuzzleTest {
    private static final String EXAMPLE = """
            35
            20
            15
            25
            47
            40
            62
            55
            65
            95
            102
            117
            150
            182
            127
            219
            299
            277
            309
            576""";

    @Override
    public void testExample1() {
        var day9 = new Day9(EXAMPLE, 5);
        Assert.assertEquals(day9.solve1(), "127");
    }

    @Override
    public void testPart1() throws Exception {
        var day9 = new Day9(getStoredInput(9));
        Assert.assertEquals(day9.solve1(), "29221323");
    }

    @Override
    public void testExample2() {

    }

    @Override
    public void testPart2() throws Exception {

    }
}
