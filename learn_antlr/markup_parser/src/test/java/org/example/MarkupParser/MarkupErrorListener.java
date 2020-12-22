package org.example.MarkupParser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

public class MarkupErrorListener extends BaseErrorListener {
    String symbol = "";

    public String getSymbol() {
        return symbol;
    }

    @Override
    public void syntaxError(Recognizer<?,?> recognizer,
        Object offendingSymbol,
        int line,
        int charPositionInLine,
        String msg,
        RecognitionException e)
    {
        System.out.println(String.format("[MarkupErrorListener.syntaxError] %s" + msg));

        if(offendingSymbol.getClass().getName() == "org.antlr.v4.runtime.CommonToken")
        {            
            CommonToken token = (CommonToken) offendingSymbol;
            symbol = token.getText();            
        } 
    }
}
