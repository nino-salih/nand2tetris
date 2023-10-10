package de.salihbegovic;

import de.salihbegovic.codewriter.CodeWriter;
import de.salihbegovic.parser.Parser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VMTranslator {

    private static String input;
    private static Path path;

    public static void main(String[] args) {

        if(args.length != 1) {
            System.err.println("The Programm needs exactly one Parameter exiting ...");
            System.exit(-1);
        }
        path = Paths.get(args[0]);
        if(!path.getFileName().toString().contains(".vm") || path.getFileName().toString().charAt(0) < 'A'
                || path.getFileName().toString().charAt(0) > 'Z') {
            System.err.println("The Filename doesn't follow the Name-convention");
            System.exit(-1);
        }

        readFile();
        Parser parser = new Parser(input.lines().iterator());

        CodeWriter codeWriter = new CodeWriter(path);

        try {
            while(parser.hasMoreLines()) {
                parser.advance();
                if(parser.arg1().isEmpty()) continue;
                switch (parser.commandType()) {
                    case C_POP:
                    case C_PUSH:
                        codeWriter.writePushPop(parser.commandType(),parser.arg1().get(), parser.arg2());
                        break;
                    case C_ARITHMETIC:
                        codeWriter.writeArithmetic(parser.arg1().get());
                        break;
                    default:
                        break;
                }
            }
            codeWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void readFile() {
        try {
            input = Files.readString(path.toAbsolutePath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Couldn't open the File: " + path + " does it exists ?");
            throw new RuntimeException(e);
        }
    }
}
