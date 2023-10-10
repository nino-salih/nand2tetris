package de.salihbegovic.lexer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {
    private TokenName token;
    private String value;
    private int lineNumber;


    public String toBinary() {
        int number = Integer.parseInt(this.value);
        return String.format("%16s", Integer.toBinaryString(number)).replace(" ", "0");
    }
}
