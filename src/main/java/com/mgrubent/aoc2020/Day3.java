package com.mgrubent.aoc2020;

import java.util.List;
import java.util.stream.Collectors;

public class Day3 extends Puzzle {
    private final Scroll _scroll;
    private final List<Slope> _slopes;

    /**
     * Constructor which accepts the puzzle input to be solved
     *
     * @param input the puzzle input
     */
    Day3(String input) {
        super(input);
        _scroll = new Scroll(input.lines().collect(Collectors.toList()));
        _slopes = List.of(
                new Slope(1, 1),
                new Slope(3, 1),
                new Slope(5, 1),
                new Slope(7, 1),
                new Slope(1, 2));
    }

    @Override
    int getDay() {
        return 3;
    }

    // Use long to make it easier to take the product of several counted trees
    private long countTrees(Slope slope) {
        int treeCount = 0;
        for (int y = 0, x = 0; y < _scroll.length(); y += slope.down(), x += slope.right()) {
            if (_scroll.isTree(x, y)) treeCount++;
        }
        return treeCount;
    }

    @Override
    String solve1() {
        Slope slope = _slopes.get(1);
        return Long.toString(countTrees(slope));
    }

    @Override
    String solve2() {
        long product = _slopes.stream().map(this::countTrees).reduce(1L, (x, y) -> x * y);
        return Long.toString(product);
    }
}

/**
 * This class is intended to represent the infinite scroll-to-the-right given an initial "sprite"
 * input.
 * <p>
 * That is, (provided you do not overflow), you can ask about any X coordinate 0 or greater.
 * <p>
 * The Y coordinate, however, is still bound to the height of the given sprite.
 * <p>
 * The (X, Y) coordinate convention for a Scroll is as follows:
 * <p>
 * (0,0)                (4, 0)
 * .      .      .      .
 * .
 * .
 * .
 * (0, 4)
 */
class Scroll {
    private final List<String> _sprite;
    private final int _width;

    Scroll(List<String> sprite) {
        _sprite = sprite;
        _width = _sprite.get(0).length();
    }

    boolean isTree(int x, int y) {
        // Keep from falling off the right edge
        x = x % _width;

        // From the problem prompt, trees are represented as "#".
        return _sprite.get(y).charAt(x) == '#';
    }

    int length() {
        return _sprite.size();
    }
}

record Slope(int right, int down) {

}