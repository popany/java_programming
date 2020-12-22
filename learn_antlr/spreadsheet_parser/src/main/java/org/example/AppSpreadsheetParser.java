package org.example;

import org.example.SpreadsheetParser.SpreadsheetLexer;
import org.example.SpreadsheetParser.SpreadsheetParser;
import org.example.SpreadsheetParser.DemoSpreadsheetVisitor;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class AppSpreadsheetParser 
{
    public static void main( String[] args )
    {
        String text = "log(10 + A1 * 37 + (5.4 - 7.4) ^ 4)";

        CharStream inputStream = CharStreams.fromString(text);
        SpreadsheetLexer spreadsheetLexer = new SpreadsheetLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(spreadsheetLexer);
        SpreadsheetParser spreadsheetParser = new SpreadsheetParser(commonTokenStream);

        SpreadsheetParser.ExpressionContext expressionContext = spreadsheetParser.expression();
        DemoSpreadsheetVisitor visitor = new DemoSpreadsheetVisitor();
        visitor.setData("A1", 2.0);

        System.out.println(visitor.visit(expressionContext));
    }
}
