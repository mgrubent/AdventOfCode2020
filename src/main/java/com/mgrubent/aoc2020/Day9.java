package com.mgrubent.aoc2020;

import kotlin.Pair;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day9 extends Puzzle {
    private final List<Long> _longList;
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
        _longList = input.lines().map(Long::parseUnsignedLong).collect(Collectors.toList());

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
        _longList = input.lines().map(Long::parseUnsignedLong).collect(Collectors.toList());
        _preamble = preamble;
    }

    /**
     * Find two numbers in the range [left, right) of the input numbers that could sum to target.
     *
     * @param target the sum that is sought
     * @param left the left-most index, inclusive, in which to search for matching numbers
     * @param right the right-most index, exclusive, in which to search for matching numbers
     * @return a Pair of longs that sum to target, if any exist.
     *         Optional.empty() otherwise.
     */
    private Optional<Pair<Long, Long>> twoSum(long target, int left, int right) {
        // Grab a sublist of our input and sort it
        List<Long> sortedIntegerList = _longList.subList(left, right).stream().sorted().collect(Collectors.toList());

        long current;
        long other;

        // It would have been nice to reuse the code from Day1 for this search, but
        // enough was different in their applications that it didn't seem immediately
        // obvious how to do this without significantly reworking Day1.
        //
        // Accordingly, I've just reproduced most of the Day1 code here, with some tweaks
        for (int i = 0; i < sortedIntegerList.size(); i++) {
            current = sortedIntegerList.get(i);
            long remaining = target - current;
            int lo = i;
            int hi = sortedIntegerList.size();

            for (int mid = (lo + hi) / 2; lo <= hi && mid < sortedIntegerList.size(); mid = (lo + hi) / 2) {
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

    /**
     * Given the index of a number in the given _intList, return the list of at least two contiguous
     * numbers before the given index that sum to that number.
     *
     * @param index some index in the range [0, _intList.size())
     * @return A List of at least size two, if any two or more contiguous numbers sum to the number at the index.
     *         Optional.empty() otherwise
     */
    Optional<List<Long>> setSum(int index) {
        long target = _longList.get(index);

        long current;
        for (int length = 2; length < index; length++) {
            for (int i = 0, j = length; j <= index; i++, j++) {
                List<Long> subList = _longList.subList(i, j);
                current = subList.stream().reduce(0L, Long::sum);
                if (current == target){
                    return Optional.of(subList);
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
        Optional<Pair<Long, Long>> twoSumAns;
        long target;

        // Yes, i and right are the same throughout this loop.
        // I'm OK wasting two operations because I think it makes it more readable.
        for (int i = _preamble, left = 0, right = _preamble; i < _longList.size(); i++, left++, right++) {
            target = _longList.get(i);
            twoSumAns = twoSum(target, left, right);
            if (twoSumAns.isEmpty()) {
                return Long.toString(target);
            }
        }
        // We didn't find anything :/
        return null;
    }

    @Override
    String solve2() {
        // First, we need to get the index of the answer to the first part
        int index = _longList.indexOf(Long.parseLong(solve1()));

        // Next, we know that the run of contiguous numbers, if they exist, must be before index
        Optional<List<Long>> subList = setSum(index);

        if (subList.isPresent()) {
            List<Long> actualList = subList.get();

            // We're asked to sum the smallest and largest numbers, so sort the sublist and get the first and last
            actualList = actualList.stream().sorted().collect(Collectors.toList());
            long sum = actualList.get(0) + actualList.get(actualList.size() - 1);
            return Long.toString(sum);
        }
        // We didn't find anything :/
        return null;
    }
}

