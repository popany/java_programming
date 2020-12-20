package org.example;

import org.example.MarkupParser.MarkupLexer;
import org.example.MarkupParser.MarkupParser;
import org.example.MarkupParser.MarkupVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class AppMarkupParser 
{
    public static void main( String[] args )
    {
        String text = "I would like to [b]emphasize[/b] this and [u]underline [b]that[/b][/u]. " +
            "Let's not forget to quote: [quote author=\"John\"]You're wrong![/quote]";
        CharStream inputStream = CharStreams.fromString(text);
        MarkupLexer MarkupLexer = new MarkupLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(MarkupLexer);
        MarkupParser markupParser = new MarkupParser(commonTokenStream);
        MarkupParser.FileContext fileContext = markupParser.file();                
        MarkupVisitor visitor = new MarkupVisitor();                
        visitor.visit(fileContext);   
    }
}
