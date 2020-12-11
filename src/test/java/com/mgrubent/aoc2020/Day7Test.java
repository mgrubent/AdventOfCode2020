package com.mgrubent.aoc2020;

import org.junit.Assert;

public class Day7Test extends BasePuzzleTest {
    private static String EXAMPLE = """
            light red bags contain 1 bright white bag, 2 muted yellow bags.
            dark orange bags contain 3 bright white bags, 4 muted yellow bags.
            bright white bags contain 1 shiny gold bag.
            muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
            shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
            dark olive bags contain 3 faded blue bags, 4 dotted black bags.
            vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            faded blue bags contain no other bags.
            dotted black bags contain no other bags.""";

    @Override
    public void testExample1() {
        var day7 = new Day7(EXAMPLE);
        Assert.assertEquals("4", day7.solve1());
    }

    @Override
    public void testPart1() throws Exception {
        var day7 = new Day7(getStoredInput(7));
        Assert.assertEquals("144", day7.solve1());
    }

    @Override
    public void testExample2() {
        var day7 = new Day7(EXAMPLE);
        Assert.assertEquals("32", day7.solve2());

        final String example2 = """
                shiny gold bags contain 2 dark red bags.
                dark red bags contain 2 dark orange bags.
                dark orange bags contain 2 dark yellow bags.
                dark yellow bags contain 2 dark green bags.
                dark green bags contain 2 dark blue bags.
                dark blue bags contain 2 dark violet bags.
                dark violet bags contain no other bags.""";

        day7 = new Day7(example2);
        Assert.assertEquals("126", day7.solve2());
    }

    @Override
    public void testPart2() throws Exception {
        var day7 = new Day7(getStoredInput(7));
        Assert.assertEquals("5956", day7.solve2());
    }
}
