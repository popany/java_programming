// https://tomassetti.me/antlr-mega-tutorial
// https://github.com/gabriele-tomassetti/antlr-mega-tutorial

package org.example;

import org.example.SimpleParser.MySimpleParserListener;
import org.example.SimpleParser.SimpleParserLexer;
import org.example.SimpleParser.SimpleParserParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class App 
{
    public static void main( String[] args )
    {
        CharStream inputStream = CharStreams.fromString("437 + 734");
        SimpleParserLexer simpleParserLexer = new SimpleParserLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(simpleParserLexer);
        SimpleParserParser simpleParserParser = new SimpleParserParser(commonTokenStream);
        simpleParserParser.setBuildParseTree(true);
        ParseTree parseTree = simpleParserParser.operation();
        ParseTreeWalker.DEFAULT.walk(new MySimpleParserListener(), parseTree);
    }
}
