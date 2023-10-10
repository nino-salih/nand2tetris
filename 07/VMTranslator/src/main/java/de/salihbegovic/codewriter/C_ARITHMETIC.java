package de.salihbegovic.codewriter;

public class C_ARITHMETIC extends Command{

    private long counter = 0;

    public C_ARITHMETIC() {
        setValues();
    }

    public void incrementCounter() {
        counter++;
        setValues();
    }

    private void setValues() {
        commands.put("add", popToStack() + mathSegment("D+M"));
        commands.put("sub", popToStack() + mathSegment("M-D"));
        commands.put("neg", mathSegment("-M"));
        commands.put("and", popToStack() + mathSegment("D&M"));
        commands.put("or",  popToStack() + mathSegment("D|M"));
        commands.put("not", mathSegment("!M"));

        commands.put("gt", jumpSegment("JGT"));
        commands.put("eq", jumpSegment("JEQ"));
        commands.put("lt", jumpSegment("JLT"));
    }
    private String mathSegment(String symbol) {
        return decrementStackPointer() +
                loadSP() +
                "M=" + symbol + "\n" +
                incrementStackPointer();
    }

    private String jumpSegment(String jump) {
       return  popToStack() +
                "@SP" + "\n" +
                "A=M-1" + "\n" +
                "D=M-D" + "\n" +
                "M=-1" + "\n" +
                "@JUMP_SEGMENT" + counter + "\n" +
                "D;" + jump + "\n" + // Jump
                "@SP" + "\n" +
                "A=M-1" + "\n" +
                "M=0" + "\n" +
                "(JUMP_SEGMENT" + counter + ")" + "\n";
    }
}
