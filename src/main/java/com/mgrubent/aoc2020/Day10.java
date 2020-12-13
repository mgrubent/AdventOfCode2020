package com.mgrubent.aoc2020;

import java.util.*;
import java.util.stream.Collectors;

public class Day10 extends Puzzle {
    private final List<JoltageAdapter> _joltageAdapters;
    private final Device _device;

    // We expect only to have the differences 1, 2, and 3, and so this could fit in an array of size 3.
    // That said, I really don't want to have to deal inline with the conversion from 1-indexed-counting
    // to 0-indexed counting.  Accordingly, just accept the wasted space in exchange for greater readability.
    private final int[] _joltageDifferences = new int[4];

    /**
     * Constructor which accepts the puzzle input to be solved
     *
     * @param input the puzzle input
     */
    Day10(String input) {
        super(input);

        // Collect all of the "joltages"
        _joltageAdapters = input.lines().map(Integer::parseInt).map(JoltageAdapter::new).collect(Collectors.toList());

        // Instantiate "my device" with the expected JoltageAdapter
        _device = new Device(_joltageAdapters);

        // Calculate difference distribution statistics when connecting the charging outlet near our seat (0 joltage)
        // to our device, using all of our JoltageAdapters in series
        List<Integer> sortedJoltageRatings = new LinkedList<>();

        // "Treat the charging outlet near your seat as having an effective joltage rating of 0."
        sortedJoltageRatings.add(0);

        // Add in all of the joltage ratings from our JoltageAdapters
        sortedJoltageRatings.addAll(_joltageAdapters.stream()
                .map(JoltageAdapter::getRating).sorted().collect(Collectors.toList()));

        // Finally, we must consider the joltage rating of our device, which by definition is 3 higher
        // than the highest joltage rating of any of our JoltageAdapters
        sortedJoltageRatings.add(_device.getJoltageAdapter().getRating());

        // Sum all the differences
        for (int i = 0, j = 1; j < sortedJoltageRatings.size(); i++, j++) {
            int joltageDifference = sortedJoltageRatings.get(j) - sortedJoltageRatings.get(i);
            _joltageDifferences[joltageDifference]++;
        }
    }

    int[] getJoltageDifferences() {
        return Arrays.copyOf(_joltageDifferences, 4);
    }

    Device getDevice() {
        return _device;
    }

    @Override
    int getDay() {
        return 10;
    }

    @Override
    String solve1() {
        // "What is the number of 1-jolt differences multiplied by the number of 3-jolt differences?"
        return Integer.toString(_joltageDifferences[1] * _joltageDifferences[3]);
    }

    @Override
    String solve2() {
        return null;
    }
}

class JoltageAdapter {
    private final int _rating;

    JoltageAdapter(int rating) {
        _rating = rating;
    }

    int getRating() {
        return _rating;
    }

    boolean canAcceptInputJoltage(int inputJoltage) {
        // "Any given adapter can take an input 1, 2, or 3 jolts lower than its rating
        // and still produce its rated output joltage."
        return inputJoltage < _rating && inputJoltage >= _rating - 3;
    }
}

class Device {
    private final JoltageAdapter _joltageAdapter;

    Device(JoltageAdapter joltageAdapter) {
        _joltageAdapter = joltageAdapter;
    }

    Device(List<JoltageAdapter> joltageAdapters) {
        Optional<JoltageAdapter> max = joltageAdapters.stream().max(Comparator.comparingInt(JoltageAdapter::getRating));
        // "In addition, your device has a built-in joltage adapter rated for 3 jolts higher
        // than the highest-rated adapter in your bag."
        _joltageAdapter = new JoltageAdapter(max.get().getRating() + 3);
    }

    JoltageAdapter getJoltageAdapter() {
        return _joltageAdapter;
    }
}