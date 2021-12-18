package analisadores;

import analisadores.lexico.CategTokens;
import analisadores.lexico.Lexico;
import analisadores.lexico.Token;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {
	    Lexico lexico = new Lexico(args[0]);
        //System.out.println(lexico.getBufReader());
        Token token;
        try {
            do {
                token = lexico.nextToken();
                if(token != null) {
                    System.out.println(token);
                }
            }while(!Objects.requireNonNull(token).category.equals(CategTokens.EOF));
        }catch(Exception ex) {
            System.out.println("common error");
        }
    }
}
