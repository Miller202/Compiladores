package analisadores.lexico;

public class Token {

    public CategTokens category;
    public String lexeme;
    public int line, column;

    public Token(CategTokens category, String lexeme, int line, int column) {
        this.category = category;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column + 1;
    }

    @Override
    public String toString() {
        String format = "              [%04d, %04d] (%04d, %20s) {%s} ";
        return String.format(format, line - 1, column, category.ordinal(), category, lexeme);
    }

}
