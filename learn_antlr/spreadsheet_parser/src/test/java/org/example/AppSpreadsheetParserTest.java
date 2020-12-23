package org.example.SpreadsheetParser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import org.example.SpreadsheetParser.*;

public class AppSpreadsheetParserTest 
{
    SpreadsheetLexer spreadsheetLexer;
    SpreadsheetParser spreadsheetParser;

    void setSpreadsheetParser(String text) {
        CharStream inputStream = CharStreams.fromString(text);
        spreadsheetLexer = new SpreadsheetLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(spreadsheetLexer);
        spreadsheetParser = new SpreadsheetParser(commonTokenStream);
        spreadsheetLexer.removeErrorListeners();
        spreadsheetParser.removeErrorListeners();
        spreadsheetParser.addErrorListener(new SpreadsheetErrorListener());
    }

    public void testNumericAtom(String text) {
        setSpreadsheetParser(text);

        SpreadsheetParser.NumericAtomExpContext context = (SpreadsheetParser.NumericAtomExpContext)spreadsheetParser.expression();        
        CommonTokenStream tokenStream = (CommonTokenStream)spreadsheetParser.getInputStream();            
        SpreadsheetErrorListener spreadsheetErrorListener = (SpreadsheetErrorListener)spreadsheetParser.getErrorListeners().get(0);

        assertEquals(SpreadsheetLexer.NUMBER, tokenStream.get(0).getType());

        // note that this.errorListener.symbol could be null or empty
        // when ANTLR doesn't recognize the token or there is no error.
        // In such cases check the output of errorListener            
        assertEquals(new String(""), spreadsheetErrorListener.getSymbol());
    }

    @Test
    public void testNumericAtom() {
        testNumericAtom("1");
        testNumericAtom("10");
        testNumericAtom("10.00");
    }

    @Test
    public void testIdAtom()
    {
        setSpreadsheetParser("A1");

        SpreadsheetParser.IdAtomExpContext context = (SpreadsheetParser.IdAtomExpContext)spreadsheetParser.expression();

        CommonTokenStream tokenStream = (CommonTokenStream)spreadsheetParser.getInputStream();            
        SpreadsheetErrorListener spreadsheetErrorListener = (SpreadsheetErrorListener)spreadsheetParser.getErrorListeners().get(0);

        assertEquals(SpreadsheetLexer.ID, tokenStream.get(0).getType());           
        assertEquals("", spreadsheetErrorListener.getSymbol());
    }

    @Test
    public void testWrongIdAtom()
    {
        setSpreadsheetParser("AB1");

        var context = spreadsheetParser.expression();

        CommonTokenStream tokenStream = (CommonTokenStream)spreadsheetParser.getInputStream();            
        tokenStream.seek(0);
        SpreadsheetErrorListener spreadsheetErrorListener = (SpreadsheetErrorListener)spreadsheetParser.getErrorListeners().get(0);

        assertEquals(SpreadsheetLexer.NAME, tokenStream.get(0).getType());           
        assertEquals("<EOF>", spreadsheetErrorListener.getSymbol());
    }

    @Test
    public void testExpressionPow()
    {
        setSpreadsheetParser("5^3^2");

        SpreadsheetParser.PowerExpContext context = (SpreadsheetParser.PowerExpContext)spreadsheetParser.expression();

        CommonTokenStream tokenStream = (CommonTokenStream)spreadsheetParser.getInputStream();   

        assertEquals(SpreadsheetLexer.NUMBER, tokenStream.get(0).getType());
        assertEquals(SpreadsheetLexer.T__2, tokenStream.get(1).getType());
        assertEquals(SpreadsheetLexer.NUMBER, tokenStream.get(2).getType());
        assertEquals(SpreadsheetLexer.T__2, tokenStream.get(3).getType());
        assertEquals(SpreadsheetLexer.NUMBER, tokenStream.get(4).getType()); 
    }

    @Test
    public void testVisitPowerExp()
    {
        setSpreadsheetParser("4^3^2");

        SpreadsheetParser.PowerExpContext context = (SpreadsheetParser.PowerExpContext)spreadsheetParser.expression();

        SpreadsheetVisitor visitor = new DemoSpreadsheetVisitor();

        Double result = (Double)visitor.visitPowerExp(context);

        assertEquals(Double.valueOf(262144), result);
    }

    @Test
    public void testVisitFunctionExp()
    {
        setSpreadsheetParser("log(100)");

        SpreadsheetParser.FunctionExpContext context = (SpreadsheetParser.FunctionExpContext)spreadsheetParser.expression();

        SpreadsheetVisitor visitor = new DemoSpreadsheetVisitor();

        Double result = (Double)visitor.visitFunctionExp(context);

        assertEquals(Double.valueOf(2), result);
    }

    @Test
    public void testWrongVisitFunctionExp()
    {
        setSpreadsheetParser("logga(100)");

        SpreadsheetParser.FunctionExpContext context = (SpreadsheetParser.FunctionExpContext)spreadsheetParser.expression();

        SpreadsheetVisitor visitor = new DemoSpreadsheetVisitor();
        SpreadsheetErrorListener spreadsheetErrorListener = (SpreadsheetErrorListener)spreadsheetParser.getErrorListeners().get(0);

        Double result = (Double)visitor.visitFunctionExp(context);

        CommonTokenStream tokenStream = (CommonTokenStream)spreadsheetParser.getInputStream();   

        // this is syntactically correct and would be true even for a good function
        assertEquals(SpreadsheetLexer.NAME, tokenStream.get(0).getType());
        assertEquals(new String(""), spreadsheetErrorListener.getSymbol());
        // we choose to return 0 if we can't the find function with that NAME
        // so that's how we know is wrong
        assertEquals(Double.valueOf(0), result);
    }

    @Test
    public void testCompleteExp()
    {
        setSpreadsheetParser("log(5+6*7/8)");

        SpreadsheetParser.ExpressionContext context = spreadsheetParser.expression();

        SpreadsheetVisitor visitor = new DemoSpreadsheetVisitor();
        Double result = (Double)visitor.visit(context);            

        assertEquals(new String("1.0107238653917732"), result.toString());            
    }
}
