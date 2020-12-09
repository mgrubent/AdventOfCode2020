package com.mgrubent.aoc2020;

import org.junit.Assert;

public class Day4Test extends BasePuzzleTest {
    private static final String EXAMPLE = """
            ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
            byr:1937 iyr:2017 cid:147 hgt:183cm

            iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
            hcl:#cfa07d byr:1929

            hcl:#ae17e1 iyr:2013
            eyr:2024
            ecl:brn pid:760753108 byr:1931
            hgt:179cm

            hcl:#cfa07d eyr:2025 pid:166559648
            iyr:2011 ecl:brn hgt:59in""";
    @Override
    public void testExample1() {
        var day4 = new Day4(EXAMPLE);
        Assert.assertEquals("2", day4.solve1());
    }

    @Override
    public void testPart1() throws Exception {
        Assert.assertFalse(true);
    }

    @Override
    public void testExample2() {
        Assert.assertFalse(true);
    }

    @Override
    public void testPart2() throws Exception {
        Assert.assertFalse(true);
    }
}
