package de.salihbegovic.lexer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {
    private final TokenType tokenType;
    private final String value;

    @Override
    public String toString() {
        String tag = tokenType.toString().toLowerCase();
        if(tokenType == TokenType.STRING_CONST) {
            tag = "stringConstant";
        }
        if(tokenType == TokenType.INT_CONST) {
            tag = "integerConstant";
        }

        return "<" + tag + "> " + value + " </" + tag + ">";
    }
}
