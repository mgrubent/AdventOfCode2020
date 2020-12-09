package com.mgrubent.aoc2020;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 extends Puzzle {
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
        return Long.toString(_passports.stream().filter(Passport::isValid1).count());
    }

    @Override
    String solve2() {
        return Long.toString(_passports.stream().filter(Passport::isValid2).count());
    }
}

class Passport {
    private static final String BIRTH_YEAR = "byr";
    private static final String ISSUE_YEAR = "iyr";
    private static final String EXPIRATION_YEAR = "eyr";
    private static final String HEIGHT = "hgt";
    private static final String HAIR_COLOR = "hcl";
    private static final String EYE_COLOR = "ecl";
    private static final String PASSPORT_ID = "pid";
    private static final String COUNTRY_ID = "cid";


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

    boolean isValid1() {
        boolean allKeysValid = VALID_KEYS.containsAll(_properties.keySet());
        boolean hasAllRequiredKeys = _properties.keySet().containsAll(REQUIRED_KEYS);
        return hasAllRequiredKeys && allKeysValid;
    }

    private boolean byrValid() {
        try {
            int byr = Integer.parseInt(_properties.get(BIRTH_YEAR));
            return byr >= 1920 && byr <= 2002;
        } catch (NumberFormatException nfe) {
            return false;
        }

    }

    private boolean iyrValid() {
        try {
            int iyr = Integer.parseInt(_properties.get(ISSUE_YEAR));
            return iyr >= 2010 && iyr <= 2020;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean eyrValid() {
        try {
            int eyr = Integer.parseInt(_properties.get(EXPIRATION_YEAR));
            return eyr >= 2020 && eyr <= 2030;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean hgtCmValid(int value) {
        return value >= 150 && value <= 193;
    }

    private boolean hgtInValid(int value) {
        return value >= 59 && value <= 76;
    }

    private boolean hgtValid() {
        String hgtString = _properties.get(HEIGHT);

        Pattern heightRe = Pattern.compile("(?<value>[0-9]+)(?<unit>in|cm)");
        Matcher m = heightRe.matcher(hgtString);
        if (m.find()) {
            int value;
            try {
                value = Integer.parseInt(m.group("value"));
            } catch (NumberFormatException nfe) {
                return false;
            }

            switch (m.group("unit")) {
                case "in" -> {
                    return hgtInValid(value);
                }
                case "cm" -> {
                    return hgtCmValid(value);
                }

            }
        }
        return false;

    }

    private boolean hclValid() {
        Pattern hairColorRe = Pattern.compile("#[0-9a-f]{6}");

        String hclString = _properties.get(HAIR_COLOR);

        Matcher m = hairColorRe.matcher(hclString);
        return m.matches();
    }

    private boolean eclValid() {
        Set<String> validEyeColors = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        String eclString = _properties.get(EYE_COLOR);

        return validEyeColors.contains(eclString);
    }

    private boolean pidValid() {
        Pattern passportRe = Pattern.compile("[0-9]{9}");

        String pidString = _properties.get(PASSPORT_ID);

        Matcher m = passportRe.matcher(pidString);
        return m.matches();
    }

    private boolean strictRequirementsMet() {
        return byrValid() && iyrValid() && eyrValid() && hgtValid() && hclValid() && eclValid() && pidValid();
    }

    boolean isValid2() {
        return isValid1() && strictRequirementsMet();
    }

}