package com.mgrubent.aoc2020;

import org.junit.Test;
import org.testng.Assert;

import java.util.List;
import java.util.Optional;

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

    @Test
    public void testListExtraction() {
        var day9 = new Day9(EXAMPLE, 5);
        List<Long> expected = List.of(15L, 25L, 47L, 40L);
        Optional<List<Long>> actual = day9.setSum(14);
        if (actual.isPresent()) {
            Assert.assertEquals(actual.get(), expected);
        } else {
            Assert.fail();
        }
    }

    @Override
    public void testExample2() {
        var day9 = new Day9(EXAMPLE, 5);
        Assert.assertEquals(day9.solve2(), "62");
    }

    @Override
    public void testPart2() throws Exception {
        var day9 = new Day9(getStoredInput(9));
        Assert.assertEquals(day9.solve2(), "4389369");
    }
}
