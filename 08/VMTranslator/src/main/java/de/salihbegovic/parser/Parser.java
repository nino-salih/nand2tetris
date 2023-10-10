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
        if(currentCommand.startsWith("//") || currentCommand.isEmpty()) {
            advance();
        }

    }

    public CommandType commandType() {
        this.currentCommand = currentCommand.replaceAll("//.*", "");
        String type = currentCommand.split(" ")[0];

        switch(type) {
            case "return":
                return CommandType.C_RETURN;
            case "label":
                return CommandType.C_LABEL;
            case "if-goto":
                return CommandType.C_IF;
            case "goto":
                return CommandType.C_GOTO;
            case "function":
                return CommandType.C_FUNCTION;
            case "call":
                return CommandType.C_CALL;
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
            return Optional.of("");
        }

        if(commandType() == CommandType.C_ARITHMETIC) {
            return Optional.of(currentCommand.replaceAll("\\s",""));
        }

        return Optional.of(currentCommand.split(" ")[1].replaceAll("\\s",""));

    }

    public int arg2() {

        if(commandType() != CommandType.C_PUSH &&
                commandType() != CommandType.C_POP  &&
                commandType() != CommandType.C_FUNCTION &&
                commandType() != CommandType.C_CALL) {
            return 0;
        }
        return Integer.parseInt(currentCommand.split(" ")[2].replaceAll("\\s",""));
    }
}
