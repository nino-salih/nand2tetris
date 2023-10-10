package de.salihbegovic.compiler;

import de.salihbegovic.lexer.Lexer;
import de.salihbegovic.lexer.Token;
import de.salihbegovic.lexer.TokenType;

import java.io.*;
import java.nio.file.Path;
import java.util.ListIterator;

public class Compiler {
    private final Path path;
    private String fileName;
    private final BufferedWriter bw;

    private final ListIterator<Token> tokenIterator;

    public Compiler(Path path) {
        this.path = path;
        this.fileName = path.getFileName().toString();

        try {
            String toSave;
            if (fileName.contains(".jack")) {
                //Single File
                toSave = this.path.toAbsolutePath().toString().replace(".jack", ".xml");
            } else {
                //Directory
                toSave = this.path.toAbsolutePath() + File.separator + fileName.concat(".xml");
            }

            Lexer lexer = new Lexer();
            lexer.setInput(path);
            this.tokenIterator = lexer.tokenize().listIterator();

            FileOutputStream outputStream = new FileOutputStream(toSave);
            this.bw = new BufferedWriter(new OutputStreamWriter(outputStream));

        } catch (IOException e) {
            System.err.println("Couldn't open the File: " + this.path + " does it exists ?");
            throw new RuntimeException(e);
        }
    }

    public void setFileName(Path rename) {
        this.fileName = rename.getFileName().toString().replace(".jack", "");
    }

    public void close() throws IOException {
        bw.close();
    }

    public void compile() throws IOException {
        compileClass();
    }

    private void validType(Token type) {
        if(type.getTokenType() != TokenType.KEYWORD && type.getTokenType() != TokenType.IDENTIFIER ) {
            throw new IllegalArgumentException("Should be an Identifier or an int, boolean, char but was " + type.getTokenType());
        }

        if(!type.getValue().equals("int") && !type.getValue().equals("char") && !type.getValue().equals("boolean") && type.getTokenType() != TokenType.IDENTIFIER ) {
            throw new IllegalArgumentException("Should be an Identifier or an int, boolean, char but was " + type.getValue());
        }
    }

    private boolean isValidType() {
        Token type = tokenIterator.next();
        tokenIterator.previous();
        return type.getTokenType() == TokenType.KEYWORD || type.getTokenType() == TokenType.IDENTIFIER ||
                type.getValue().equals("int") || type.getValue().equals("char") || type.getValue().equals("boolean");

    }

    private void validValue(Token token, String toCheck) {
        if(!token.getValue().equals(toCheck)) {
            throw new IllegalArgumentException("Should be \"" + toCheck + "\" but was " + token.getValue());
        }
    }

    private void writeNext(String toCheck) throws IOException {
        Token shouldBe = tokenIterator.next();
        validValue(shouldBe, toCheck);
        bw.write(shouldBe.toString());
        bw.newLine();
    }

    private void writeNext(TokenType toCheck) throws IOException {
        Token shouldBe = tokenIterator.next();
        if(shouldBe.getTokenType() != toCheck) {
            throw new IllegalArgumentException("Should be \"" + toCheck + "\" but was " + shouldBe.getTokenType());
        }
        bw.write(shouldBe.toString());
        bw.newLine();
    }

    private void writeNextType() throws IOException {
        Token type = tokenIterator.next();
        validType(type);
        bw.write(type.toString());
        bw.newLine();
    }

    private boolean shouldWriteOptional(String toCheck) {
        Token couldBe = tokenIterator.next();
        tokenIterator.previous();
        return couldBe.getValue().equals(toCheck);
    }

    private void compileClass() throws IOException {
        bw.write("<class>");
        bw.newLine();
        writeNext("class");
        writeNext(TokenType.IDENTIFIER);
        writeNext("{");

        boolean run = true;
        int counter = 0;
        while(run) {
            try {
                compileClassVarDec();
                counter++;
            } catch (IllegalArgumentException e) {
                tokenIterator.previous();
                run = false;

            }

        }
        run = true;
        counter = 0;
        while(run) {
            try {
                compileSubroutine();
                counter++;
            } catch (IllegalArgumentException e) {
                if(counter > 0) {
                    tokenIterator.previous();
                }
                run = false;
            }

        }

        writeNext("}");
        bw.write("</class>");
        bw.newLine();
    }

