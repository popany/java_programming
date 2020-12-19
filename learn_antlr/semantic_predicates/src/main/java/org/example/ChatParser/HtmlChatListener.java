package org.example.ChatParser;

import org.example.ChatParser.ChatParser;
import org.example.ChatParser.ChatParser.ChatContext;
import java.util.HashMap;

public class HtmlChatListener extends ChatBaseListener {
    StringBuilder htmlText = new StringBuilder();
    HashMap<Object, String> ctxText = new HashMap<Object, String>();

    void resetHtmlText() {
        htmlText.delete(0, htmlText.length());
        htmlText.append("<html><head><meta charset=\"UTF-8\"/></head><body>");
    }

    public String getHtmlText() {
        return htmlText.toString();
    }

    @Override
    public void enterChat(ChatContext ctx) {
        resetHtmlText();
    }

    @Override
    public void exitChat(ChatContext ctx) {
        htmlText.append("</body></html>");
    }

    @Override
    public void enterName(ChatParser.NameContext ctx) {
        htmlText.append("<Strong>");
    }

    @Override
    public void exitName(ChatParser.NameContext ctx) {
        htmlText.append(ctx.WORD().getText());
        htmlText.append("</Strong> ");
    }
    
    @Override
    public void exitEmoticon(ChatParser.EmoticonContext ctx) {
        String emoticon = ctx.getText();

        if(emoticon.equals(":-)") || emoticon.equals(":)")) {
            ctxText.put(ctx, "🙂");        
        }
    
        if(emoticon.equals(":-(") || emoticon.equals(":(")) {
            ctxText.put(ctx, "🙁");            
        }
    }

    @Override
    public void enterCommand(ChatParser.CommandContext ctx) {
        if(ctx.SAYS() != null) {
            htmlText.append(ctx.SAYS().getText() + ":<p>");
        } else if(ctx.SHOUTS() != null) {
            htmlText.append(ctx.SHOUTS().getText() + ":<p style=\"text-transform: uppercase\">");
        }
    }

    @Override
    public void exitColor(ChatParser.ColorContext ctx) {
        StringBuilder text = new StringBuilder();
        String color = ctx.WORD().getText();
        text.append("<span style=\"color: " + color + "\">");
        text.append(ctxText.get(ctx.message()));
        text.append("</span>");
        ctxText.put(ctx, text.toString());
    }

    @Override
    public void enterLink(ChatParser.LinkContext ctx) {
        String text = String.format("<a href=\"%s\">%s</a>", ctx.TEXT().get(1), ctx.TEXT().get(0));
        ctxText.put(ctx, text);
    } 

    @Override
    public void exitMessage(ChatParser.MessageContext ctx) {
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctxText.containsKey(ctx.getChild(i))) {
                text.append(ctxText.get(ctx.getChild(i)));
            } else {
                text.append(ctx.getChild(i).getText());
            }
        }

        if (ctx.getParent() instanceof ChatParser.LineContext) {
            htmlText.append(text.toString());
            htmlText.append("</p>");
        } else {
            ctxText.put(ctx, text.toString());
        }
    }
}
