package analisadores.lexico;

import java.io.BufferedReader;
import java.io.FileReader;

public class Lexico {

    private int line, column, pos;
    private char[] cont;
    private BufferedReader bufReader;
    private String txtLine = " ";

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

            pos = 0;
            column = 0;

            return true;
        }

        return false;
    }

    public BufferedReader getBufReader() {
        return bufReader;
    }
}
