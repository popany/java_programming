package org.example.ChatParser;

import org.antlr.v4.runtime.*;

public class ChatErrorListener extends BaseErrorListener {
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
        System.out.println("[ChatErrorListener.syntaxError] " + msg);
        symbol = ((Token)offendingSymbol).getText();
    }
}
