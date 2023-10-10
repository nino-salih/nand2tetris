package de.salihbegovic.codewriter;

public class C_GOTO extends Command{
    private final String label;
    private final String key;

    public C_GOTO(String label, String fileName){
        // Throws IllegalArgumentException if label is not valid
        C_LABEL toCheck = new C_LABEL(label, fileName);

        this.label = toCheck.toString();
        this.key = label;
        setValues();
    }

    private void setValues() {
        commands.put(key, gotoSegment());
    }


    private String gotoSegment() {
        return "@" + this.label + "\n" +
                "0;JMP" + "\n";
    }
}
