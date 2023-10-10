package de.salihbegovic;

import de.salihbegovic.codewriter.CodeWriter;
import de.salihbegovic.parser.Parser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VMTranslator {

    private static String input;
    private static Path path;

    public static void main(String[] args) throws IOException {

        if(args.length != 1) {
            //System.err.println("The Programm needs exactly one Parameter exiting ...");
            //System.exit(-1);
            path = Paths.get("").toAbsolutePath();
        } else {
            path = Paths.get(args[0]);
        }

        if (!Files.isDirectory(path)) {
            if(!validName(path)) {
                System.err.println("The File " + path.getFileName() + " doesnt follow the Name convention");
                System.exit(1);
            }

            CodeWriter codeWriter = new CodeWriter(path);
            readFile(path);
            Parser parser = new Parser(input.lines().iterator());
            write(codeWriter, parser);
            codeWriter.close();
            System.exit(0);
        }

        DirectoryStream<Path> files = Files.newDirectoryStream(path);
        CodeWriter codeWriter = new CodeWriter(path);
        for(Path paths : files) {
            if (!validName(paths)) continue;
            codeWriter.setFileName(paths);
            readFile(paths);
            Parser parser = new Parser(input.lines().iterator());
            write(codeWriter, parser);
        }
        codeWriter.close();

    }

    private static boolean validName(Path toCheck) {
        return (toCheck.getFileName().toString().contains(".vm") && toCheck.getFileName().toString().charAt(0) >= 'A'
                && toCheck.getFileName().toString().charAt(0) <= 'Z');
    }

    private static void write(CodeWriter codeWriter, Parser parser) {
        try {
            while(parser.hasMoreLines()) {
                parser.advance();
                if(parser.arg1().isEmpty()) continue;
                switch (parser.commandType()) {
                    case C_POP:
                    case C_PUSH:
                        codeWriter.writePushPop(parser.commandType(), parser.arg1().get(), parser.arg2());
                        break;
                    case C_ARITHMETIC:
                        codeWriter.writeArithmetic(parser.arg1().get());
                        break;
                    case C_LABEL:
                        codeWriter.writeLabel(parser.arg1().get());
                        break;
                    case C_IF:
                        codeWriter.writeIf(parser.arg1().get());
                        break;
                    case C_CALL:
                        codeWriter.writeCall(parser.arg1().get(), parser.arg2());
                        break;
                    case C_GOTO:
                        codeWriter.writeGoto(parser.arg1().get());
                        break;
                    case C_RETURN:
                        codeWriter.writeReturn();
                        break;
                    case C_FUNCTION:
                        codeWriter.writeFunction(parser.arg1().get(), parser.arg2());
                        break;
                    default:
                        break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void readFile(Path toOpen) {
        try {
            input = Files.readString(toOpen.toAbsolutePath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Couldn't open the File: " + toOpen + " does it exists ?");
            throw new RuntimeException(e);
        }
    }
}
