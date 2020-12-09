package com.mgrubent.aoc2020;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 extends Puzzle {
    private final List<BoardingPass> _boardingPasses;

    /**
     * Constructor which accepts the puzzle input to be solved
     *
     * @param input the puzzle input
     */
    Day5(String input) {
        super(input);
        _boardingPasses = input.lines().map(BoardingPass::fromBinarySpacePartitioning).collect(Collectors.toList());
    }

    @Override
    int getDay() {
        return 5;
    }

    @Override
    String solve1() {
        return Integer.toString(_boardingPasses.stream().map(BoardingPass::seatId).max(Integer::compareTo).get());
    }

    @Override
    String solve2() {
        Map<Integer, BoardingPass> bySeatId = new HashMap<>();
        for (BoardingPass boardingPass : _boardingPasses) {
            bySeatId.put(boardingPass.seatId(), boardingPass);
        }

        // Check for missing boarding passes
        // Start on the second row, left-most column (inclusive)
        // Stop on the last row, left-most column (exclusive)
        List<Integer> mine = new LinkedList<>();
        IntStream.range(8, 127 * 8)
                .filter(seatId -> !bySeatId.containsKey(seatId))
                .filter(possibleSeatId -> bySeatId.containsKey(possibleSeatId - 1))
                .filter(possibleSeatId -> bySeatId.containsKey(possibleSeatId + 1))
                .forEach(mine::add);


        if (mine.size() == 1) {
            return Integer.toString(mine.get(0));
        }
        return null;
    }
}

record BoardingPass(int row, int column, int seatId) {
    private static final Pattern spacePartitionRe = Pattern.compile("(?<rowString>[FB]{7})(?<columnString>[LR]{3})");

    private static int _parseBitString(String bitString, char high) {
        int result = 0;


        for (int i = bitString.length() - 1, val = 1; i > -1; i--, val *= 2) {
            if (bitString.charAt(i) == high) result += val;
        }
        return result;
    }

    private static int parseRowString(String rowString) {
        return _parseBitString(rowString, 'B');
    }

    private static int parseColumnString(String columnString) {
        return _parseBitString(columnString, 'R');
    }

    private static int seatId(int row, int column) {
        return row * 8 + column;
    }

    static BoardingPass fromBinarySpacePartitioning(String input) {
        Matcher m = spacePartitionRe.matcher(input);
        if (m.find()) {
            int row = parseRowString(m.group("rowString"));
            int column = parseColumnString(m.group("columnString"));

            return new BoardingPass(row, column, seatId(row, column));
        }
        throw new RuntimeException(":shrug:");
    }

    public static void main(String[] args) {
        System.out.println(BoardingPass._parseBitString("FBFBBFF", 'B'));
        System.out.println(BoardingPass._parseBitString("RLR", 'R'));
    }
}