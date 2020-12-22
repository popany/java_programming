package org.example;

import static org.junit.Assert.assertTrue;
import org.example.MarkupParser.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    MarkupParser createChatParser(String text) {
        CharStream inputStream = CharStreams.fromString(text);
        MarkupLexer markupLexer = new MarkupLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(markupLexer);
        MarkupParser chatParser = new MarkupParser(commonTokenStream);
        markupParser.removeErrorListeners();
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
}
