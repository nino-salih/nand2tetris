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
    private int position = 0;
    private final RegExToken regExToken = new RegExToken();
    private String input;

    public Lexer() {
    }

    private boolean hasNextToken() {
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

    private Token advance() {
        if (!hasNextToken()) {
            return null;
        }
        int maxPos = Math.min(input.length(), position + 2048);

        final String substring = input.substring(position, maxPos);

        if (substring.isEmpty()) {
            return null;
        }

        for (Pattern p : regExToken.getRegEx().keySet()) {
            String tokenValue = matching(p, substring);
            TokenType current = regExToken.getRegEx().get(p);

            if (tokenValue == null) {
                continue;
            }

            if(current == TokenType.COMMENTS || current == TokenType.WHITE_SPACE) {
                return null;
            }

            if (current == TokenType.UNKNOWN) {
                throw new IllegalArgumentException("The Value: \"" + tokenValue + "\" is not a valid Input");
            }

            if (current == TokenType.INT_CONST) {
                return new Token(current, String.valueOf(intVal(tokenValue)));
            }

            if (current == TokenType.STRING_CONST) {
                return new Token(current, stringVal(tokenValue));
            }

            return new Token(current, tokenValue);
        }

        return null;
    }

    private int intVal(String token) {
        int tmp = Integer.parseInt(token);

        if(tmp < 0 || tmp > 32767) {
            throw new IllegalArgumentException("Position: " + position + " The Integer value of " + tmp + "is not in the Range of [0,32767]");
        }
        return tmp;
    }

    private String stringVal(String token) {
        return token.replaceAll("[\"\n]", "");
    }

    public void setInput(Path input) {
        openFile(input);
    }

    public List<Token> tokenize() {
        ArrayList<Token> tokens = new ArrayList<>();

        while(hasNextToken()) {
            Optional<Token> optionalToken = Optional.ofNullable(advance());
            optionalToken.ifPresent(tokens::add);
        }
        return tokens;
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
