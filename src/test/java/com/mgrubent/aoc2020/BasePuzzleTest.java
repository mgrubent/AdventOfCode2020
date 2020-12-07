package com.mgrubent.aoc2020;

import com.google.common.io.Resources;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;

public abstract class BasePuzzleTest {

    public String getStoredInput(int day) throws Exception {
        var puzzleInputResource = Resources.getResource("puzzle/" + day);
        return Files.readString(Path.of(puzzleInputResource.toURI()));
    }

    @Test
    abstract public void testExample1();

    @Test
    abstract public void testPart1() throws Exception;

    @Test
    abstract public void testExample2();

    @Test
    abstract public void testPart2() throws Exception;
}
