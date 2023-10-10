package de.salihbegovic;

import de.salihbegovic.compiler.Compiler;
import de.salihbegovic.lexer.Lexer;
import de.salihbegovic.lexer.Token;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
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

            Compiler compiler = new Compiler(path);
            try {
                compiler.compile();
                compiler.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.exit(0);
        }

        DirectoryStream<Path> files = Files.newDirectoryStream(path);

        for(Path paths : files) {
            if (!validName(paths)) continue;
            Compiler compiler = new Compiler(paths);
            compiler.compile();
            compiler.close();
        }

    }

    private static boolean validName(Path toCheck) {
        return (toCheck.getFileName().toString().contains(".jack") && toCheck.getFileName().toString().charAt(0) >= 'A'
                && toCheck.getFileName().toString().charAt(0) <= 'Z');
    }
}
