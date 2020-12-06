package com.mgrubent.aoc2020;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day1 extends Puzzle {
    private static final int SUM = 2020;
    private final List<Integer> _sortedIntegerList;

    Day1(String input) {
        super(input);
        _sortedIntegerList = input.lines().map(Integer::valueOf).sorted().collect(Collectors.toList());
    }

    @Override
    public int getDay() {
        return 1;
    }

    private Stack<Integer> twoSum(int target, int left, int right) {
        Stack<Integer> ans = new Stack<>();

        int current = 0;
        int other = 0;

        outer:
        for (int i = left; i < right; i++) {
            current = _sortedIntegerList.get(i);
            int remaining = target - current;
            int lo = i + 1;
            int hi = right;
            int mid;

            while (lo <= hi) {
                mid = (lo + hi) / 2;
                other = _sortedIntegerList.get(mid);
                if (other == remaining) {
                    ans.push(current);
                    ans.push(other);
                    break outer;
                } else if (other < remaining) {
                    lo = mid + 1;
                } else if (other > remaining) {
                    hi = mid - 1;
                }
            }
        }
        return ans;
    }

    private Stack<Integer> twoSum(int target) {
        return twoSum(target, 0, _sortedIntegerList.size() - 1);
    }

    @Override
    public String solve1() {
        Stack<Integer> twoTerms = twoSum(SUM);

        if (!twoTerms.empty()) {
            return Integer.toString(twoTerms.pop() * twoTerms.pop());
        }

        return null;
    }

    private Stack<Integer> threeSum(int target) {
        Stack<Integer> twoSum;
        for (int i = 0; i < _sortedIntegerList.size(); i++) {
            int current = _sortedIntegerList.get(i);
            twoSum = twoSum(SUM - current, i + 1, _sortedIntegerList.size() - 1);
            if (!twoSum.empty()) {
                twoSum.push(current);
                return twoSum;
            }
        }
        return new Stack<>();
    }

    @Override
    public String solve2() {
        Stack<Integer> threeTerms = threeSum(SUM);

        if (!threeTerms.empty()) {
            return Integer.toString(threeTerms.pop() * threeTerms.pop() * threeTerms.pop());
        }

        return null;
    }
}
