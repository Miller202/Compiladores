package analisadores;

import analisadores.lexico.Lexico;

public class Main {

    public static void main(String[] args) {
	    Lexico lexico = new Lexico("C:/Users/mulle/OneDrive/Documentos/GitHub/Compiladores/CodigosExemplo/hello_world.txt");
        System.out.println(lexico.getBufReader());
    }
}
