package analisadores;

import analisadores.lexico.Lexico;
import analisadores.lexico.Token;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {
	    Lexico lexico = new Lexico("C:/Users/mulle/OneDrive/Documentos/GitHub/Compiladores/CodigosExemplo/hello_world.txt");
        //System.out.println(lexico.getBufReader());
        Token token;
        try {
            do {
                token = lexico.nextToken();
                if(token != null) {
                    System.out.println(token);
                }
            }while(!Objects.requireNonNull(token).lexeme.equals("EOF"));
        }catch(Exception ex) {
            System.out.println("common error");
        }
    }
}
