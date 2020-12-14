package com.mgrubent.aoc2020;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class Day10Test extends BasePuzzleTest {
    private static final String SMALL_EXAMPLE = """
            3
            9
            6""";

    private static final String EXAMPLE = """
            16
            10
            15
            5
            1
            11
            7
            19
            6
            12
            4""";

    private static final String LARGER_EXAMPLE = """
            28
            33
            18
            42
            31
            14
            46
            20
            48
            47
            24
            23
            49
            45
            19
            38
            39
            11
            1
            32
            25
            35
            8
            17
            7
            9
            4
            2
            34
            10
            3""";

    @Test
    public void testDevice() {
        List<JoltageAdapter> joltageAdapters =
                List.of(new JoltageAdapter(3), new JoltageAdapter(9), new JoltageAdapter(6));

        JoltageAdapter expected = new JoltageAdapter(12);
        JoltageAdapter actual = new Device(joltageAdapters).getJoltageAdapter();

        Assert.assertEquals(expected.getRating(), actual.getRating());
    }

    @Override
    public void testExample1() {
        var day10 = new Day10(SMALL_EXAMPLE);
        Assert.assertEquals(12, day10.getDevice().getJoltageAdapter().getRating());

        day10 = new Day10(EXAMPLE);
        Assert.assertEquals(22, day10.getDevice().getJoltageAdapter().getRating());

        // "In this example, when using every adapter, there are 7 differences of 1 jolt and 5 differences of 3 jolts"
        int[] expected = new int[] {0, 7, 0, 5};
        int[] actual = day10.getJoltageDifferences();
        Assert.assertArrayEquals(expected, actual);

        day10 = new Day10(LARGER_EXAMPLE);
        expected = new int[] {0, 22, 0, 10};
        actual = day10.getJoltageDifferences();
        Assert.assertArrayEquals(expected, actual);
    }

    @Override
    public void testPart1() throws Exception {
        var day10 = new Day10(getStoredInput(10));
        Assert.assertEquals("2040", day10.solve1());
    }

    @Override
    public void testExample2() {
        var day10 = new Day10(EXAMPLE);
        Assert.assertEquals("8", day10.solve2());
    }

    @Override
    public void testPart2() throws Exception {
        Assert.fail();
    }
}
