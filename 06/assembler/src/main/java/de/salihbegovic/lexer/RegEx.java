package de.salihbegovic.lexer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegEx {
    private String regex;
    private TokenName tokenName;
}
