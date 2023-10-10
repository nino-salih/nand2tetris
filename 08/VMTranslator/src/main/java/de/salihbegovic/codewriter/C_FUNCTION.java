package de.salihbegovic.codewriter;

public class C_FUNCTION extends Command{
    private final String functionName;
    private final int nArgs;
    private final C_PUSH cPush = new C_PUSH(0, "");

    public C_FUNCTION(String functionName, int nArgs){
        this.functionName = functionName;
        this.nArgs = nArgs;
        setValues();
    }

    private void setValues() {
        commands.put(functionName + nArgs, functionSegment());
    }


    private String functionSegment() {
        StringBuilder functionSegment = new StringBuilder();

        functionSegment.append("(").append(functionName).append(")").append("\n");
        for(int i = 0; i < nArgs; i++) {
            functionSegment.append(cPush.getCommands().get("constant"));
        }

        return functionSegment.toString();
    }


}
