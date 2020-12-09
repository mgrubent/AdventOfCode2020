package com.mgrubent.aoc2020;

import org.testng.Assert;

public class Day5Test extends BasePuzzleTest {
    @Override
    public void testExample1() {
        Assert.assertEquals(BoardingPass.fromBinarySpacePartitioning("FBFBBFFRLR"),
                new BoardingPass(44, 5, 357));

        Assert.assertEquals(BoardingPass.fromBinarySpacePartitioning("BFFFBBFRRR"),
                new BoardingPass(70, 7, 567));
        Assert.assertEquals(BoardingPass.fromBinarySpacePartitioning("FFFBBBFRRR"),
                new BoardingPass(14, 7, 119));
        Assert.assertEquals(BoardingPass.fromBinarySpacePartitioning("BBFFBBFRLL"),
                new BoardingPass(102, 4, 820));
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
