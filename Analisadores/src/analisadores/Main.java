package analisadores;

import analisadores.lexico.CategTokens;
import analisadores.lexico.Lexico;
import analisadores.lexico.Token;
import analisadores.sintatico.Sintatico;

public class Main {

    public static void main(String[] args) {
	    //Lexico lexico = new Lexico(args[0]);
        //System.out.println(lexico.getBufReader());
        try {
//            do {
//                lexico.columnUpdate();
//                token = lexico.nextToken();
//                if(token != null) {
//                    System.out.println(token);
//                }
//            }while(!Objects.requireNonNull(token).category.equals(CategTokens.EOF));
            Sintatico sintatico = new Sintatico(args[0]);
        }catch(Exception ex) {
            System.out.println("common error");
        }
    }
}
