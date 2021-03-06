package analisadores.lexico;

import java.io.BufferedReader;
import java.io.FileReader;

public class Lexico {

    private int line, column, pos;
    private char[] cont;
    private BufferedReader bufReader;
    private String txtLine = " ";
    private final WordsHashTable hashTable = new WordsHashTable();

    public Lexico(String file){
        try{
            line = 1;
            column = 0;
            pos = 0;
            bufReader = new BufferedReader(new FileReader(file));
            readNextLine();
            cont = txtLine.toCharArray();
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    public String getPositionToken() {
        String format = "[%04d, %04d]";
        return String.format(format, line - 1, column + 1);
    }

    private boolean readNextLine(){

        String contTemp = " ";

        try {
            contTemp = bufReader.readLine();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if(contTemp != null)
        {
            txtLine = contTemp;
            String sep = " ";
            System.out.printf("%4d %s%s%n", line, sep, txtLine);
            txtLine += " ";

            line++;
            column = 0;
            pos = 0;

            return true;
        }

        return false;
    }

    public BufferedReader getBufReader() {
        return bufReader;
    }

    public Token nextToken(){
        int state = 0;
        char character;
        String lexico = "";

        while(true){
            if(pos == cont.length){
                if(!readNextLine()){
                    return new Token(CategTokens.EOF, "EOF", line, column - 1);
                }else{
                    cont = txtLine.toCharArray();
                }
            }

            character = cont[pos++]; // next char

            switch(state){
                case 0:
                    if(isWhiteSpc(character)){
                        columnUpdate();
                        state = 0;
                    }
                    else if(character == '#') {
                        if(isWhiteSpc(character)) {
                            columnUpdate();
                            state = 0;
                        }
                    }
                    else if(Character.isDigit(character)){
                        lexico += character;
                        state = 1;
                    }
                    else if(Character.isLowerCase(character)){
                        lexico += character;
                        state = 5;
                    }
                    else if(Character.isUpperCase(character)){
                        lexico += character;
                        state = 7;
                    }
                    else if(character == '\''){
                        lexico += character;
                        state = 9;
                    }
                    else if(character == '\"'){
                        lexico += character;
                        state = 11;
                    }
                    else if(isOperation(character)){
                        lexico += character;
                        state = 13;
                    }
                    else if(isSymbolToken(character)){
                        lexico += character;
                        CategTokens category = symbolTokens(character);
                        return new Token(category, lexico, line, column);
                    }
                    else {
                        new Token(CategTokens.ERR_SYM, lexico, line, column);
                    }
                    break;
                case 1:
                    if(character == '.') {
                        lexico += character;
                        state = 2;
                    }
                    else if(Character.isDigit(character)) {
                        lexico += character;
                    }
                    else if(!Character.isLetterOrDigit(character) || isWhiteSpc(character)
                            || isOperation(character)) {
                        pos--;
                        state = 3;
                    }
                    else {
                        new Token(CategTokens.ERR_NUM, lexico, line, column);
                    }
                    break;
                case 2:
                    if(Character.isDigit(character)) {
                        lexico += character;
                    }
                    else if(!Character.isLetterOrDigit(character) || isWhiteSpc(character)
                            || isOperation(character)) {
                        pos--;
                        state = 4;
                    }
                    else {
                        new Token(CategTokens.ERR_NUM, lexico, line, column);
                    }
                    break;
                case 3:
                    pos--;
                    return new Token(CategTokens.CT_INT, lexico, line, column);
                case 4:
                    pos--;
                    return new Token(CategTokens.CT_FLOAT, lexico, line, column);
                case 5:
                    if(Character.isDigit(character) || Character.isLowerCase(character)
                            || Character.isUpperCase(character)) {
                        lexico += character;
                    }
                    else if(!Character.isLetterOrDigit(character) || isWhiteSpc(character)
                            || isOperation(character)) {
                        pos--;
                        state = 6;
                    }
                    else {
                        new Token(CategTokens.ERR_ID, lexico, line, column);
                    }
                    break;
                case 6:
                    pos--;
                    return new Token(CategTokens.ID, lexico, line, column);
                case 7:
                    if(Character.isLowerCase(character)) {
                        lexico += character;
                    } else if(!Character.isLowerCase(character) || isWhiteSpc(character)) {
                        pos--;
                        state = 8;
                    }
                    break;
                case 8:
                    pos--;
                    if(hashTable.reservedWord.get(lexico) == null) {
                        new Token(CategTokens.ERR_PR, lexico, line, column);
                    }else {
                        return new Token(hashTable.reservedWord.get(lexico), lexico, line, column);
                    }
                case 9:
                    if((character < (char)127) && (character > (char)31)) {

                        char aux = character;
                        lexico += character;
                        character = cont[pos++]; // next char

                        if(aux == '\\' && character == '0'){
                            lexico += character;
                            character = cont[pos++];
                        }
                        if(character == '\'') {
                            lexico += character;
                            state = 10;
                        } else {
                            return new Token(CategTokens.ERR_CHAR, lexico, line, column);
                        }

                    }else {
                        new Token(CategTokens.ERR_CHAR, lexico, line, column);
                    }
                    break;
                case 10:
                    pos--;
                    return new Token(CategTokens.CT_CHAR, lexico, line, column);
                case 11:
                    if((character < (char)127) && (character > (char)31)) {

                        lexico += character;

                        if(character == '\"') {
                            state = 12;
                        }

                    }else {
                        new Token(CategTokens.ERR_CHAR, lexico, line, column);
                    }
                    break;
                case 12:
                    pos--;
                    return new Token(CategTokens.CT_STR, lexico, line, column);
                case 13:
                    pos -= 2;
                    character = cont[pos++];

                    if(character == '=') {

                        character = cont[pos++];

                        if(character != '=') {
                            pos--;
                            return new Token(CategTokens.OP_ATR, lexico, line, column);
                        }else {
                            lexico += character;
                            return new Token(CategTokens.OP_RELEQUAL, lexico, line, column);
                        }

                    }
                    else if(character == '!') {

                        character = cont[pos++];

                        if(character != '=') {
                            pos--;
                            return new Token(CategTokens.ERR_SYM, lexico, line, column);
                        }else {
                            lexico += character;
                            return new Token(CategTokens.OP_RELDIF, lexico, line, column);
                        }

                    }
                    else if(character == '>') {

                        character = cont[pos++];

                        if(character != '=') {
                            pos--;
                            return new Token(CategTokens.OP_GREATER, lexico, line, column);
                        }else {
                            lexico += character;
                            return new Token(CategTokens.OP_GREATERT, lexico, line, column);
                        }

                    }
                    else if(character == '<') {

                        character = cont[pos++];
                        if(character != '=') {
                            pos--;
                            return new Token(CategTokens.OP_LESS, lexico, line, column);
                        }else {
                            lexico += character;
                            return new Token(CategTokens.OP_LESST, lexico, line, column);
                        }

                    } else {
                        new Token(CategTokens.ERR_SYM, lexico, line, column);
                    }
            }
        }
    }

    public CategTokens symbolTokens(char character){
        if(character == ';') {
            return CategTokens.TERMINAL;
        }
        else if(character == ',') {
            return CategTokens.SEP;
        }
        else if(character == '(') {
            return CategTokens.AB_PAR;
        }
        else if(character == ')') {
            return CategTokens.FEC_PAR;
        }
        else if(character == '[') {
            return CategTokens.AB_COL;
        }
        else if(character == ']') {
            return CategTokens.FEC_COL;
        }
        else if(character == '+') {
            return CategTokens.OP_AD;
        }
        else if(character == '-') {
            return CategTokens.OP_SUB;
        }
        else if(character == '*') {
            return CategTokens.OP_MULT;
        }
        else if(character == '/') {
            return CategTokens.OP_DIV;
        }
        else if(character == '%') {
            return CategTokens.OP_RES;
        }
        else if(character == '~') {
            return CategTokens.OP_NOTUNI;
        }
        else if(character == '&') {
            return CategTokens.OP_CONCAT;
        }
        else if(character == '@'){
            return CategTokens.OP_SIZE;
        }
        else {
            return CategTokens.ERR_SYM;
        }
    }

    public void columnUpdate(){
        this.column = this.pos;
    }

    private boolean isWhiteSpc(char character) {
        return Character.isWhitespace(character) || character == '\n' || character == '\f'
                || character == '\b' || character == '\0' || character == '\r' || character == '\t';
    }

    private boolean isOperation(char character) {
        return  character == '=' || character == '!' || character == '>' || character == '<';
    }

    private boolean isSymbolToken(char character){
        return character == ';' || character == ',' || character == '(' || character == ')' || character == '['
        || character == ']' || character == '+' || character == '-' || character == '*' || character == '/' ||
                character == '%' || character == '~' || character == '&' || character == '@';
    }

}
