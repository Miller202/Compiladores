package analisadores.sintatico;

import analisadores.lexico.CategTokens;
import analisadores.lexico.Lexico;
import analisadores.lexico.Token;

public class Sintatico {
    private Lexico lexico;
    private Token token;
    private String epsilon = "Epsilon";

    public Sintatico(String a) {
        lexico = new Lexico(a);
        setNextToken();
    }

    public void setNextToken() {
        lexico.columnUpdate();
        token = lexico.nextToken();
        if(token.category == CategTokens.EOF) {
            System.out.println(token);
            return;
        }
        FirstProduction();
    }

    public boolean checkCategory(CategTokens... categories) {
        for (CategTokens category: categories) {
            if (token.category == category) {
                return true;
            }
        }
        return false;
    }

    public void printProduction(String left, String right) {
        String format = "%10s%s = %s";
        System.out.printf((format) + "%n", "", left, right);
    }

    public void FirstProduction(){
        if(checkCategory(CategTokens.PR_MAIN)){
            printProduction("S", "DcMain");
            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_FUNCTION)){
            printProduction("S", "DcFun S");
            System.out.println(token);
            setNextToken();
        }
        else{
            printProduction("S", epsilon);
            System.out.println(token);
            setNextToken();
        }
    }

}
