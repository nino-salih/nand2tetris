package de.salihbegovic.codewriter;

public class C_RETURN extends Command{
    private final C_POP cPop = new C_POP(0,"");

    public C_RETURN(){
        setValues();
    }

    private void setValues() {
        commands.put("", returnSegment());
    }

    private String restoreSegment(String segment) {
        return "@R14" + "\n" +
                "AM=M-1" + "\n" +
                "D=M" + "\n" +
                "@" + segment + "\n" +
                "M=D" + "\n";
    }
    // R14 for Frame, R15 for Return Address
    private String returnSegment() {
        return  "@LCL" + "\n" +
                "D=M"  + "\n" +
                "@R14" + "\n" +
                "M=D" + "\n" +
                //retAddr = *(frame-5)
                "@5" + "\n" +
                "A=D-A" + "\n" +
                "D=M" + "\n" +
                "@R15" + "\n" +
                "M=D" + "\n" +
                //*ARGS = pop()
                cPop.getCommands().get("argument") +
                //SP = ARG + 1
                "@ARG" + "\n" +
                "D=M+1" + "\n" +
                "@SP" + "\n" +
                "M=D" + "\n" +
                restoreSegment("THAT") +
                restoreSegment("THIS") +
                restoreSegment("ARG") +
                restoreSegment("LCL") +
                "@R15" + "\n" +
                "A=M" + "\n" +
                "0;JMP" + "\n";

    }
}
