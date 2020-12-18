package org.example.ChatParser;

import org.example.ChatParser.ChatParser.ChatContext;

public class HtmlChatListener extends ChatBaseListener {
    StringBuilder htmlText = new StringBuilder();

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
            htmlText.append("🙂");        
        }
    
        if(emoticon.equals(":-(") || emoticon.equals(":(")) {
            htmlText.append("🙁");            
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
    public void exitCommand(ChatParser.CommandContext ctx) {
        htmlText.append("</p>");
    }
}
