package de.salihbegovic.codewriter;

public class C_PUSH extends Command{

    private final String fileName;
    public C_PUSH(int idx, String fileName) {
        this.fileName = fileName;
        setValues(idx);
    }

    public void setIdx(int idx) {
        setValues(idx);
    }

    private void setValues(int idx) {
        this.idx = idx;
        commands.put("local",  loadSegment("LCL", this.idx) + "D=M" + "\n" + pushToStack());
        commands.put("static", "@" + fileName + this.idx + "\n" + "D=M" + "\n" + pushToStack());
        commands.put("constant", "@" + this.idx + "\n" + "D=A" + "\n"+ pushToStack());
        commands.put("this", loadSegment("THIS", this.idx) + "D=M" + "\n"+ pushToStack());
        commands.put("that", loadSegment("THAT", this.idx) + "D=M" + "\n"+ pushToStack());
        commands.put("pointer", "@R" + (this.idx + 3) + "\n" + "D=M"+ "\n"+ pushToStack());
        commands.put("temp", "@R" + + (this.idx + 5) + "\n" + "D=M"+ "\n"+ pushToStack());
        commands.put("argument", loadSegment("ARG", this.idx) + "D=M" + "\n"+ pushToStack());
    }
}
