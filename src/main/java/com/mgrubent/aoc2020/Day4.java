package com.mgrubent.aoc2020;

import java.util.*;

public class Day4 extends Puzzle {
    private static final String BIRTH_YEAR = "byr";
    private static final String ISSUE_YEAR = "iyr";
    private static final String EXPIRATION_YEAR = "eyr";
    private static final String HEIGHT = "hgt";
    private static final String HAIR_COLOR = "hcl";
    private static final String EYE_COLOR = "ecl";
    private static final String PASSPORT_ID = "pid";
    private static final String COUNTRY_ID = "cid";

    private final List<Passport> _passports = new LinkedList<>();

    /**
     * Constructor which accepts the puzzle input to be solved
     *
     * @param input the puzzle input
     */
    Day4(String input) {
        super(input);

        // Hackily parse and tokenize the input
        Map<String, String> tmpMap = new HashMap<>();
        String[] keyAndValue;
        for (String line : input.split("\\n")) {
            if (line.isEmpty()) {
                _passports.add(Passport.fromMap(tmpMap));
                tmpMap.clear();
            } else {
                for (String token : line.split(" ")) {
                    keyAndValue = token.split(":");
                    tmpMap.put(keyAndValue[0], keyAndValue[1]);
                }
            }
        }
        _passports.add(Passport.fromMap(tmpMap));
        tmpMap.clear();
    }

    @Override
    int getDay() {
        return 4;
    }

    @Override
    String solve1() {
        return Long.toString(_passports.stream().filter(Passport::isValid).count());
    }

    @Override
    String solve2() {
        return null;
    }
}

class Passport {
    private static final Set<String> REQUIRED_KEYS = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
    private static final Set<String> VALID_KEYS = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid");


    static Passport fromMap(Map<String, String> map) {
        Passport passport = new Passport();
        map.forEach(passport::put);
        return passport;
    }

    private Map<String, String> _properties = new HashMap<>();

    Passport put(String key, String value) {
        _properties.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "_properties=" + _properties +
                '}';
    }

    boolean isValid() {
        boolean allKeysValid = VALID_KEYS.containsAll(_properties.keySet());
        boolean hasAllRequiredKeys = _properties.keySet().containsAll(REQUIRED_KEYS);
        return hasAllRequiredKeys && allKeysValid;
    }

}