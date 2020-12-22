package org.example;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.example.MarkupParser.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppMarkupParserTest 
{
    MarkupLexer markupLexer;
    MarkupParser markupParser;

    void setMarkupParser(String text) {
        CharStream inputStream = CharStreams.fromString(text);
        markupLexer = new MarkupLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(markupLexer);
        markupParser = new MarkupParser(commonTokenStream);
        markupLexer.removeErrorListeners();
        markupParser.removeErrorListeners();
        markupParser.addErrorListener(new MarkupErrorListener());
    }

    @Test
    public void testText() {
        setMarkupParser("anything in here");

        MarkupParser.ContentContext context = markupParser.content();        
        MarkupErrorListener markupErrorListener = (MarkupErrorListener)markupParser.getErrorListeners().get(0);
        
        assertEquals("", markupErrorListener.getSymbol());
    }

    @Test
    public void testInvalidText() {
        setMarkupParser("[anything in here");

        MarkupParser.ContentContext context = markupParser.content();        
        MarkupErrorListener markupErrorListener = (MarkupErrorListener)markupParser.getErrorListeners().get(0);
        
        // note that this.errorListener.symbol could be empty
        // when ANTLR doesn't recognize the token or there is no error.           
        // In such cases check the output of errorListener        
        assertEquals("[", markupErrorListener.getSymbol());
    }

    @Test
    public void testWrongMode()
    {
        setMarkupParser("author=\"john\"");                

        MarkupParser.AttributeContext context = markupParser.attribute(); 
        MarkupErrorListener markupErrorListener = (MarkupErrorListener)markupParser.getErrorListeners().get(0);

        TokenStream ts = markupParser.getTokenStream();        
        
        assertEquals(MarkupLexer.DEFAULT_MODE, markupLexer._mode);
        assertEquals(MarkupLexer.TEXT, ts.get(0).getType());        
        assertEquals("author=\"john\"", markupErrorListener.getSymbol());
    }

    @Test
    public void testAttribute()
    {
        setMarkupParser("author=\"john\"");
        // we have to manually push the correct mode
        markupLexer.pushMode(MarkupLexer.BBCODE);

        MarkupParser.AttributeContext context = markupParser.attribute(); 
        MarkupErrorListener markupErrorListener = (MarkupErrorListener)markupParser.getErrorListeners().get(0);
        TokenStream ts = markupParser.getTokenStream();        
        
        assertEquals(MarkupLexer.ID, ts.get(0).getType());
        assertEquals(MarkupLexer.EQUALS, ts.get(1).getType());
        assertEquals(MarkupLexer.STRING, ts.get(2).getType()); 
        
        assertEquals("", markupErrorListener.getSymbol());
    }

    @Test
    public void testInvalidAttribute()
    {
        setMarkupParser("author=/\"john\"");
        // we have to manually push the correct mode
        markupLexer.pushMode(MarkupLexer.BBCODE);
        
        MarkupParser.AttributeContext context = markupParser.attribute();        
        MarkupErrorListener markupErrorListener = (MarkupErrorListener)markupParser.getErrorListeners().get(0);
        
        assertEquals("/", markupErrorListener.getSymbol());
    }
}
