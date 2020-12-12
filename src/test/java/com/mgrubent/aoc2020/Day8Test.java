package com.mgrubent.aoc2020;

import org.junit.Assert;

public class Day8Test extends BasePuzzleTest {
    private static final String EXAMPLE = """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            jmp -4
            acc +6""";
    @Override
    public void testExample1() {
        var day8 = new Day8(EXAMPLE);
        Assert.assertEquals("5", day8.solve1());
    }

    @Override
    public void testPart1() throws Exception {
        var day8 = new Day8(getStoredInput(8));
        Assert.assertEquals("1087", day8.solve1());
    }

    @Override
    public void testExample2() {
        Assert.fail();
    }

    @Override
    public void testPart2() throws Exception {
        Assert.fail();
    }
}
