// https://github.com/antlr/grammars-v4/tree/master/sql/mysql/Positive-Technologies

package org.example;

import org.example.condexpr.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.List;
import java.util.Arrays;

public class AppCondexpr 
{
    static String getInput() {
        System.out.println("Please enter expressions:");

        String input = System.console().readLine(); 
        if (input == null) {
            return null;
        }
        return input;
    }

    static void printTokens(CommonTokenStream tokenStream, Vocabulary vocabulary) {
        System.out.println("\ntokens>");
        for (Token token : tokenStream.getTokens()) {
            String tokenName = vocabulary.getDisplayName(token.getType());
            System.out.println(String.format("%s -> %s", token.getText(), tokenName));
        }
    }

    static void printTree(ParseTree tree, CondexprParser parser) {
        System.out.println("\ntree>");
        System.out.println(tree.toStringTree(parser));
        System.out.println("\npretty tree>");
        List<String> ruleNames = Arrays.asList(parser.getRuleNames());

        TreePrinterListener listener = new TreePrinterListener(ruleNames);
        ParseTreeWalker.DEFAULT.walk(listener, tree);
        System.out.println(listener.toString());
    }

    public static void main( String[] args )
    {
        while (true) {
            String input = getInput();
            if (input == null) {
                break;
            }

            System.out.println(String.format("\ninput>\n%s", input));

            CharStream inputStream = CharStreams.fromString(input);
            CondexprLexer lexer = new CondexprLexer(inputStream); 
            CommonTokenStream tokenStream = new CommonTokenStream(lexer); 
            CondexprParser parser = new CondexprParser(tokenStream); 
            ParseTree tree = parser.expression();

            printTokens(tokenStream, parser.getVocabulary());
            printTree(tree, parser);

            System.out.println(String.format("\noutput>\n"));
            DemoCondexprVisitor visitor = new DemoCondexprVisitor();
            visitor.visit(tree);
        }
    }
}
