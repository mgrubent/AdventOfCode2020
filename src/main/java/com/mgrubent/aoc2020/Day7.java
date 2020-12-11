package com.mgrubent.aoc2020;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 extends Puzzle {
    private static final Pattern lineRe =
            Pattern.compile("(?<outerBag>[a-z]+ [a-z]+) bags contain (?<innerBagSection>[a-z0-9, ]+).");
    private static final Pattern innerBagRe =
            Pattern.compile("(?<quantity>[0-9]+) (?<bagName>[a-z]+ [a-z]+) bags?");

    private final Map<Bag, List<BagQuantity>> _bagMap = new HashMap<>();
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
        return null;
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