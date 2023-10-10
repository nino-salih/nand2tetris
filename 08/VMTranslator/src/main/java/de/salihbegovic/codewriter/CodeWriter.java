package de.salihbegovic.codewriter;

import de.salihbegovic.parser.CommandType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class CodeWriter {
    private final C_ARITHMETIC cArithmetic = new C_ARITHMETIC();
    private final C_CALL cCall = new C_CALL("", 0);
    private final BufferedWriter bw;
    private final Path path;
    private String fileName;

    public CodeWriter(Path path) {
        this.path = path;
        this.fileName = path.getFileName().toString();
        try {
            String toSave;
            if(fileName.contains(".vm")) {
                //Single File
                toSave = this.path.toAbsolutePath().toString().replace(".vm", ".asm");
            } else {
                //Directory
                toSave = this.path.toAbsolutePath() + File.separator + fileName.concat(".asm");
            }
            FileOutputStream outputStream = new FileOutputStream(toSave);
            this.bw = new BufferedWriter(new OutputStreamWriter(outputStream));
            String bootstrap = cCall.bootstrap();
            bw.write("//BOOTSTRAP CODE");
            bw.newLine();
            bw.write(bootstrap);

        } catch (IOException e) {
            System.err.println("Couldn't open the File: " + this.path + " does it exists ?");
            throw new RuntimeException(e);
        }
    }

    public void setFileName(Path rename) {
        this.fileName = rename.getFileName().toString().replace(".vm", "");
    }

    public void writeArithmetic(String command) throws IOException {
        cArithmetic.incrementCounter();
        bw.write("//" + command.toUpperCase());
        bw.newLine();
        String toWrite = cArithmetic.getCommands().get(command);
        bw.write(toWrite);
        //bw.newLine();
    }

    public void writePushPop(CommandType commandType, String segment, int index) throws IOException {

        C_PUSH cPush = new C_PUSH(index, fileName);
        C_POP cPop = new C_POP(index, fileName);

        if(cPush.getCommands().containsKey(segment) && commandType == CommandType.C_PUSH) {
            bw.write("//PUSH " + segment + " " + index);
            bw.newLine();
            String toWrite = cPush.getCommands().get(segment);
            bw.write(toWrite);
            //bw.newLine();
            return;
        }

        if(cPop.getCommands().containsKey(segment) && commandType == CommandType.C_POP) {
            bw.write("//POP " + segment + " " + index);
            bw.newLine();
            String toWrite = cPop.getCommands().get(segment);
            bw.write(toWrite);
            //bw.newLine();
        }
    }

    public void writeLabel(String label) throws IOException {
        C_LABEL cLabel = new C_LABEL(label, fileName);
        bw.write("//LABLE " + label);
        bw.newLine();
        String toWrite = cLabel.getCommands().get(label);
        bw.write(toWrite);
    }

    public void writeGoto(String label) throws IOException {
        C_GOTO cGoto = new C_GOTO(label, fileName);
        bw.write("//GOTO " + label);
        bw.newLine();
        String toWrite = cGoto.getCommands().get(label);
        bw.write(toWrite);
    }

    public void writeIf(String label) throws IOException {
        C_IF cIf = new C_IF(label, fileName);
        bw.write("//IF " + label);
        bw.newLine();
        String toWrite = cIf.getCommands().get(label);
        bw.write(toWrite);
    }

    public void writeFunction(String functionName, int nArgs) throws IOException {
        C_FUNCTION cFunction = new C_FUNCTION(functionName, nArgs);
        bw.write("//FUNCTION " + functionName + " " + nArgs);
        bw.newLine();
        String toWrite = cFunction.getCommands().get(functionName + nArgs);
        bw.write(toWrite);
    }

    public void writeCall(String functionName, int nArgs) throws IOException {
        cCall.setFunctionName(functionName);
        cCall.setnArgs(nArgs);
        cCall.incrementCounter();
        bw.write("//CALL " + functionName + " " + nArgs);
        bw.newLine();
        String toWrite = cCall.getCommands().get(functionName + nArgs);
        bw.write(toWrite);
    }

    public void writeReturn() throws IOException {
        C_RETURN cReturn = new C_RETURN();
        bw.write("//RETURN ");
        bw.newLine();
        String toWrite = cReturn.getCommands().get("");
        bw.write(toWrite);
    }

    public void close() throws IOException {
        bw.write(cArithmetic.loopSegment());
        bw.close();
    }

}
