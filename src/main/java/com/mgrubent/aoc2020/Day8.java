package com.mgrubent.aoc2020;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day8 extends Puzzle {
    private static final Pattern instructionRe =
            Pattern.compile("(?<operation>acc|jmp|nop) (?<argument>[+-][0-9]+)");

    private Process _process;
    private final Program _program;

    /**
     * Constructor which accepts the puzzle input to be solved
     *
     * @param input the puzzle input
     */
    Day8(String input) {
        super(input);

        // Store the program, so that we can mutate it and instantiate fresh processes from it.
        _program = new Program(input.lines().map(Day8::parseLine).collect(Collectors.toList()));

        // Parse every line into an instruction, wrap that as a Program, and wrap that inside a new Process
        _process = new Process(_program);
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
        while (!_process.is_terminated()) {
            lastAccumulator = _process.getAccumulator();
            _process.step();
        }

        // If we are here, _process has terminated; we need to verify that it's because of infinite loop
        if (_process.get_exitCode().equals(ExitCode.INFINITE_LOOP)) {
            return Integer.toString(lastAccumulator);
        } else {
            return null;
        }
    }

    private Optional<Integer> getAccumulatorOnNormalExit() {
        int lastAccumulator = _process.getAccumulator();
        while (!_process.is_terminated()) {
            lastAccumulator = _process.getAccumulator();
            _process.step();
        }

        // If we are here, _process has terminated; we need to verify that it's because of normal exit
        if (_process.get_exitCode().equals(ExitCode.NORMAL)) {
            return Optional.of(lastAccumulator);
        } else {
            return Optional.empty();
        }
    }

    @Override
    String solve2() {
        // See if this program exits normally without mutation
        Optional<Integer> accumulator = getAccumulatorOnNormalExit();
        if (accumulator.isPresent()) {
            return Integer.toString(accumulator.get());
        }

        // Brute force mutate the program until it exits normally
        for (int i = 0; i < _program.instructions().size(); i++) {
            Instruction currentInstruction = _program.instructions().get(i);
            Operation op = currentInstruction.op();
            // "By changing exactly one jmp or nop, you can repair the boot code and make it terminate correctly"
            if (op.equals(Operation.NOP) || op.equals(Operation.JMP)) {
                // Copy the existing instructions into a new program
                List<Instruction> mutatedInstructions = new ArrayList<>(_program.instructions());

                // Mutate this instruction, and insert it back into the mutatedInstructions list
                Instruction mutatedInstruction = switch (op) {
                    case JMP -> new Instruction(Operation.NOP, currentInstruction.arg());
                    case NOP -> new Instruction(Operation.JMP, currentInstruction.arg());
                    default -> throw new IllegalStateException("Unexpected value: " + op);
                };
                mutatedInstructions.set(i, mutatedInstruction);

                // Instantiate a new process around these mutated instructions
                _process = new Process(new Program(mutatedInstructions));

                // Run this process and see if we get an accumulator
                accumulator = getAccumulatorOnNormalExit();
                if (accumulator.isPresent()) {
                    return Integer.toString(accumulator.get());
                }
            }
        }

        return null;
    }
}

record Argument(int number) {

}

enum Operation {
    ACC("acc"),
    JMP("jmp"),
    NOP("nop");

    Operation(String name) {
    }
}

record Instruction(Operation op, Argument arg) {

}

record Program(List<Instruction> instructions) {

}

enum ExitCode {
    NORMAL,
    INFINITE_LOOP,
    OUT_OF_BOUNDS;
}

class Process {
    private static final Logger LOGGER = LoggerFactory.getLogger(Process.class);

    private final Program _program;
    private int ptr = 0;
    private int accumulator = 0;
    private final boolean[] _visited;
    private boolean _terminated;
    private ExitCode _exitCode;

    Process(Program program) {
        _program = program;
        _visited = new boolean[_program.instructions().size()];
    }

    boolean is_terminated() {
        return _terminated;
    }

    ExitCode get_exitCode() {
        return _exitCode;
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
        // Handle this process having already terminated
        if (_terminated) {
            return;
        }

        // Handle exiting normally,
        // "by attempting to execute an instruction immediately after the last instruction in the file"
        if (ptr == _program.instructions().size()) {
            LOGGER.info("Attempting to execute an instruction immediately after the final instruction");
            _terminated = true;
            _exitCode = ExitCode.NORMAL;
            return;
        }

        // Handle being out of the program bounds
        if (ptr < 0 || ptr > _program.instructions().size()) {
            LOGGER.error("Program pointer {} is out of program bounds [0, {}]", ptr, _program.instructions().size());
            _terminated = true;
            _exitCode = ExitCode.OUT_OF_BOUNDS;
            return;
        }

        // Handle having visited this instruction before
        if (_visited[ptr]) {
            LOGGER.error("Instruction {}: {} has already been visited!", ptr, _program.instructions().get(ptr));
            _terminated = true;
            _exitCode = ExitCode.INFINITE_LOOP;
            return;
        }
        // Mark that this instruction has now been visited.
        _visited[ptr] = true;

        // Execute the next instruction
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