package analisadores.lexico;

import java.util.Hashtable;

public class WordsHashTable {

    Hashtable<String, CategTokens> reserved_word = new Hashtable<>();

    public WordsHashTable(){
        reserved_word.put("Begin", CategTokens.PR_BEGIN);
        reserved_word.put("End", CategTokens.PR_END);
        reserved_word.put("Funct", CategTokens.PR_FUNCTION);
        reserved_word.put("Main", CategTokens.PR_MAIN);
        reserved_word.put("Void", CategTokens.PR_VOID);
        reserved_word.put("Null", CategTokens.PR_NULL);
        reserved_word.put("Return", CategTokens.PR_RETURN);
        reserved_word.put("And", CategTokens.PR_AND);
        reserved_word.put("Or", CategTokens.PR_OR);
        reserved_word.put("If", CategTokens.PR_IF);
        reserved_word.put("Else", CategTokens.PR_ELSE);
        reserved_word.put("While", CategTokens.PR_WHILE);
        reserved_word.put("For", CategTokens.PR_FOR);
        reserved_word.put("Int", CategTokens.PR_INT);
        reserved_word.put("Float", CategTokens.PR_FLOAT);
        reserved_word.put("Char", CategTokens.PR_CHAR);
        reserved_word.put("Str", CategTokens.PR_STR);
        reserved_word.put("Bool", CategTokens.PR_BOOL);
        reserved_word.put("Input", CategTokens.PR_INPUT);
        reserved_word.put("Output", CategTokens.PR_OUTPUT);
        reserved_word.put("Outputnl", CategTokens.PR_OUTPUTNL);
        reserved_word.put("True", CategTokens.PR_TRUE);
        reserved_word.put("False", CategTokens.PR_FALSE);
    }

}
