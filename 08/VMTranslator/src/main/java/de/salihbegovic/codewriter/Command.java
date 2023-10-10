package de.salihbegovic.codewriter;

import java.util.HashMap;
import java.util.Map;

public class Command {

    protected int idx;
    protected HashMap<String, String> commands = new HashMap<>(20);

    public Map<String, String> getCommands() {
        return commands;
    }
    protected String incrementStackPointer() {
       return "@SP" + "\n" +
               "M=M+1" + "\n";
    }
    protected String decrementStackPointer() {
        return "@SP" + "\n" +
                "M=M-1" + "\n";
    }
    protected String loadSP() {
        return "@SP" + "\n" +
                "A=M" + "\n";
    }

    protected String loadSegment(String segment, int idx) {
        return "@" + segment + "\n" +
                "D=M" + "\n" +
                "@" + idx + "\n" +
                "A=D+A" + "\n";
    }

    protected String pushToStack() {
        return loadSP() +
                "M=D" + "\n" +
                incrementStackPointer();
    }

    protected String popToStack() {
        return decrementStackPointer() +
                "A=M" + "\n" +
                "D=M" + "\n";
    }
    public String loopSegment() {
        return "(END$LABEL)" + "\n" +
                "@END$LABEL" + "\n" +
                "0;JMP";
    }


}
