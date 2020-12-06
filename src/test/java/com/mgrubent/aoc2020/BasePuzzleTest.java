package com.mgrubent.aoc2020;

import com.google.common.io.Resources;

import java.nio.file.Files;
import java.nio.file.Path;

public class BasePuzzleTest {

    public String getStoredInput(int day) throws Exception {
        var puzzleInputResource = Resources.getResource("puzzle/" + day);
        return Files.readString(Path.of(puzzleInputResource.toURI()));
    }
}
