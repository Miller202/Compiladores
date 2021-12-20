package analisadores.lexico;

import java.util.Hashtable;

public class WordsHashTable {

    Hashtable<String, CategTokens> reservedWord = new Hashtable<>();

    public WordsHashTable(){
        reservedWord.put("Begin", CategTokens.PR_BEGIN);
        reservedWord.put("End", CategTokens.PR_END);
        reservedWord.put("Funct", CategTokens.PR_FUNCTION);
        reservedWord.put("Main", CategTokens.PR_MAIN);
        reservedWord.put("Void", CategTokens.PR_VOID);
        reservedWord.put("Null", CategTokens.PR_NULL);
        reservedWord.put("Return", CategTokens.PR_RETURN);
        reservedWord.put("And", CategTokens.PR_AND);
        reservedWord.put("Or", CategTokens.PR_OR);
        reservedWord.put("Not", CategTokens.PR_NOT);
        reservedWord.put("If", CategTokens.PR_IF);
        reservedWord.put("Else", CategTokens.PR_ELSE);
        reservedWord.put("While", CategTokens.PR_WHILE);
        reservedWord.put("For", CategTokens.PR_FOR);
        reservedWord.put("Int", CategTokens.PR_INT);
        reservedWord.put("Float", CategTokens.PR_FLOAT);
        reservedWord.put("Char", CategTokens.PR_CHAR);
        reservedWord.put("Str", CategTokens.PR_STR);
        reservedWord.put("Bool", CategTokens.PR_BOOL);
        reservedWord.put("Input", CategTokens.PR_INPUT);
        reservedWord.put("Output", CategTokens.PR_OUTPUT);
        reservedWord.put("Outputln", CategTokens.PR_OUTPUTLN);
        reservedWord.put("True", CategTokens.PR_TRUE);
        reservedWord.put("False", CategTokens.PR_FALSE);
    }

}
