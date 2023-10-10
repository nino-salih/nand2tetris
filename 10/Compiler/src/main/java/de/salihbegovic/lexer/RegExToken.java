package de.salihbegovic.lexer;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
public class RegExToken {
    private LinkedHashMap<Pattern, TokenType> regEx = new LinkedHashMap<>(10);

    public RegExToken() {
        init();
    }

    private void init() {
        regEx.put(Pattern.compile("(\\/\\/.*)|(/\\*\\*[\\s\\S]*?\\*/)"), TokenType.COMMENTS);
        regEx.put(Pattern.compile("[\\s\\n]"), TokenType.WHITE_SPACE);
        regEx.put(Pattern.compile("class|constructor|function|method|field|static|var|int|char|boolean|void|true|false|null|this|let|do|if|else|while|return"), TokenType.KEYWORD);
        regEx.put(Pattern.compile("[{}()\\[\\].,;+\\-*/&|<>=~]"), TokenType.SYMBOL);
        regEx.put(Pattern.compile("[_A-Za-z]\\w*"), TokenType.IDENTIFIER);
        // Need to check if it's in Range [0,32767]
        regEx.put(Pattern.compile("\\d+"), TokenType.INT_CONST);
        // Remove Double Quotes and newlines
        regEx.put(Pattern.compile("\".+\""), TokenType.STRING_CONST);
        // Unknown Token Should throw an Error
        regEx.put(Pattern.compile("."), TokenType.UNKNOWN);
    }
}
