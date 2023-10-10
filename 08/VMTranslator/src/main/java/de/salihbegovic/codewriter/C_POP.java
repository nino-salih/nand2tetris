package de.salihbegovic.codewriter;

import java.util.HashMap;

public class C_POP extends Command{
    private final String fileName;
    public C_POP(int idx, String fileName) {
        this.fileName = fileName;
        setValues(idx);
    }

    public void setIdx(int idx) {
        setValues(idx);
    }

    private void setValues(int idx) {
        this.idx = idx;
        commands.put("local",  loadSegment("LCL", this.idx) + popSegment());
        //FileName
        commands.put("static", "@" + fileName + this.idx + "\n" + popSegment());
        commands.put("this", loadSegment("THIS", this.idx) + popSegment());
        commands.put("that", loadSegment("THAT", this.idx) + popSegment());
        commands.put("pointer", "@R" + (this.idx + 3) + "\n" + popSegment());
        commands.put("temp", "@R" + (this.idx + 5) + "\n" + popSegment());
        commands.put("argument", loadSegment("ARG", this.idx) + popSegment());
    }
    // Needs to be Called after a command
    private String popSegment() {
        return  "D=A" + "\n" +
                "@R13" + "\n" +
                "M=D" + "\n" +
                popToStack() +
                "@R13" + "\n" +
                "A=M" + "\n" +
                "M=D" + "\n";
    }
}
