package com.mgrubent.aoc2020;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 extends Puzzle {
    // Define some Patterns to help in parsing the String input
    private static final Pattern lineRe =
            Pattern.compile("(?<outerBag>[a-z]+ [a-z]+) bags contain (?<innerBagSection>[a-z0-9, ]+).");
    private static final Pattern innerBagRe =
            Pattern.compile("(?<quantity>[0-9]+) (?<bagName>[a-z]+ [a-z]+) bags?");

    // Our fully-parsed input
    private final Map<Bag, List<BagQuantity>> _bagMap = new HashMap<>();

    // Define some data structures to cache intermediate results,
    // preventing the performance hit of having to recalculate the same thing numerous times.
    private final Map<Bag, Long> _bagsInside = new HashMap<>();
    private final Map<BagPair, Boolean> _canContainCache = new HashMap<>();

    /**
     * Constructor which accepts the puzzle input to be solved
     *
     * @param input the puzzle input
     */
    Day7(String input) {
        super(input);
        input.lines()
                .map(Day7::parseLine)
                .forEach(bagRule -> _bagMap.put(bagRule.outermost(), bagRule.innerBags()));
    }

    /**
     * Given a bag, return how many bags it <em>must</em> contain.
     *
     * @param outer a {@link Bag} that may or may not contain any other bags
     * @return how many bags the given bag _must_ contain.
     */
    private long countBagsInside(Bag outer) {
        // Handle having already answered this question
        if (_bagsInside.containsKey(outer)) {
            return _bagsInside.get(outer);
        }

        // Perform recursive BFS and update _bagsInside
        List<BagQuantity> insideBags = _bagMap.get(outer);

        long bagCount = 0L;

        for (BagQuantity bagQuantity : insideBags) {
            Bag current = bagQuantity.bag();
            // countBagsInside could return 0, but we still want to count this bag as 1 * its quantity.
            bagCount += (1 + countBagsInside(current)) * bagQuantity.quantity();
        }

        // Record that we've already made this calculation
        _bagsInside.put(outer, bagCount);

        return bagCount;
    }

    /**
     * Can outer possibly contain inner, either directly or indirectly?
     *
     * @param outer the {@link Bag} that must be able to contain inner
     * @param inner the {@link Bag} that must be inside outer, either directly or indirectly
     * @return True if outer can possible contain inner, either directly or indirectly.
     *         False otherwise.
     */
    private boolean bagCanContain(Bag outer, Bag inner) {
        // Handle having already answered this question
        BagPair pair = new BagPair(outer, inner);
        if (_canContainCache.containsKey(pair)) {
            return _canContainCache.get(pair);
        }

        // Handle outer being inner
        if (outer.equals(inner)) {
            _canContainCache.put(pair, false);
            return false;
        }

        // Implement BFS and update _canContainCache
        Queue<Bag> q = new LinkedList<>();
        q.add(outer);

        Bag cur;

        while (!q.isEmpty()) {
            // pop the head of the queue, note that we can reach it from outer,
            // and see if it's inner.
            cur = q.remove();

            if (cur.equals(outer)) {
                // It's _not_ the case that a bag can contain itself,
                _canContainCache.put(new BagPair(outer, cur), false);
            } else {
                // Otherwise, record that we _know_ that outer is able to contain cur
                _canContainCache.put(new BagPair(outer, cur), true);
            }

            // If we've reached inner from outer, we affirmatively know that we _can_ reach it from outer.
            if (cur.equals(inner)) {
                return true;
            }

            // add all of the current bag's directly-reachable children to this queue
            _bagMap.get(cur).forEach(bagQuantity -> q.add(bagQuantity.bag()));
        }

        // If we're here, we know that we can't reach inner from outer, so note that.
        _canContainCache.put(new BagPair(outer, inner), false);
        return false;
    }

    /**
     * Helper method to return a {@link BagRule} from a given line.
     *
     * @param line some line of puzzle input
     * @return the BagRule that this line represents
     */
    private static BagRule parseLine(String line) {
        Matcher m = lineRe.matcher(line);
        if (m.find()) {
            Bag outer = new Bag(m.group("outerBag"));
            List<BagQuantity> innerBags = parseInnerBags(m.group("innerBagSection"));

            // At this point, we could somewhat pre-populate the bag cache if we think we'll benefit from it.
            // For now, I'll skip this unless it seems like it's a good use of time.

            return new BagRule(outer, innerBags);
        } else {
            throw new RuntimeException("Could not parse \"" + line + "\"; is it a valid line?");
        }
    }

    /**
     * Helper method to return a List of {@link BagQuantity} from the given String
     *
     * @param innerBagSection the innerBagSection of {@link Day7#lineRe}
     * @return a (possibly-empty) List of {@link BagQuantity} that the given String represents
     */
    private static List<BagQuantity> parseInnerBags(String innerBagSection) {
        // Handle "no other bags" case
        if (innerBagSection.equals("no other bags")) {
            return List.of();
        }

        List<BagQuantity> bagQuantities = new LinkedList<>();

        for (String innerBagString : innerBagSection.split(", ")) {
            Matcher m = innerBagRe.matcher(innerBagString);
            if (m.find()) {
                int quantity = Integer.parseInt(m.group("quantity"));
                String bagName = m.group("bagName");
                bagQuantities.add(new BagQuantity(new Bag(bagName), quantity));
            } else {
                throw new RuntimeException("Could not parse \"" + innerBagSection + "\"; is it a valid bag section?");
            }
        }

        return bagQuantities;
    }

    /**
     * Helper method to count the number of outer bags in {@link Day7#_bagMap} that could possibly contain
     * the given innerBag.
     *
     * @param innerBag any {@link Bag}
     * @return the number of outer bags in {@link Day7#_bagMap} that could possibly contain innerBag
     */
    private long numberOfBagsContaining(Bag innerBag) {
        return _bagMap.keySet().stream().filter(outerBag -> this.bagCanContain(outerBag, innerBag)).count();
    }

    @Override
    int getDay() {
        return 7;
    }

    @Override
    String solve1() {
        // Arbitrarily, the problem authors have chosen "shiny gold" bag as the target inner bag
        Bag shinyGold = new Bag("shiny gold");
        return Long.toString(numberOfBagsContaining(shinyGold));
    }

    @Override
    String solve2() {
        // Arbitrarily, the problem authors have chosen "shiny gold" bag as the target _outer_ bag
        Bag shinyGold = new Bag("shiny gold");
        return Long.toString(countBagsInside(shinyGold));
    }
}

record Bag(String name) {

}

record BagQuantity(Bag bag, int quantity) {

}

record BagRule(Bag outermost, List<BagQuantity> innerBags) {

}

record BagPair(Bag outer, Bag inner) {

}