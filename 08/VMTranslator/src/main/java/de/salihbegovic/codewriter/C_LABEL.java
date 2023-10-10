package de.salihbegovic.codewriter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class C_LABEL extends Command{
    private static final Pattern labelPattern = Pattern.compile("^[a-zA-Z:_.][\\w:.]*");
    private final String label;
    private final String functionName;

    public C_LABEL(String label, String functionName) {
        Matcher m = labelPattern.matcher(label);
        if (!m.matches()) {
            throw new IllegalArgumentException("The Label: " + label + " don't follow the naming Convention");
        }
        this.label = label;
        this.functionName = functionName;
        setValues();
    }

    private void setValues() {
        commands.put(label, labelSegment());
    }

    private String labelSegment() {
        return "(" +  functionName + "$" + label + ")" + "\n";
    }

    @Override
    public String toString() {
        return functionName + "$" + label;
    }
}