    private void compileClassVarDec() throws IOException {


        if(shouldWriteOptional("field")) {
            bw.write("<classVarDec>");
            bw.newLine();
            writeNext("field");

        }
        if(shouldWriteOptional("static")) {
            bw.write("<classVarDec>");
            bw.newLine();
            writeNext("static");

        }
        writeNextType();
        writeNext(TokenType.IDENTIFIER);

        while(shouldWriteOptional(",")) {
            writeNext(",");
            writeNext(TokenType.IDENTIFIER);
        }
        writeNext(";");
        bw.write("</classVarDec>");
        bw.newLine();
    }



    private void compileSubroutine() throws IOException {

        Token keyword = tokenIterator.next();

        if(keyword.getTokenType() != TokenType.KEYWORD) {
            throw new IllegalArgumentException("Should be an  KEYWORD but was " + keyword.getTokenType());
        }

        if(!keyword.getValue().equals("constructor") && !keyword.getValue().equals("function") && !keyword.getValue().equals("method")) {
            throw new IllegalArgumentException("Should be an constructor | function | method but was " + keyword.getValue());
        }
        bw.write("<subroutineDec>");
        bw.newLine();
        bw.write(keyword.toString());
        bw.newLine();

        Token type = tokenIterator.next();

        if(!type.getValue().equals("void")) {
            validType(type);
        }

        bw.write(type.toString());
        bw.newLine();

        writeNext(TokenType.IDENTIFIER);
        writeNext("(");
        compileParameterList();
        writeNext(")");
        compileSubroutineBody();
        bw.write("</subroutineDec>");
        bw.newLine();
    }

    private void compileParameterList() throws IOException {
        if(!isValidType()) {
            bw.write("<parameterList>");
            bw.newLine();
            bw.write("</parameterList>");
            bw.newLine();
            return;
        }
        bw.write("<parameterList>");
        bw.newLine();
        writeNextType();
        writeNext(TokenType.IDENTIFIER);

        while(shouldWriteOptional(",")) {
            writeNext(",");
            writeNextType();
            writeNext(TokenType.IDENTIFIER);
        }
        bw.write("</parameterList>");
        bw.newLine();
    }

    private void compileSubroutineBody() throws IOException{
        bw.write("<subroutineBody>");
        bw.newLine();
        writeNext("{");
        while(shouldWriteOptional("var")) {
            compileVarDec();
        }

        compileStatements(true);
        writeNext("}");
        bw.write("</subroutineBody>");
        bw.newLine();
    }

    private void compileVarDec() throws IOException {
        bw.write("<varDec>");
        bw.newLine();
        writeNext("var");
        writeNextType();
        writeNext(TokenType.IDENTIFIER);

        while(shouldWriteOptional(",")) {
            writeNext(",");
            writeNext(TokenType.IDENTIFIER);
        }

        writeNext(";");
        bw.write("</varDec>");
        bw.newLine();
    }
    //Mehrzahl
    public void compileStatements(boolean firstRun) throws IOException {
        Token statement = tokenIterator.next();
        tokenIterator.previous();
        if(firstRun) {
            bw.write("<statements>");
            bw.newLine();
        }
        switch (statement.getValue()) {
            case "let":
                compileLet();
                break;
            case "if":
                compileIf();
                break;
            case "while":
                compileWhile();
                break;
            case "do":
                compileDo();
                break;
            case "return":
                compileReturn();
                break;
            default:
                bw.write("</statements>");
                bw.newLine();
                return;
        }
        compileStatements(false);
    }

    private void compileLet() throws IOException {
        bw.write("<letStatement>");
        bw.newLine();

        writeNext("let");
        writeNext(TokenType.IDENTIFIER);

        // ('[' expression ']')?
        if(shouldWriteOptional("[")) {
            writeNext("[");
            compileExpression();
            writeNext("]");
        }

        writeNext("=");
        compileExpression();
        writeNext(";");

        bw.write("</letStatement>");
        bw.newLine();
    }

    private void compileIf() throws IOException {
        bw.write("<ifStatement>");
        bw.newLine();

        writeNext("if");
        writeNext("(");
        compileExpression();
        writeNext(")");
        writeNext("{");
        compileStatements(true);
        writeNext("}");
        if(shouldWriteOptional("else")) {
            writeNext("else");
            writeNext("{");
            compileStatements(true);
            writeNext("}");
        }


        bw.write("</ifStatement>");
        bw.newLine();
    }

    private void compileWhile() throws IOException {
        bw.write("<whileStatement>");
        bw.newLine();

        writeNext("while");
        writeNext("(");
        compileExpression();
        writeNext(")");

        writeNext("{");
        compileStatements(true);
        writeNext("}");
        bw.write("</whileStatement>");
        bw.newLine();
    }

