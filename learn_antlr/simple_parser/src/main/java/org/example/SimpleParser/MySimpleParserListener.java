package org.example.SimpleParser;

public class MySimpleParserListener extends SimpleParserBaseListener {
    @Override
    public void enterOperation(SimpleParserParser.OperationContext ctx) {
        System.out.println(ctx.getText());
    }
}
