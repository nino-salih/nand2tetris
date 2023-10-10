package de.salihbegovic.lexer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    private int lineNumber = 0;

    private String input;
    private int position = 0;
    private boolean skip = false;
    private final RegEx[] regRules = {
            new RegEx("@(R(1[0-5]|[0-9])|SP|LCL|ARG|THIS|THAT|SCREEN|KBD)", TokenName.PREDEFINED_SYMBOL),
            new RegEx("null|M=|D=|MD=|A=|AM=|AD=|AMD=", TokenName.DEST),
            new RegEx("[ADM][\\+\\-][1]|[D][\\+\\-][AM]|[AM][\\-][D]|[D][\\&\\|][AM]|[-!]{0,1}[ADM]|0|[-]{0,1}1", TokenName.COMP),
            new RegEx("null|;JGT|;JEQ|;JGE|;JLT|;JNE|;JLE|;JMP", TokenName.JUMP),
            new RegEx("\\@\\d+", TokenName.CONSTANTS),
            new RegEx("\\([a-zA-Z:_$.][\\w:$.]*\\)", TokenName.LABEL_SYMBOL),
            new RegEx("\\@[a-zA-Z:_$.][\\w:$.]*", TokenName.SYMBOL),
            new RegEx("\\d+", TokenName.DIGIT),
            new RegEx("\\/\\/.*", TokenName.COMMENT),
            new RegEx("\n", TokenName.NEW_LINE),
            new RegEx("\\s", TokenName.WHITE_SPACE),

    };

    private final LinkedList<Pattern> pattern = new LinkedList<>();

    public Lexer() {
        for(RegEx regEx : regRules) {
            pattern.add(Pattern.compile(regEx.getRegex()));
        }
    }

    public boolean hasNextToken() {
        return input.length() > position;
    }

    private String matching(Pattern regex, String substring) {
        Matcher matcher = regex.matcher(substring);
        if(matcher.lookingAt()) {
            position += matcher.group().length();
            return matcher.group();
        }

        return null;
    }
    public Token getNextToken() {
        if(!hasNextToken()) {
            return null;
        }
        int maxPos = Math.min(input.length(), position + 256);

        final String substring = input.substring(position, maxPos);

        if(substring.isEmpty()) {
            return null;
        }
        int index = -1;
        for(Pattern p : pattern) {
            index++;

            String tokenValue = matching(p, substring);

            if(tokenValue == null) {
                continue;
            }

            if (skip) return new Token(regRules[index].getTokenName(), tokenValue, lineNumber++);

            if(regRules[index].getTokenName() == TokenName.WHITE_SPACE) {
                return getNextToken();
            }

            Token C_INSTRUCTION = iscInstruction(index, tokenValue);
            if (C_INSTRUCTION != null) return C_INSTRUCTION;

            if(regRules[index].getTokenName() == TokenName.PREDEFINED_SYMBOL
                    || regRules[index].getTokenName() == TokenName.SYMBOL
                    || regRules[index].getTokenName() == TokenName.CONSTANTS) {
                tokenValue = tokenValue.replace("@", "");
            }
            // IGNORE WHITESPACE
            if(regRules[index].getTokenName() == TokenName.COMMENT|| regRules[index].getTokenName() == TokenName.WHITE_SPACE
                    ||regRules[index].getTokenName() == TokenName.NEW_LINE) {
                return getNextToken();
            }

            if(regRules[index].getTokenName() != TokenName.NEW_LINE
                    &&  regRules[index].getTokenName() != TokenName.COMMENT
                    && regRules[index].getTokenName() != TokenName.LABEL_SYMBOL) {
                lineNumber++;
            }

            return new Token(regRules[index].getTokenName(), tokenValue, lineNumber);
        }

        throw new IllegalStateException("Unexpected Token: " + substring);

    }

    private Token iscInstruction(int index, String tokenValue) {
        if(regRules[index].getTokenName() == TokenName.DEST) {
            skip = true;
            Token next = getNextToken();
            skip = false;
            if(next.getToken() == TokenName.COMP) {
                return new Token(TokenName.C_INSTRUCTION, tokenValue + next.getValue(), lineNumber);
            } else {
                throw new IllegalStateException("After DEST should follow COMP but was:" + next.getToken());
            }
        }

        if(regRules[index].getTokenName() == TokenName.COMP) {
            skip = true;
            Token next = getNextToken();
            skip = false;
            if(next.getToken() == TokenName.JUMP) {
                return new Token(TokenName.C_INSTRUCTION, tokenValue + next.getValue(), lineNumber);
            } else {
                throw new IllegalStateException("After COMP should follow JUMP but was:" + next.getToken());
            }
        }
        return null;
    }

    public List<Token> tokenize() {
        ArrayList<Token> tokens = new ArrayList<>();

        while(hasNextToken()) {
            Optional<Token> optionalToken = Optional.ofNullable(getNextToken());
            optionalToken.ifPresent(tokens::add);
        }
        return tokens;
    }

    public void setInput(Path input) {
        openFile(input);
    }

    private void openFile(Path path) {
        try {
            input = Files.readString(path.toAbsolutePath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Couldn't open the File: " + path + " does it exists ?");
            throw new RuntimeException(e);
        }

    }
}
