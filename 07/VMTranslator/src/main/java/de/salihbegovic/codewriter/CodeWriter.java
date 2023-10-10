package de.salihbegovic.codewriter;

import de.salihbegovic.parser.CommandType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class CodeWriter {
    private final C_ARITHMETIC cArithmetic = new C_ARITHMETIC();
    private final BufferedWriter bw;
    private final Path path;
    public CodeWriter(Path path) {
        this.path = path;
        try {
            FileOutputStream outputStream = new FileOutputStream(this.path.getFileName().toString().replace(".vm", ".asm"));
            this.bw = new BufferedWriter(new OutputStreamWriter(outputStream));
        } catch (IOException e) {
            System.err.println("Couldn't open the File: " + this.path + " does it exists ?");
            throw new RuntimeException(e);
        }
    }

    public void writeArithmetic(String command) throws IOException {
        cArithmetic.incrementCounter();
        bw.write("//" + command);
        bw.newLine();
        String toWrite = cArithmetic.getCommands().get(command);
        bw.write(toWrite);
        //bw.newLine();
    }

    public void writePushPop(CommandType commandType, String segment, int index) throws IOException {
        C_PUSH cPush = new C_PUSH(index, path.getFileName().toString().replace(".vm", ""));
        C_POP cPop = new C_POP(index, path.getFileName().toString().replace(".vm", ""));

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

    public void close() throws IOException {
        bw.write(cArithmetic.loopSegment());
        bw.close();
    }

}
