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
        S();
    }

    private void setNextToken() {
        lexico.columnUpdate();
        token = lexico.nextToken();
        if(token.category == CategTokens.EOF) {
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

    private boolean isFunctTypeMinusIntCategory() {
        return checkCategory(CategTokens.PR_FLOAT, CategTokens.PR_CHAR, CategTokens.PR_STR, CategTokens.PR_BOOL, CategTokens.PR_VOID);
    }

    private boolean isRelCategory(){
        return checkCategory(CategTokens.OP_RELEQUAL, CategTokens.OP_RELDIF);
    }

    private boolean isOpsCategory(){
        return checkCategory(CategTokens.OP_GREATER, CategTokens.OP_LESS, CategTokens.OP_GREATERT, CategTokens.OP_LESST);
    }

    private void printProduction(String left, String right) {
        String format = "%10s%s = %s";
        System.out.printf((format) + "%n", "", left, right);
    }

    private void S(){
        if(checkCategory(CategTokens.PR_FUNCTION)){
            printProduction("S", "'Funct' DcFun S");

            System.out.println(token);
            setNextToken();

            DcFun();
            S();
        }
        else if(isTypeCategory()){
            printProduction("S", "DcId S");
            
            DcId();
            S();
        }
        else {
            printProduction("S", epsilon);
            System.out.println(token);
        }
    }

    private void DcFun () {
        if(isFunctTypeMinusIntCategory()) {
            printProduction("DcFun", "FunTypeMinusInt 'id' '(' Param ')' BlockDc");
            
            FunTypeMinusInt();

            if (checkCategory(CategTokens.ID)) {
                System.out.println(token);
                setNextToken();

                if (checkCategory(CategTokens.AB_PAR)) {
                    System.out.println(token);
                    setNextToken();

                    Param();

                    if (checkCategory(CategTokens.FEC_PAR)) {
                        System.out.println(token);
                        setNextToken();

                        BlockDc();
                    }
                }

            }
        }
        else if (checkCategory(CategTokens.PR_INT)) {
            printProduction("DcFun", "'Int' DcIntFunMain");

            System.out.println(token);
            setNextToken();

            DcIntFunMain();

        }
    }

    private void FunTypeMinusInt() {
        if(checkCategory(CategTokens.PR_VOID)) {
            printProduction("FunTypeMinusInt", "'Void'");
            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_FLOAT)) {
            printProduction("FunTypeMinusInt", "'Float'");
            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_CHAR)) {
            printProduction("FunTypeMinusInt", "'Char'");
            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_STR)) {
            printProduction("FunTypeMinusInt", "'Str'");
            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_BOOL)) {
            printProduction("FunTypeMinusInt", "'Bool'");
            System.out.println(token);
            setNextToken();
        }
    }

    private void DcIntFunMain(){
        if (checkCategory(CategTokens.ID)) {
            printProduction("DcIntFunMain", "'id' '(' Param ')' BlockDc");
            
            System.out.println(token);
            setNextToken();

            if (checkCategory(CategTokens.AB_PAR)) {
                System.out.println(token);
                setNextToken();

                Param();

                if (checkCategory(CategTokens.FEC_PAR)) {
                    System.out.println(token);
                    setNextToken();
                }

                BlockDc();
            }   
            
        } else if (checkCategory(CategTokens.PR_MAIN)) {
            printProduction("DcIntFunMain", "'Main' '(' ')' BlockDc");
            
            System.out.println(token);
            setNextToken();

            if (checkCategory(CategTokens.AB_PAR)) {
                System.out.println(token);
                setNextToken();

                 if (checkCategory(CategTokens.FEC_PAR)) {
                    System.out.println(token);
                    setNextToken();

                    BlockDc();
                }
            }

           
        }
    }

    private void VarType() {
        if(checkCategory(CategTokens.PR_INT)) {
            printProduction("VarType", "'Int'");

            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_FLOAT)) {
            printProduction("VarType", "'Float'");

            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_CHAR)) {
            printProduction("VarType", "'Char'");

            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_STR)) {
            printProduction("VarType", "'Str'");

            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_BOOL)) {
            printProduction("VarType", "'Bool'");

            System.out.println(token);
            setNextToken();
        }
        
    }

    private void Param() {
        if(isTypeCategory()){
            printProduction("Param", "ParamDc");
            ParamDc();
        }
        else{
            printProduction("Param", epsilon);
        }
    }

    private void ParamDc(){
            printProduction("ParamDc", "VarType 'id' Vet ParamDcFat");

            if (isTypeCategory()) {
                VarType();
            }

            if (checkCategory(CategTokens.ID)) {
                System.out.println(token);
                setNextToken();
            }    

            Vet();
            ParamDcFat();
    }

    private void ParamDcFat() {
        if (checkCategory(CategTokens.SEP)) {
            printProduction("ParamDcFat", "',' ParamDc");
            
            System.out.println(token);
            setNextToken();

            ParamDc();
        }
        else {
            printProduction("ParamDcFat", epsilon);
        }
    }

    private void BlockDc() {
        if (checkCategory(CategTokens.PR_BEGIN)) {
            printProduction("BlockDc", "'Begin' Instructions 'End'");

            System.out.println(token);
            setNextToken();

            Instructions();

            if (checkCategory(CategTokens.PR_END)) {
                System.out.println(token);
                setNextToken();
            }
        }
    }

    private void Vet() {
        if (checkCategory(CategTokens.AB_COL)) {
            printProduction("Vet", "'[' ']'");

            System.out.println(token);
            setNextToken();

            if(checkCategory(CategTokens.FEC_COL)) {
                System.out.println(token);
                setNextToken();
            }
        }
        else {
            printProduction("Vet", epsilon);
        }
    }

    private void Instructions() {
        if (isTypeCategory()) {
            printProduction("Instructions", "DcId Instructions");

            DcId();
            Instructions();
        }
        else if (checkCategory(CategTokens.PR_WHILE, CategTokens.PR_IF, CategTokens.PR_FOR)) {
            printProduction("Instructions", "Command Instructions");

            Command();
            Instructions();
        }
        else if(checkCategory(CategTokens.PR_INPUT, CategTokens.PR_OUTPUT, CategTokens.PR_OUTPUTLN)) {
            printProduction("Instructions", "CommandIO Instructions");

            CommandIO();
            Instructions();
        }
        else if(checkCategory(CategTokens.ID)) {
            printProduction("Instructions", "'id' AtrDirFunCall Instructions");

            System.out.println(token);
            setNextToken();

            AtrDirFunCall();
            Instructions();
        }
        else if(checkCategory(CategTokens.PR_RETURN)) {
            printProduction("Instructions", "Return Instructions");

            Return();
        }
        else {
            printProduction("Instructions", epsilon);
        }
        
    }
    
    private void DcId() {
        if (isTypeCategory()) {
            printProduction("DcId", "VarType DcIdAtr ';'");
            
            VarType();

            DcIdAtr();

            if (checkCategory(CategTokens.TERMINAL)) {
                System.out.println(token);
                setNextToken();
            }
        }
    }

    private void DcIdAtr() {
        if (checkCategory(CategTokens.ID)) {
            printProduction("DcIdAtr", "'id' Id Atr DcIdAtrFat");

            System.out.println(token);
            setNextToken();

            Id();
            Atr();
            DcIdAtrFat();
        }
    }

    private void DcIdAtrFat() {
        if(checkCategory(CategTokens.SEP)) {
            printProduction("DcIdAtrFat", "',' DcIdAtr");

            System.out.println(token);
            setNextToken();

            DcIdAtr();
        }
        else {
            printProduction("DcIdAtrFat", epsilon);
        }
    }

    private void Id() {
        if(checkCategory(CategTokens.AB_COL)) {
            printProduction("Id", "'[' Ea ']'");

            System.out.println(token);
            setNextToken();

            Ea();

            if(checkCategory(CategTokens.FEC_COL)) {
                System.out.println(token);
                setNextToken();
            }
        }
        else {
            printProduction("Id", epsilon);
        }
    }

    private void Atr() {
        if(checkCategory(CategTokens.OP_ATR)) {
            printProduction("Atr", "'=' AtrFat");

            System.out.println(token);
            setNextToken();

            AtrFat();
        }
        else {
            printProduction("Atr", epsilon);
        }
    }

    private void AtrFat() {
        if(checkCategory(CategTokens.AB_COL)) {
            printProduction("AtrFat", "'[' AtrVet ']'");

            System.out.println(token);
            setNextToken();

            AtrVet();

            if(checkCategory(CategTokens.FEC_COL)) {
                System.out.println(token);
                setNextToken();
            }

        } else {
            printProduction("AtrFat", "Ec");

            Ec();
        }
    }

    private void AtrVet() {
        printProduction("AtrVet", "Ec AtrVetFat");

        Ec();
        AtrVetFat();
    }

    private void AtrVetFat() {
        if (checkCategory(CategTokens.SEP)) {
            printProduction("AtrVetFat", "',' AtrVet");

            System.out.println(token);
            setNextToken();

            AtrVet();            
        }
        else {
            printProduction("AtrVetFat", epsilon);
        }
    }
    
    private void Command() {
        if(checkCategory(CategTokens.PR_WHILE)){
            printProduction("Command", "While");
            While();
        }
        else if(checkCategory(CategTokens.PR_IF)){
            printProduction("Command", "IfElse");
            IfElse();
        }
        else if(checkCategory(CategTokens.PR_FOR)){
            printProduction("Command", "For");
            For();
        }
    }

    private void CommandIO() {
        if(checkCategory(CategTokens.PR_INPUT)){
            printProduction("CommandIO", "Input");
            Input();
        }
        else if(checkCategory(CategTokens.PR_OUTPUT, CategTokens.PR_OUTPUTLN)){
            printProduction("CommandIO", "Output");
            Output();
        }
    }

    private void AtrDirFunCall() {
        if(checkCategory(CategTokens.AB_COL, CategTokens.OP_ATR)){
            printProduction("AtrDirFunCall", "AtrDir");

            AtrDir();
        }
        else if(checkCategory(CategTokens.AB_PAR)){
            printProduction("AtrDirFunCall", "FunCall");

            FunCall();
        }
    }

    private void AtrDir() {
        if(checkCategory(CategTokens.AB_COL)){
            printProduction("AtrDir", "'[' Ea ']' '=' Ec ';'");

            System.out.println(token);
            setNextToken();

            Ea();

            if(checkCategory(CategTokens.FEC_COL)){
                System.out.println(token);
                setNextToken();   
                if(checkCategory(CategTokens.OP_ATR)){
                    System.out.println(token);
                    setNextToken();   

                    Ec();
                    
                    if(checkCategory(CategTokens.TERMINAL)){
                        System.out.println(token);
                        setNextToken();   
                    }
                }
            }

        }
        else if (checkCategory(CategTokens.OP_ATR)){
            printProduction("AtrDir", "'=' Ec ';'");

            System.out.println(token);
            setNextToken();

            Ec();
            
            if(checkCategory(CategTokens.TERMINAL)){
                System.out.println(token);
                setNextToken();   
            }
        }
    }

    private void FunCall(){
        if(checkCategory(CategTokens.AB_PAR)) {
            printProduction("FunCall", "'(' ParamFun ')' ';'");

            System.out.println(token);
            setNextToken();

            ParamFun();

            if(checkCategory(CategTokens.FEC_PAR)) {
                System.out.println(token);
                setNextToken();

                if(checkCategory(CategTokens.TERMINAL)) {
                    System.out.println(token);
                    setNextToken();
                }
            }

        }
    }

    private void IdFunCall(){
        if(checkCategory(CategTokens.AB_PAR)) {
            printProduction("IdFunCall", "FunCall");

            FunCall();
        }
        else {
            printProduction("IdFunCall", "Id");

            Id();
        }
    }

    private void ParamFun(){
        printProduction("ParamFun", "Ec ParamFunLL");
        
        Ec();
        ParamFunLL();
    }

    private void ParamFunLL(){
        if(checkCategory(CategTokens.SEP)) {
            printProduction("ParamFunLL", "',' ParamFun");

            System.out.println(token);
            setNextToken();

            ParamFun();
        }
        else {
            printProduction("ParamFunLL", epsilon);
        }
    }

    private void Return(){
        if(checkCategory(CategTokens.PR_RETURN)) {
            printProduction("Return", "'Return' Ec ';'");

            System.out.println(token);
            setNextToken();
            
            Ec();

            if(checkCategory(CategTokens.TERMINAL)) {
                System.out.println(token);
                setNextToken();
            }
        }
    }

    private void IfElse(){
        if(checkCategory(CategTokens.PR_IF)) {
            printProduction("IfElse", "'If' '(' Eb ')' BlockDc IfElseFat");

            System.out.println(token);
            setNextToken();

            if(checkCategory(CategTokens.AB_PAR)) {
                System.out.println(token);
                setNextToken();

                Eb();

                if(checkCategory(CategTokens.FEC_PAR)) {
                    System.out.println(token);
                    setNextToken();

                    BlockDc();
                    IfElseFat();
                }
            }
        }
    }

    private void IfElseFat(){
        if(checkCategory(CategTokens.PR_ELSE)) {
            printProduction("IfElseFat", "'Else' BlockDc");

            System.out.println(token);
            setNextToken();

            BlockDc();
        }
        else {
            printProduction("IfElseFat", epsilon);
        }
    }

    private void While(){
        if (checkCategory(CategTokens.PR_WHILE)) {
            printProduction("While", "'While' '(' Eb ')' BlockDc");
            
            System.out.println(token);
            setNextToken();
            
            if(checkCategory(CategTokens.AB_PAR)){
                System.out.println(token);
                setNextToken();

                Eb();

                if(checkCategory(CategTokens.FEC_PAR)){
                    System.out.println(token);
                    setNextToken();

                    BlockDc();
                }
            }
        }
    }

    private void For(){
        if(checkCategory(CategTokens.PR_FOR)) {
            printProduction("For", "'For' '(' DcInt ',' IntValue ForFat");

            System.out.println(token);
            setNextToken();

            if(checkCategory(CategTokens.AB_PAR)) {
                System.out.println(token);
                setNextToken();

                DcInt();
                
                if(checkCategory(CategTokens.SEP)) {
                    System.out.println(token);
                    setNextToken();
                    
                    IntValue();
                    ForFat();
                }
            }    
        }
    }

    private void ForFat(){
        if(checkCategory(CategTokens.SEP)) {
            printProduction("ForFat", "',' IntValue ')' BlockDc");

            System.out.println(token);
            setNextToken();

            if(checkCategory(CategTokens.FEC_PAR)) {
                System.out.println(token);
                setNextToken();

                BlockDc();
            }
        }
        else if(checkCategory(CategTokens.FEC_PAR)) {
            printProduction("ForFat", "')' BlockDc");

            System.out.println(token);
            setNextToken();

            BlockDc();
        }
    }

    private void DcInt(){
        if(checkCategory(CategTokens.PR_INT)){
            printProduction("DcInt", "'Int' 'id' '=' IntValue");

            System.out.println(token);
            setNextToken();

            if(checkCategory(CategTokens.ID)) {
                System.out.println(token);
                setNextToken();

                if(checkCategory(CategTokens.OP_ATR)){
                    System.out.println(token);
                    setNextToken();
                    IntValue();
                }
            }
        }
        else if(checkCategory(CategTokens.ID)){
            printProduction("DcInt", "'id' AtrInt");

            System.out.println(token);
            setNextToken();

            AtrInt();
        }
        
    }

    private void IntValue(){
        if(checkCategory(CategTokens.ID, CategTokens.CT_INT)){
            if(checkCategory(CategTokens.ID)){
                printProduction("IntValue", "'id'");
            }
            else if(checkCategory(CategTokens.CT_INT)){
                printProduction("IntValue", "'CT_INT'");
            }
            System.out.println(token);
            setNextToken();
        }
    }

    private void AtrInt() {
        if (checkCategory(CategTokens.OP_ATR)) {
            printProduction("AtrInt", "'=' IntValue");

            System.out.println(token);
            setNextToken();

            IntValue();
        }
        else {
            printProduction("AtrInt", epsilon);
        }
    }

    private void Input(){
        if(checkCategory(CategTokens.PR_INPUT)){
            printProduction("Input", "'Input' '(' InputParam ')' ';'");
            System.out.println(token);
            setNextToken();

            if(checkCategory(CategTokens.AB_PAR)){
                System.out.println(token);
                setNextToken();
                InputParam();

                if(checkCategory(CategTokens.FEC_PAR)){
                    System.out.println(token);
                    setNextToken();
                    if(!checkCategory(CategTokens.TERMINAL)){
                    }else{
                        System.out.println(token);
                        setNextToken();
                    }
                }
            }
        }
    }

    private void InputParam(){
        if(checkCategory(CategTokens.ID)){
            printProduction("InputParam", "'id' Id InputParamFat");

            System.out.println(token);
            setNextToken();

            Id();
            InputParamFat();
        }
    }

    private void InputParamFat(){
        if(checkCategory(CategTokens.SEP)){
            printProduction("InputParamFat", "',' InputParam");
            System.out.println(token);
            setNextToken();
            InputParam();
        }
        else{
            printProduction("InputParamFat", epsilon);
        }
    }

    private void Output(){
        if(checkCategory(CategTokens.PR_OUTPUT, CategTokens.PR_OUTPUTLN)){
            if(checkCategory(CategTokens.PR_OUTPUT)){
                printProduction("Output", "'Output' '(' OutputParam ')' ';'");
            }
            else if(checkCategory(CategTokens.PR_OUTPUTLN)){
                printProduction("Output", "'Outputln' '(' OutputParam ')' ';'");
            }
            System.out.println(token);
            setNextToken();
    
            if(checkCategory(CategTokens.AB_PAR)){
                System.out.println(token);
                setNextToken();
                OutputParam();
    
                if(checkCategory(CategTokens.FEC_PAR)){
                    System.out.println(token);
                    setNextToken();
                    if(!checkCategory(CategTokens.TERMINAL)){
                    }
                    else{
                        System.out.println(token);
                        setNextToken();
                    }
                }
            }
        }
    }

    private void OutputParam(){
        printProduction("OutputParam", "Ec OutputParamFat");
        System.out.println(token);
        setNextToken();
        Ec();
        OutputParamFat();
    }

    private void OutputParamFat(){
        if(checkCategory(CategTokens.SEP)){
            printProduction("OutputParamFat", "',' OutputParam");
            System.out.println(token);
            setNextToken();
            OutputParam();
        }
        else {
            printProduction("OutputParamFat", epsilon);
        }
    }

    private void Ec() {
        printProduction("Ec", "Eb EcLL");
        Eb();
        EcLL();
    }
 
    private void EcLL() {
        if(checkCategory(CategTokens.OP_CONCAT)){
            printProduction("EcLL", "'OP_CONCAT' Eb EcLL");
            System.out.println(token);
            setNextToken();
            Eb();
            EcLL();
        } 
        else{
            printProduction("EcLL", epsilon);
        }
    }

    private void Eb() {
        printProduction("Eb", "Tb EbLL");
        Tb();
        EbLL();
    }

    private void EbLL(){
        if(checkCategory(CategTokens.PR_OR, CategTokens.PR_AND)){
            if(checkCategory(CategTokens.PR_OR)){
                printProduction("EbLL", "'OP_OR' Tb EbLL");
            }
            else if(checkCategory(CategTokens.PR_AND)){
                printProduction("EbLL", "'OP_AND' Tb EbLL");
            }
            System.out.println(token);
            setNextToken();
            Tb();
            EbLL();
        }
        else{
            printProduction("EbLL", epsilon);
        }
    }

    private void Tb(){
        printProduction("Tb", "Ra TbLL");
        Ra();
        TbLL();
    }

    private void TbLL(){
        if(checkCategory(CategTokens.PR_NOT)){
            printProduction("TbLL", "'PR_NOT' Ra TbLL");
            System.out.println(token);
            setNextToken();
            Ra();
            TbLL();
        }
        else{
            printProduction("TbLL", epsilon);
        }
    }

    private void Ra(){
        printProduction("Ra", "Rb RaLL");
        Rb();
        RaLL();
    }

    private void RaLL(){
        if(isRelCategory()){
            printProduction("RaLL", "Rel Rb RaLL");
            Rel();
            System.out.println(token);
            setNextToken();
            Rb();
            RaLL();
        }
        else{
            printProduction("RaLL", epsilon);
        }
    }

    private void Rb(){
        printProduction("Rb", "Ea RbLL");
        Ea();
        RbLL();
    }

    private void RbLL(){
        if(isOpsCategory()){
            printProduction("RbLL", "Ops Ea RbLL");
            Ops();
            System.out.println(token);
            setNextToken();
            Ea();
            RbLL();
        }
        else{
            printProduction("RbLL", epsilon);
        }
    }

    private void Ea(){
        printProduction("Ea", "Ta EaLL");
        Ta();
        EaLL();
    }

    private void EaLL(){
        if(checkCategory(CategTokens.OP_AD, CategTokens.OP_SUB)){
            if(checkCategory(CategTokens.OP_AD)){
                printProduction("EaLL", "'OP_AD' Ta EaLL");
            }
            else if(checkCategory(CategTokens.OP_SUB)){
                printProduction("EaLL", "'OP_SUB' Ta EaLL");
            }
            System.out.println(token);
            setNextToken();
            Ta();
            EaLL();
        }
        else{
            printProduction("EaLL", epsilon);
        }
    }

    private void Ta(){
        printProduction("Ta", "Fa TaLL");
        Fa();
        TaLL();
    }

    private void TaLL(){
        if(checkCategory(CategTokens.OP_MULT, CategTokens.OP_DIV, CategTokens.OP_RES)){
            if(checkCategory(CategTokens.OP_MULT)){
                printProduction("TaLL", "'OP_MULT' Fa TaLL");
            }
            else if(checkCategory(CategTokens.OP_DIV)){
                printProduction("TaLL", "'OP_DIV' Fa TaLL");
            }
            else if(checkCategory(CategTokens.OP_RES)){
                printProduction("TaLL", "'OP_RES' Fa TaLL");
            }
            System.out.println(token);
            setNextToken();
            Fa();
            TaLL();
        }
        else{
            printProduction("TaLL", epsilon);
        }
    }

    private void Fa(){
        if(checkCategory(CategTokens.AB_PAR)){
            printProduction("Fa", "'(' Ec ')'");
            System.out.println(token);
            setNextToken();
            Ec();
            if (!checkCategory(CategTokens.FEC_PAR)){
            } 
            else {
                System.out.println(token);
                setNextToken();
            }
        }
        else if(checkCategory(CategTokens.ID)){
            printProduction("Fa", "'id' IdFunCall");
            System.out.println(token);
            setNextToken();
            IdFunCall();
        }
        else if(checkCategory(CategTokens.CT_INT)){
            printProduction("Fa", "'CT_INT'");
            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.CT_FLOAT)){
            printProduction("Fa", "'CT_FLOAT'");
            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_TRUE)){
            printProduction("Fa", "'PR_TRUE'");
            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_FALSE)){
            printProduction("Fa", "'PR_FALSE'");
            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.PR_BOOL)){
            printProduction("Fa", "'PR_BOOL'");
            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.CT_CHAR)){
            printProduction("Fa", "'CT_CHAR'");
            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.CT_STR)){
            printProduction("Fa", "'CT_STR'");
            System.out.println(token);
            setNextToken();
        }
        else if(checkCategory(CategTokens.OP_NOTUNI)){
            printProduction("Fa", "'OP_NOTUNI' 'id'");
            System.out.println(token);
            setNextToken();
            if(checkCategory(CategTokens.ID)){
                System.out.println(token);
                setNextToken(); 
            }
        }
        else if(checkCategory(CategTokens.OP_SIZE)){
            printProduction("Fa", "'OP_SIZE' 'id'");
            System.out.println(token);
            setNextToken();
            if(checkCategory(CategTokens.ID)){
                System.out.println(token);
                setNextToken();
            }
        }
    }

    private void Rel(){
        if(checkCategory(CategTokens.OP_RELEQUAL)){
            printProduction("Rel", "'OP_RELEQUAL'");
        }
        else if(checkCategory(CategTokens.OP_RELDIF)){
            printProduction("Rel", "'OP_RELDIF'");
        }
    }

    private void Ops(){
        if(checkCategory(CategTokens.OP_GREATER)){
            printProduction("Ops", "'OP_GREATER'");
        }
        else if(checkCategory(CategTokens.OP_LESS)){
            printProduction("Ops", "'OP_LESS'");
        }
        else if(checkCategory(CategTokens.OP_GREATERT)){
            printProduction("Ops", "'OP_GREATERT'");
        }
        else if(checkCategory(CategTokens.OP_LESST)){
            printProduction("Ops", "'OP_LESST'");
        }
    }
    
}
