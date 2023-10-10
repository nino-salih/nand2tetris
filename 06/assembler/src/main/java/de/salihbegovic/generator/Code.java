package de.salihbegovic.generator;

import de.salihbegovic.lexer.Token;
import de.salihbegovic.lexer.TokenName;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Code {
    private static final LinkedList<String> result = new LinkedList<>();

    public static void save(Path path) {

        try {
            FileOutputStream outputStream = new FileOutputStream(String.valueOf(path.getFileName()).replace(".asm", ".hack"));
            byte[] strToBytes = String.join("\n", result).getBytes(StandardCharsets.UTF_8);
            outputStream.write(strToBytes);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final SymbolTable symbolTable = new SymbolTable();

    private static byte dest(String input) {
        BitSet bitSet = new BitSet(3);

        for(char character : input.toCharArray()) {
            switch (character) {
                case 'M':
                    bitSet.set(0);
                    break;
                case 'D':
                    bitSet.set(1);
                    break;
                case 'A':
                    bitSet.set(2);
                    break;
                default:
                    throw new IllegalStateException("Illegal DEST instruction");
            }

        }


        return bitSet.toByteArray()[0];
    }

    private static byte comp(String input) {
        switch (input) {
            case "0":
                return 0b0101010;
            case "1":
                return 0b0111111;
            case "-1":
                return 0b0111010;
            case "D":
                return 0b0001100;
            case "A":
                return 0b0110000;
            case "M":
                return 0b1110000;
            case "!D":
                return 0b0001101;
            case "!A":
                return 0b0110001;
            case "!M":
                return 0b1110001;
            case "-D":
                return 0b0001111;
            case "-A":
                return 0b0110011;
            case "-M":
                return 0b1110011;
            case "D+1":
                return 0b0011111;
            case "A+1":
                return 0b0110111;
            case "M+1":
                return 0b1110111;
            case "D-1":
                return 0b0001110;
            case "A-1":
                return 0b0110010;
            case "M-1":
                return 0b1110010;
            case "D+A":
                return 0b0000010;
            case "D+M":
                return 0b1000010;
            case "D-A":
                return 0b0010011;
            case "D-M":
                return 0b1010011;
            case "A-D":
                return 0b0000111;
            case "M-D":
                return 0b1000111;
            case "D&A":
                return 0b0000000;
            case "D&M":
                return 0b1000000;
            case "D|A":
                return 0b0010101;
            case "D|M":
                return 0b1010101;
            default:
                throw new IllegalStateException("Illegal COMP instruction");
        }
    }

    private static byte jump(String input) {
        switch (input) {
            case "null":
                return 0b000;
            case "JGT":
                return 0b001;
            case "JEQ":
                return 0b010;
            case "JGE":
                return 0b011;
            case "JLT":
                return 0b100;
            case "JNE":
                return 0b101;
            case "JLE":
                return 0b110;
            case "JMP":
                return 0b111;
            default:
                throw new IllegalStateException("Illegal JUMP instruction");
        }
    }

    public static void assembly(List<Token> tokenList) {
        tokenList.forEach(

                Code::firstPass
        );

        tokenList.forEach(
                Code::secondPass
        );
    }

    private static void firstPass(Token token) {

        if(token.getToken() == TokenName.LABEL_SYMBOL) {
            token.setValue(token.getValue().replace("(", "").replace(")", ""));
            symbolTable.put(token.getValue(), token.getLineNumber());
        }

        if(token.getToken() == TokenName.PREDEFINED_SYMBOL) {
            token.setValue(String.valueOf(symbolTable.get(token.getValue())));
            token.setValue(token.toBinary());
        }
    }

    private static void secondPass(Token token) {
        AtomicInteger comp = new AtomicInteger();
        AtomicInteger jump = new AtomicInteger();
        AtomicInteger dest = new AtomicInteger();

        if(token.getToken() == TokenName.C_INSTRUCTION) {

            if(token.getValue().contains(";")) {
                Optional<String> optionalC = Optional.ofNullable(token.getValue().split(";")[0]);
                optionalC.ifPresent(s -> comp.set(comp(s)));
                Optional<String> optionalJ = Optional.ofNullable(token.getValue().split(";")[1]);
                optionalJ.ifPresent(s -> jump.set(jump(s)));
                comp.set(comp.get() << 6);
                token.setValue(String.valueOf(comp.get() + jump.get()));
                token.setValue(token.toBinary().replaceFirst("000", "111"));
            } else if (token.getValue().contains("=")) {
                Optional<String> optionalD = Optional.ofNullable(token.getValue().split("=")[0]);
                optionalD.ifPresent(s -> dest.set(dest(s)));
                Optional<String> optionalC = Optional.ofNullable(token.getValue().split("=")[1]);
                optionalC.ifPresent(s -> comp.set(comp(s)));

                comp.set(comp.get() << 6);
                dest.set(dest.get() << 3);
                token.setValue(String.valueOf(dest.get() + comp.get()));
                token.setValue(token.toBinary().replaceFirst("000", "111"));
            }
        }

        // A_INSTRUCTION
        if(token.getToken() == TokenName.CONSTANTS) {
            token.setValue(token.toBinary());
        }

        if(token.getToken() == TokenName.SYMBOL) {
            if(symbolTable.containsKey(token.getValue())) {
                int value = symbolTable.get(token.getValue());
                token.setValue(String.valueOf(value));
                token.setValue(token.toBinary());
            } else {
                symbolTable.put(token.getValue());
                token.setValue(String.valueOf(symbolTable.get(token.getValue())));
                token.setValue(token.toBinary());
            }
        }

        if(token.getToken() == TokenName.LABEL_SYMBOL) {
            return;
        }
        result.add(token.getValue());
    }
}
