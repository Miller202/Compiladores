package analisadores;

import analisadores.sintatico.Sintatico;

public class Main {

    public static void main(String[] args) {
        try {
            Sintatico sintatico = new Sintatico(args[0]);
        }catch(Exception ex) {
            System.out.println("common error");
        }
    }
}
