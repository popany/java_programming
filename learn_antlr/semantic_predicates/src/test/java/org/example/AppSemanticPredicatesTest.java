package org.example;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import org.example.ChatParser.ChatLexer;
import org.example.ChatParser.ChatParser;
import org.example.ChatParser.HtmlChatListener;
import org.example.ChatParser.ChatErrorListener;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class AppSemanticPredicatesTest 
{
    ChatParser createChatParser(String text) {
        CharStream inputStream = CharStreams.fromString(text);
        ChatLexer chatLexer = new ChatLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(chatLexer);
        ChatParser chatParser = new ChatParser(commonTokenStream);
        chatParser.removeErrorListeners();
        chatParser.addErrorListener(new ChatErrorListener());
        return chatParser;
    }

    @Test
    public void testValiedName()
    {
        ChatParser chatParser = createChatParser("John ");
        HtmlChatListener htmlChatListener = new HtmlChatListener();
        ParseTree parseTree = chatParser.name();
        ParseTreeWalker.DEFAULT.walk(htmlChatListener, parseTree);
        ChatErrorListener chatErrorListener = (ChatErrorListener)chatParser.getErrorListeners().get(0);
        assertEquals(new String(""), chatErrorListener.getSymbol());
    }

    @Test
    public void testInvaliedName()
    {
        ChatParser chatParser = createChatParser("Joh-");
        HtmlChatListener htmlChatListener = new HtmlChatListener();
        ParseTree parseTree = chatParser.name();
        ParseTreeWalker.DEFAULT.walk(htmlChatListener, parseTree);
        ChatErrorListener chatErrorListener = (ChatErrorListener)chatParser.getErrorListeners().get(0);
        assertEquals(new String("-"), chatErrorListener.getSymbol());
    }
}
