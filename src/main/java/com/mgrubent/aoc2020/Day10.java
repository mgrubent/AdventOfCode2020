package com.mgrubent.aoc2020;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Day10 extends Puzzle {
    private final static Logger LOGGER = LoggerFactory.getLogger(Day10.class);

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

        // Calculate the sequential joltage differences
        calculateSequentialJoltageDifferences();
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

    private List<Integer> getChargerToDeviceJoltages() {
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

        return sortedJoltageRatings;
    }

    private void calculateSequentialJoltageDifferences() {
        List<Integer> sortedJoltageRatings = getChargerToDeviceJoltages();

        // Sum all the differences
        for (int i = 0, j = 1; j < sortedJoltageRatings.size(); i++, j++) {
            int joltageDifference = sortedJoltageRatings.get(j) - sortedJoltageRatings.get(i);
            _joltageDifferences[joltageDifference]++;
        }
    }

    private long countPathsToTarget(int target, int currentIndex, List<Integer> joltages, Map<Integer, Long> pathToTargetCount) {
        // Handle our having already calculated the number of paths to the device from this index
        if (pathToTargetCount.containsKey(currentIndex)) {
            return pathToTargetCount.get(currentIndex);
        }

        // Handle our currently being at the target
        if (joltages.get(currentIndex) == target) {

            // Memorize the number of paths we've found
            pathToTargetCount.put(currentIndex, 1L);
            return 1;
        }

        // Handle being past where the target could possibly be
        if (currentIndex == joltages.size()) {
            LOGGER.info("Path cannot possibly reach the target");

            // Memorize the number of paths we've found
            pathToTargetCount.put(currentIndex, 0L);
            return 0;
        }

        // If we're not currently at the target, "visit" this node
        int currentJoltage = joltages.get(currentIndex);

        // So far, we don't know of any paths from this index to the target
        long numPathsToTarget = 0;

        // Descend as many times as we can
        for (int nextCompatibleIndex = currentIndex + 1; // The next JoltageAdapter must be after this one
             // Further, the next JoltageAdapter's rating must be less than 4 higher than this one
             nextCompatibleIndex < joltages.size() && joltages.get(nextCompatibleIndex) - currentJoltage < 4;
             // Keep going as long as there are compatible JoltageAdapters
             nextCompatibleIndex++) {

            // The number of paths from this index to the target is the sum of the number of paths to the target
            // from each of this joltage adapter's compatible adapters (1, 2, or 3 ratings higher)
            numPathsToTarget += countPathsToTarget(target, nextCompatibleIndex, joltages, pathToTargetCount);
        }

        // Memorize the number of paths we've found from this index, so that subsequent calls with this currentIndex
        // are O(1) rather than combinatorial
        pathToTargetCount.put(currentIndex, numPathsToTarget);

        return numPathsToTarget;
    }

    private long countPathsToDevice() {
        List<Integer> sortedJoltageRatings = getChargerToDeviceJoltages();

        // Keep track of how many paths from this index are there to the target
        Map<Integer, Long> pathsFromIndex = new HashMap<>();

        return countPathsToTarget(_device.getJoltageAdapter().getRating(), 0, sortedJoltageRatings, pathsFromIndex);
    }

    @Override
    String solve1() {
        // "What is the number of 1-jolt differences multiplied by the number of 3-jolt differences?"
        return Integer.toString(_joltageDifferences[1] * _joltageDifferences[3]);
    }

    @Override
    String solve2() {
        return Long.toString(countPathsToDevice());
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
}

class Device {
    private final JoltageAdapter _joltageAdapter;

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