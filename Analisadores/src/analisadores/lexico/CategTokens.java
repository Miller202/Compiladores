package analisadores.lexico;

public enum CategTokens {

    EOF,
    ID,

    // palavras reservadas
    PR_BEGIN,
    PR_END,
    PR_FUNCTION,
    PR_MAIN,
    PR_VOID,
    PR_NULL,
    PR_RETURN,
    PR_AND,
    PR_OR,
    PR_NOT,
    PR_IF,
    PR_ELSE,
    PR_WHILE,
    PR_FOR,
    PR_INT,
    PR_FLOAT,
    PR_CHAR,
    PR_STR,
    PR_BOOL,
    PR_INPUT,
    PR_OUTPUT,
    PR_OUTPUTLN,
    PR_TRUE,
    PR_FALSE,

    // constantes
    CT_FLOAT,
    CT_INT,
    CT_CHAR,
    CT_STR,

    // operações
    OP_ATR,
    OP_RELEQUAL,
    OP_RELDIF,
    OP_AD,
    OP_SUB,
    OP_MULT,
    OP_DIV,
    OP_RES,
    OP_GREATER,
    OP_LESS,
    OP_GREATERT,
    OP_LESST,
    OP_NOTUNI,
    OP_CONCAT,
    OP_SIZE,

    // delimitadores
    AB_PAR,
    FEC_PAR,
    AB_COL,
    FEC_COL,
    TERMINAL,
    SEP,

    // erros
    ERR_ID,
    ERR_CHAR,
    ERR_NUM,
    ERR_PR,
    ERR_SYM

}
