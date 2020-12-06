package com.mgrubent.aoc2020;

import org.junit.Assert;
import org.junit.Test;

public class Day2Test extends BasePuzzleTest {

    private final String example = """
            1-3 a: abcde
            1-3 b: cdefg
            2-9 c: ccccccccc""";

    @Test
    public void testParseLine() {
        String line = "1-3 a: abcde";
        PasswordEntry entry = new PasswordEntry(new PasswordPolicy('a', 1, 3), "abcde");
        Assert.assertEquals(entry, Day2.parseLine(line));
    }

    @Test
    public void testExample1() {
        var day2 = new Day2(example);
        Assert.assertEquals("2", day2.solve1());
    }

    @Test
    public void testPart1() throws Exception {
        var day2 = new Day2(getStoredInput(2));
        Assert.assertEquals("416", day2.solve1());
    }

    @Test
    public void testExample2() {
        var day2 = new Day2(example);
        Assert.assertEquals("1", day2.solve2());
    }
}
