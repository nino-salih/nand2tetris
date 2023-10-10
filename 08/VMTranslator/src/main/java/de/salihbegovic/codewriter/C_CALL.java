package de.salihbegovic.codewriter;

public class C_CALL extends Command{

    private long counter = 0;
    private String functionName;
    private int nArgs;

    public C_CALL(String functionName, int nArgs) {
        this.functionName =functionName;
        this.nArgs = nArgs;
        setValues();
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setnArgs(int nArgs) {
        this.nArgs = nArgs;
    }
    public void resetCounter() {
        this.counter = 0;
        setValues();
    }
    public void incrementCounter() {
        counter++;
        setValues();
    }

    private void setValues() {
        commands.put(functionName + nArgs, callSegment());
    }

    // SEGMENT = SP - IDX
    private String loadSpInSegment(String segment, int idx) {
        return  "@SP" + "\n" +
                "D=M" + "\n" +
                "@" + idx + "\n" +
                "D=D-A" + "\n" +
                "@" + segment + "\n" +
                "M=D" + "\n";
    }
    private String pushSegment(String segment) {
        return "@" + segment + "\n" +
                "D=M" + "\n" +
                pushToStack();

    }

    private String callSegment() {

        String returnAddress = functionName + "$ret." + counter;
        return "@" + returnAddress + "\n" +
                "D=A" + "\n" +
                pushToStack() +
                pushSegment("LCL") +
                pushSegment("ARG") +
                pushSegment("THIS") +
                pushSegment("THAT") +
                loadSpInSegment("ARG", nArgs + 5) +
                "@SP" + "\n" +
                "D=M" + "\n" +
                "@LCL" + "\n" +
                "M=D" + "\n" +
                "@" + functionName + "\n" +
                "0;JMP" + "\n" +
                "(" + returnAddress + ")" + "\n";
    }

    public String bootstrap() {
        C_CALL cCall = new C_CALL("Sys.init", 0);

        return "@256" + "\n" +
                "D=A" + "\n" +
                "@SP" + "\n" +
                "M=D" + "\n" +
                cCall.getCommands().get("Sys.init0");
    }
}
