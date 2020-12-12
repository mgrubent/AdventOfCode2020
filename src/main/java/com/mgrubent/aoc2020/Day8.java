package com.mgrubent.aoc2020;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day8 extends Puzzle {
    private static final Pattern instructionRe =
            Pattern.compile("(?<operation>acc|jmp|nop) (?<argument>[+-][0-9]+)");

    private final Process _process;
    /**
     * Constructor which accepts the puzzle input to be solved
     *
     * @param input the puzzle input
     */
    Day8(String input) {
        super(input);
        // Parse every line into an instruction, wrap that as a Program, and wrap that inside a new Process
        _process = new Process(new Program(input.lines().map(Day8::parseLine).collect(Collectors.toList())));
    }

    private static Instruction parseLine(String line) {
        Matcher m = instructionRe.matcher(line);
        if (m.find()) {
            Operation operation = Operation.valueOf(m.group("operation").toUpperCase());
            Argument argument = new Argument(Integer.parseInt(m.group("argument")));
            return new Instruction(operation, argument);
        }
        throw new RuntimeException("Could not parse \"" + line + "\"; is it a valid Instruction?");
    }

    @Override
    int getDay() {
        return 8;
    }

    @Override
    String solve1() {
        int lastAccumulator = _process.getAccumulator();
        while (true) {
            try {
                lastAccumulator = _process.getAccumulator();
                _process.step();
            } catch (IllegalStateException ise) {
                return Integer.toString(lastAccumulator);
            } catch (IndexOutOfBoundsException oobe) {
                return null;
            }
        }
    }

    @Override
    String solve2() {
        return null;
    }
}

record Argument(int number) {

}

enum Operation {
    ACC("acc"),
    JMP("jmp"),
    NOP("nop");

    private final String _name;

    Operation(String name) {
        _name = name;
    }
}

record Instruction(Operation op, Argument arg) {

}

record Program(List<Instruction> instructions) {

}

class Process {
    private final Program _program;
    private int ptr = 0;
    private int accumulator = 0;
    private final boolean[] _visited;

    Process(Program program) {
        _program = program;
        _visited = new boolean[_program.instructions().size()];
    }

    int getAccumulator() {
        return accumulator;
    }

    /**
     * Execute the next instruction (which may or may not mutate this process' internal state),
     * and update the Process pointer.
     * <p>
     * "The moment the program tries to run any instruction a second time, you know it will never terminate"
     *
     * @throws IllegalStateException if the executing instruction has been visited before.
     */
    void step() throws IllegalStateException {
        // Handle being out of the program bounds
        if (ptr < 0 || ptr >= _program.instructions().size()) {
            throw new IndexOutOfBoundsException("Program pointer " + ptr + " is out of program bounds [0, "
            + _program.instructions().size() + ")");
        }

        // Handle having visited this instruction before
        if (_visited[ptr]) {
            throw new IllegalStateException("Instruction " + ptr + ": " + _program.instructions().get(ptr)
                    + " has already been visited!");
        }
        // Mark that this instruction has now been visited.
        _visited[ptr] = true;

        // Execute the instruction
        execute(_program.instructions().get(ptr));
    }

    private void execute(Instruction instruction) {
        switch (instruction.op()) {
            case ACC -> {
                accumulator += instruction.arg().number();
                ptr++;
            }
            case JMP -> ptr += instruction.arg().number();
            case NOP -> ptr++;
        }
    }

}