package com.mgrubent.aoc2020;

import kotlin.Pair;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day9 extends Puzzle {
    private final List<Integer> _intList;
    private final int _preamble;
    /**
     * Constructor which accepts the puzzle input to be solved
     *
     * @param input the puzzle input
     */
    Day9(String input) {
        super(input);

        // Parsing the puzzle input here is quite straightforward; every line is an int,
        // and those ints should form a list.
        _intList = input.lines().map(Integer::parseInt).collect(Collectors.toList());

        // We're told in the puzzle input that the preamble is 25, except in the example
        _preamble = 25;
    }

    /**
     * This alternate constructor allows for setting the preamble.
     *
     * This will be necessary in order to test the given length-5 preamble example.
     *
     * @param input the puzzle input
     * @param preamble the size of the window for performing two-sum.
     */
    Day9(String input, int preamble) {
        super(input);

        // Parsing the puzzle input here is quite straightforward; every line is an int,
        // and those ints should form a list.
        _intList = input.lines().map(Integer::parseInt).collect(Collectors.toList());
        _preamble = preamble;
    }

    /**
     * Find two numbers in the range [left, right) of the input numbers that could sum to target.
     *
     * @param target the sum that is sought
     * @param left the left-most index, inclusive, in which to search for matching numbers
     * @param right the right-most index, exclusive, in which to search for matching numbers
     * @return a Pair of integers that sum to target, if any exist.
     *         Optional.empty() otherwise.
     */
    private Optional<Pair<Integer, Integer>> twoSum(int target, int left, int right) {

        System.out.println(_intList.subList(left, right));

        // Grab a sublist of our input and sort it
        List<Integer> sortedIntegerList = _intList.subList(left, right).stream().sorted().collect(Collectors.toList());
        System.out.println(sortedIntegerList);

        int current;
        int other;

        // It would have been nice to reuse the code from Day1 for this search, but
        // enough was different in their applications that it didn't seem immediately
        // obvious how to do this without significantly reworking Day1.
        //
        // Accordingly, I've just reproduced most of the Day1 code here, with some tweaks
        for (int i = 0; i < sortedIntegerList.size(); i++) {
            current = sortedIntegerList.get(i);
            int remaining = target - current;
            int lo = i;
            int hi = sortedIntegerList.size();

            for (int mid = (lo + hi) / 2; lo <= hi && mid <= hi; mid = (lo + hi) / 2) {
                other = sortedIntegerList.get(mid);
                if (other == remaining) {
                    return Optional.of(new kotlin.Pair<>(current, other));
                } else if (other < remaining) {
                    lo = mid + 1;
                } else if (other > remaining) {
                    hi = mid - 1;
                }
            }
        }
        return Optional.empty();
    }


    @Override
    int getDay() {
        return 9;
    }

    @Override
    String solve1() {
        Optional<Pair<Integer, Integer>> twoSumAns;
        int target;

        // Yes, i and right are the same throughout this loop.
        // I'm OK wasting two operations because I think it makes it more readable.
        for (int i = _preamble, left = 0, right = _preamble; i < _intList.size(); i++, left++, right++) {
            target = _intList.get(i);
            twoSumAns = twoSum(target, left, right);
            if (twoSumAns.isEmpty()) {
                return Integer.toString(target);
            }
        }
        // We didn't find anything :/
        return null;
    }

    @Override
    String solve2() {
        return null;
    }
}

