package de.salihbegovic.codewriter;

public class C_IF extends Command{
    private final C_LABEL toCheck;
    private final String label;
    public C_IF(String label, String functionName){
        // Throws IllegalArgumentException if label is not valid
        this.toCheck = new C_LABEL(label, functionName);
        this.label =label;
        setValues();
    }

    private void setValues() {
        commands.put(label, ifSemgent());
    }


    private String ifSemgent() {
        return decrementStackPointer() +
                loadSP() +
                "D=M" + "\n" +
                "A=A-1" + "\n" +
                "@" + toCheck.toString() + "\n" +
                "D;JNE" + '\n';
    }
}
