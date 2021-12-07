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
            column = 1;
            pos = 0;
            bufReader = new BufferedReader(new FileReader(file));
            readNextLine();
            cont = txtLine.toCharArray();
        }catch(Exception exception){
            exception.printStackTrace();
        }
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
            System.out.printf("%4d %s %n", line, txtLine);
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
                    return new Token(CategTokens.EOF, "EOF", line, column);
                }else{
                    cont = txtLine.toCharArray();
                }
            }

            character = cont[pos++]; // próximo char

            switch(state){
                case 0:
                    if(isWhitespc(character)){
                        state = 0;
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
                    else if(character == ';') {
                        lexico += character;
                        column++;
                        return new Token(CategTokens.TERMINAL, lexico, line, column);
                    }
                    else if(character == '(') {
                        lexico += character;
                        column++;
                        return new Token(CategTokens.AB_PAR, lexico, line, column);
                    }
                    else if(character == ')') {
                        lexico += character;
                        column++;
                        return new Token(CategTokens.FEC_PAR, lexico, line, column);
                    }
                    else {
                        return new Token(CategTokens.ERR_SYM, lexico, line, column);
                    }
                    break;
                case 1:
                    if(character == '.') {
                        lexico += character;
                        state = 2;
                        column++;
                    }
                    else if(Character.isDigit(character)) {
                        lexico += character;
                    }
                    else if(!Character.isLetterOrDigit(character) || isWhitespc(character)
                            || isOperation(character)) {
                        pos--;
                        state = 3;
                    }
                    else {
                        column++;
                        new Token(CategTokens.ERR_NUM, lexico, line, column);
                    }
                    break;
                case 2:
                    if(Character.isDigit(character)) {
                        lexico += character;
                    }
                    else if(!Character.isLetterOrDigit(character) || isWhitespc(character)
                            || isOperation(character)) {
                        pos--;
                        state = 4;
                    }
                    else {
                        column++;
                        new Token(CategTokens.ERR_NUM, lexico, line, column);
                    }
                    break;
                case 3:
                    pos--;
                    column++;
                    return new Token(CategTokens.CT_INT, lexico, line, column);
                case 4:
                    pos--;
                    column++;
                    return new Token(CategTokens.CT_FLOAT, lexico, line, column);
                case 5:
                    if(Character.isDigit(character) || Character.isLowerCase(character)
                            || Character.isUpperCase(character)) {
                        lexico += character;
                    }
                    else if(!Character.isLetterOrDigit(character) || isWhitespc(character)
                            || isOperation(character)) {
                        pos--;
                        state = 6;
                    }
                    else {
                        column++;
                        new Token(CategTokens.ERR_ID, lexico, line, column);
                    }
                    break;
                case 6:
                    pos--;
                    column++;
                    return new Token(CategTokens.ID, lexico, line, column);
                case 7:
                    if(Character.isLowerCase(character)) {
                        lexico += character;
                    } else if(!Character.isLowerCase(character) || isWhitespc(character)) {
                        pos--;
                        state = 8;
                    }
                    break;
                case 8:
                    pos--;
                    if(hashTable.reservedWord.get(lexico) == null) {
                        column++;
                        return new Token(CategTokens.ERR_PR, lexico, line, column);
                    }else {
                        column++;
                        return new Token(hashTable.reservedWord.get(lexico), lexico, line, column);
                    }
            }
        }
    }

    private boolean isWhitespc(char character) {
        return Character.isWhitespace(character) || character == '\n' || character == '\f'
                || character == '\b' || character == '\0' || character == '\r' || character == '\t';
    }

    private boolean isOperation(char character) {
        return  character == '=' || character == '!' || character == '>' || character == '<';
    }

}
