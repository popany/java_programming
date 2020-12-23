package org.example.SpreadsheetParser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

public class SpreadsheetErrorListener extends BaseErrorListener {
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
        System.out.println("[SpreadsheetErrorListener.syntaxError] " + msg);
        symbol = ((Token)offendingSymbol).getText();
    }
}
