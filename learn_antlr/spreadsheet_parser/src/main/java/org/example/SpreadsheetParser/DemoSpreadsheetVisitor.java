package org.example.SpreadsheetParser;

import org.example.SpreadsheetParser.SpreadsheetBaseVisitor;

import java.util.HashMap;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class DemoSpreadsheetVisitor extends SpreadsheetBaseVisitor<Double> {
    private HashMap<String, Double> data = new HashMap<String, Double>();

    public void setData(String pos, Double value) {
        data.put(pos, value);
    }

    @Override 
    public Double visitNumericAtomExp(SpreadsheetParser.NumericAtomExpContext context) {
        return Double.parseDouble(context.NUMBER().getText());
    }

    @Override 
    public Double visitIdAtomExp(SpreadsheetParser.IdAtomExpContext context) {
        String id = context.ID().getText();

        return data.get(id);
    }

    @Override 
    public Double visitParenthesisExp(SpreadsheetParser.ParenthesisExpContext context) {
        return visit(context.expression());            
    }

    @Override
    public Double visitMulDivExp(SpreadsheetParser.MulDivExpContext context)
    {            
        Double left = visit(context.expression(0));
        Double right = visit(context.expression(1));
        Double result = 0.0;

        if (context.ASTERISK() != null) {
            result = left * right;
        }

        if (context.SLASH() != null) {
            result = left / right;
        }

        return result;
    }

    @Override
    public Double visitAddSubExp(SpreadsheetParser.AddSubExpContext context)
    {
        Double left = visit(context.expression(0));
        Double right = visit(context.expression(1));
        Double result = 0.0;

        if (context.PLUS() != null) {
            result = left + right;
        }

        if (context.MINUS() != null) {
            result = left - right;
        }

        return result;
    }

    @Override
    public Double visitPowerExp(SpreadsheetParser.PowerExpContext context)
    {
        Double left = visit(context.expression(0));
        Double right = visit(context.expression(1));
        Double result = 0.0;
        
        result = Math.pow(left, right);            

        return result;
    }

    @Override
    public Double visitFunctionExp(SpreadsheetParser.FunctionExpContext context)
    {
        String name = context.NAME().getText();
        Double result = 0.0;

        switch(name)
        {
            case "sqrt":
                result = Math.sqrt(visit(context.expression()));
                break;

            case "log":
                result = Math.log10(visit(context.expression()));
                break;
        }

        return result;
    }
}