    private void compileDo() throws IOException {
        bw.write("<doStatement>");
        bw.newLine();
        writeNext("do");
        compileSubroutineCall();
        writeNext(";");
        bw.write("</doStatement>");
        bw.newLine();
    }

    private void compileReturn() throws IOException {
        bw.write("<returnStatement>");
        bw.newLine();
        writeNext("return");
        if(isExpression()) {
            compileExpression();
        }
        writeNext(";");
        bw.write("</returnStatement>");
        bw.newLine();
    }


    private boolean isOp() {
        String op = tokenIterator.next().getValue();
        tokenIterator.previous();

        return (op.matches("[+\\-*/&|<>=]") );
    }

    private boolean isUnaryOp() {
        String op = tokenIterator.next().getValue();
        tokenIterator.previous();

        return (op.matches("[\\-~]") );
    }

    private boolean isExpression() {
        Token constant = tokenIterator.next();
        tokenIterator.previous();

        return constant.getTokenType() == TokenType.INT_CONST || constant.getTokenType() == TokenType.STRING_CONST
                || isKeyWordConstant() || constant.getTokenType() == TokenType.IDENTIFIER || shouldWriteOptional("(") || isUnaryOp();
    }

    private boolean isKeyWordConstant() {
        String constant = tokenIterator.next().getValue();
        tokenIterator.previous();

        return (constant.matches("true|false|null|this") );
    }

    private void compileExpression() throws IOException {
        bw.write("<expression>");
        bw.newLine();
        compileTerm();

        while(isOp()) {
            compileOp();
            compileTerm();
        }
        bw.write("</expression>");
        bw.newLine();
    }

    private void compileTerm() throws IOException {
        bw.write("<term>");
        bw.newLine();

        Token value = tokenIterator.next();
        Token lookAhead = tokenIterator.next();
        tokenIterator.previous();
        tokenIterator.previous();
        //easy Case
        if(value.getTokenType() == TokenType.INT_CONST || value.getTokenType() == TokenType.STRING_CONST || isKeyWordConstant()) {
            tokenIterator.next();
            bw.write(value.toString());
            bw.newLine();
            bw.write("</term>");
            bw.newLine();
            return;
        }

        //var Name
        if(value.getTokenType() == TokenType.IDENTIFIER && !lookAhead.getValue().equals(".") && !lookAhead.getValue().equals("(")) {
            writeNext(TokenType.IDENTIFIER);
            if(shouldWriteOptional("[")) {
                writeNext("[");
                compileExpression();
                writeNext("]");
            }
            bw.write("</term>");
            bw.newLine();
            return;
        }

        if(shouldWriteOptional("(")) {
            writeNext("(");
            compileExpression();
            writeNext(")");
            bw.write("</term>");
            bw.newLine();
            return;
        }

        if(isUnaryOp()) {
            writeNext(TokenType.SYMBOL);
            compileTerm();
            bw.write("</term>");
            bw.newLine();
            return;
        }

        compileSubroutineCall();
        bw.write("</term>");
        bw.newLine();
    }

    private void compileSubroutineCall() throws IOException {
        writeNext(TokenType.IDENTIFIER);

        if(shouldWriteOptional(".")) {
            writeNext(".");
            writeNext(TokenType.IDENTIFIER);
        }

        writeNext("(");
        compileExpressionList();
        writeNext(")");
    }

    private void compileOp() throws IOException {
        Token symbol = tokenIterator.next();
        tokenIterator.previous();
        if(!isOp()) {
            throw new IllegalArgumentException("Should be '+', '-', '*', '/', '&', '|', '<', '>', '=' but was " + symbol.getValue() );
        }
        tokenIterator.next();
        switch(symbol.getValue()) {
            case "<":
                bw.write(new Token(TokenType.SYMBOL, "&lt;").toString());
                bw.newLine();
                break;
            case ">":
                bw.write(new Token(TokenType.SYMBOL, "&gt;").toString());
                bw.newLine();
                break;
            case "&":
                bw.write(new Token(TokenType.SYMBOL, "&amp;").toString());
                bw.newLine();
                break;
            default:
                bw.write(symbol.toString());
                bw.newLine();
        }
    }

    private void compileExpressionList() throws IOException {
        if(!isExpression()) {
            bw.write("<expressionList>");
            bw.newLine();
            bw.write("</expressionList>");
            bw.newLine();
            return;
        }
        bw.write("<expressionList>");
        bw.newLine();
        compileExpression();

        while(shouldWriteOptional(",")) {
            writeNext(",");
            compileExpression();
        }
        bw.write("</expressionList>");
        bw.newLine();
    }

}
