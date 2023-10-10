package de.salihbegovic;

import de.salihbegovic.lexer.Lexer;
import de.salihbegovic.lexer.Token;

import java.nio.file.Paths;
import java.util.List;

import static de.salihbegovic.generator.Code.assembly;
import static de.salihbegovic.generator.Code.save;

public class Assembler {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();

        if(args.length != 1) {
            System.out.println("The Programm needs exactly one Parameter exiting ...");
            System.exit(-1);
        }

        lexer.setInput(Paths.get(args[0]));

        List<Token> tokenList = lexer.tokenize();

        assembly(tokenList);
        save(Paths.get(args[0]));
    }


}
