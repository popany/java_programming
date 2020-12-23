// https://github.com/antlr/grammars-v4/tree/master/sql/mysql/Positive-Technologies

package org.example;

import org.example.condexpr.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

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
        System.out.println("tokens>");
        for (Token token : tokenStream.getTokens()) {
            String tokenName = vocabulary.getDisplayName(token.getType());
            System.out.println(String.format("%s -> %s", token.getText(), tokenName));
        }
    }

    static void printTree(ParseTree tree, CondexprParser parser) {
        System.out.println("tree>");
        System.out.println(tree.toStringTree(parser));
    }

    public static void main( String[] args )
    {
        while (true) {
            String input = getInput();
            if (input == null) {
                break;
            }

            System.out.println(String.format("input>\n%s", input));

            CharStream inputStream = CharStreams.fromString(input);
            CondexprLexer lexer = new CondexprLexer(inputStream); 
            CommonTokenStream tokenStream = new CommonTokenStream(lexer); 
            CondexprParser parser = new CondexprParser(tokenStream); 
            ParseTree tree = parser.expression();

            printTokens(tokenStream, parser.getVocabulary());
            printTree(tree, parser);

            System.out.println(String.format("output>\n"));
            DemoCondexprVisitor visitor = new DemoCondexprVisitor();
            visitor.visit(tree);
        }
    }
}
