//https://pragprog.com/titles/tpantlr2/the-definitive-antlr-4-reference/
//  The Definitive ANTLR 4 Reference
//    CHAPTER 4 A Quick Tour
//      4.1 Matching an Arithmetic Expression Language
//      4.2 Building a Calculator Using a Visitor

package org.example;

import org.example.ExprParser.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class AppCalculator 
{
    static String getInput() {
        System.out.println("Please enter expressions (\';\' for line break): ");

        String input = System.console().readLine(); 
        if (input == null) {
            return null;
        }

        input = input.replaceAll(";", "\n");
        input = input + "\n";
        return input;
    }

    public static void main( String[] args )
    {
        while (true) {
            String input = getInput();
            if (input == null) {
                break;
            }

            System.out.println(String.format("input>\n%s", input));
            System.out.println(String.format("output>\n"));

            CharStream inputStream = CharStreams.fromString(input);
            ExprLexer lexer = new ExprLexer(inputStream); 
            CommonTokenStream tokens = new CommonTokenStream(lexer); 
            ExprParser parser = new ExprParser(tokens); 
            ParseTree tree = parser.prog();
            EvalVisitor eval = new EvalVisitor();
            eval.visit(tree);
        }
    }
}
