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
        fS();
    }

    private void setNextToken() {
        lexico.columnUpdate();
        token = lexico.nextToken();
        if(token.category == CategTokens.EOF) {
            System.out.println(token);
            return;
        }
    }

    private boolean checkCategory(CategTokens... categories) {
        for (CategTokens category: categories) {
            if (token.category == category) {
                return true;
            }
        }
        return false;
    }

    private boolean isTypeCategory() {
        return checkCategory(CategTokens.PR_INT, CategTokens.PR_FLOAT, CategTokens.PR_CHAR, CategTokens.PR_STR, CategTokens.PR_BOOL);
    }

    private boolean isConstCategory() {
        return checkCategory(CategTokens.CT_INT, CategTokens.CT_FLOAT, CategTokens.CT_CHAR, CategTokens.CT_STR);
    }

    private void printProduction(String left, String right) {
        String format = "%10s%s = %s";
        System.out.printf((format) + "%n", "", left, right);
    }

    private void fS(){
//        if(checkCategory(CategTokens.PR_MAIN)){
//            printProduction("S", "DcMain");
//            System.out.println(token);
//            setNextToken();
//            fDcMain();
//        }
        if(checkCategory(CategTokens.PR_FUNCTION)){
            printProduction("S", "DcFun S");
            fDcFun();
            fS();
        }
        else if(isTypeCategory()){
            printProduction("S", "DcId S");
            System.out.println(token);
            setNextToken();
            fDcId();
            fS();
        }
        else {
            printProduction("S", epsilon);
            System.out.println(token);
        }
    }

    private void fDcMain () {
    }

    private void fDcFun () {
        if(checkCategory(CategTokens.PR_FUNCTION)) {
            printProduction("DcFun", "'Funct' FunType 'id' '(' Param ')' BlockDc");
            System.out.println(token);
            setNextToken();
            fFunType();

            if (checkCategory(CategTokens.ID)) {
                setNextToken();
            }
            if (checkCategory(CategTokens.AB_PAR)) {
                setNextToken();
            }

            fParam();

            if (checkCategory(CategTokens.FEC_PAR)) {
                setNextToken();
            }

            if (checkCategory(CategTokens.PR_BEGIN)) {
                fBlockDc();
            }

        }
    }

    private void fDcId() {
    }

    private void fFunType() {
        if(checkCategory(CategTokens.PR_VOID)) {
            printProduction("FunType", "‘Void’");
            setNextToken();
        }
        else if(isTypeCategory()) {
            printProduction("FunType", "VarType");
            fVarType();
        }
    }

    private void fVarType() {
        if(checkCategory(CategTokens.PR_INT)) {
            printProduction("VarType", "‘Int’");
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_FLOAT)) {
            printProduction("VarType", "‘Float’");
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_CHAR)) {
            printProduction("VarType", "‘Char’");
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_STR)) {
            printProduction("VarType", "‘Str’");
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_BOOL)) {
            printProduction("VarType", "‘Bool’");
            setNextToken();
        }
    }





            fVet();

            fParamDcFat();
        }
    }

    private void fParamDcFat() {
        if (checkCategory(CategTokens.SEP)) {
            printProduction("ParamDcFat", "‘,’ ParamDc");
            setNextToken();

            fParamDc();
        }
        else {
            printProduction("ParamDcFat", epsilon);
        }
    }

    private void fBlockDc() {
        if (checkCategory(CategTokens.PR_BEGIN)) {
            printProduction("BlockDc", "‘Begin’ Instructions ‘End’");
            setNextToken();

            fInstructions();

            if (checkCategory(CategTokens.PR_END)) {
                setNextToken();
            }
        }
    }

    private void fVet() {
        if (checkCategory(CategTokens.AB_PAR)) {
            printProduction("Vet", "‘[’ ‘]’");
            setNextToken();

            if(checkCategory(CategTokens.FEC_PAR)) {
                setNextToken();
            }
        }
        else {
            printProduction("Vet", epsilon);
        }
    }

    private void fInstructions() {
        if (isTypeCategory()) {
            printProduction("Instructions", "DcId Instructions");

            fDcId();
            fInstructions();
        }
        else if (checkCategory(CategTokens.PR_WHILE, CategTokens.PR_IF, CategTokens.PR_FOR)) {
            printProduction("Instructions", "Command Instructions");

            fCommand();
            fInstructions();
        }
        else if(checkCategory(CategTokens.PR_INPUT, CategTokens.PR_OUTPUT, CategTokens.PR_OUTPUTLN)) {
            printProduction("Instructions", "CommandIO Instructions");

            fCommandIO();
            fInstructions();
        }
        else if(checkCategory(CategTokens.ID)) {
            printProduction("Instructions", "FunCall Instructions");

            fFunCall();
            fInstructions();
        }
        
    }

}
