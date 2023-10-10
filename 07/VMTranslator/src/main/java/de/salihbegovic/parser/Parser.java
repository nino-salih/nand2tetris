package de.salihbegovic.parser;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

public class Parser {

    private final Iterator<String> lines;
    private String currentCommand;

    public Parser(Iterator<String> lines) {
        this.lines = lines;
    }

    public boolean hasMoreLines() {
        return lines.hasNext();
    }

    public void advance() {
        if(!hasMoreLines()) {
            return;
        }
        currentCommand = lines.next();

        if(currentCommand.contains("//") || currentCommand.isEmpty()) {
            advance();
        }

    }

    public CommandType commandType() {
        String type = currentCommand.split(" ")[0];

        switch(type) {
            case "push":
                return CommandType.C_PUSH;
            case "pop":
                return CommandType.C_POP;
            case "add":
            case "sub":
            case "neg":
            case "eq":
            case "gt":
            case "lt":
            case "and":
            case "or":
            case "not":
                return CommandType.C_ARITHMETIC;
            default:
                return CommandType.UNKNOWN;
        }
    }

    public Optional<String> arg1() {
        if(commandType() == CommandType.C_RETURN) {
            return Optional.empty();
        }

        if(commandType() == CommandType.C_ARITHMETIC) {
            return Optional.ofNullable(currentCommand);
        }

        return Optional.ofNullable(currentCommand.split(" ")[1]);

    }

    public int arg2() {

        if(commandType() != CommandType.C_PUSH &&
                commandType() != CommandType.C_POP  &&
                commandType() != CommandType.C_FUNCTION &&
                commandType() != CommandType.C_CALL) {
            return 0;
        }

        return Integer.parseInt(currentCommand.split(" ")[2]);
    }
}
