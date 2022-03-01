package analisadores;

import analisadores.sintatico.Sintatico;
import analisadores.lexico.CategTokens;
import analisadores.lexico.Lexico;
import analisadores.lexico.Token;
import analisadores.lexico.WordsHashTable;

public class Main {

    public static void main(String[] args) {
        try {
            Sintatico sintatico = new Sintatico(args[0]);
        }catch(Exception ex) {
            System.out.println("common error");
        }
    }
}
