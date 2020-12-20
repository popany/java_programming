package org.example.MarkupParser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import org.example.MarkupParser.MarkupParser;

public class MarkupVisitor extends MarkupParserBaseVisitor<String>
{
    @Override
    public String visitFile(MarkupParser.FileContext context) {
         visitChildren(context);
         
         System.out.println("");
         
         return null;
    }
    
    @Override
    public String visitContent(MarkupParser.ContentContext context) {
        return context.getText();        
    }

    @Override
    public String visitElement(MarkupParser.ElementContext context) {
        if (context.parent instanceof MarkupParser.FileContext) {
            if (context.content() != null) {
                System.out.println(visitContent(context.content()));
            }
            if (context.tag() != null) {
                System.out.println(visitTag(context.tag()));
            }
        }
        return null;
    }

    @Override
    public String visitTag(MarkupParser.TagContext context) {
        String startDelimiter = "";
        String endDelimiter = "";

        String id = context.ID(0).getText();

        switch (id) {
            case "b":
                startDelimiter = endDelimiter = "**";
                break;
            case "u":
                startDelimiter = endDelimiter = "*";
                break;
            case "quote":
                String attribute = context.attribute().STRING().getText();
                attribute = attribute.substring(1, attribute.length() - 1);
                startDelimiter = System.lineSeparator() + "> ";
                endDelimiter = System.lineSeparator() + "> " + System.lineSeparator() + "> - " + attribute + System.lineSeparator();
                break;
        }

        String text = startDelimiter;
        for (MarkupParser.ElementContext node : context.element()) {
            if (node.tag() != null) {
                text += visitTag(node.tag());
            }
            if (node.content() != null) {
                text += visitContent(node.content());
            }
        }
        text += endDelimiter;

        return text;
    }
}
