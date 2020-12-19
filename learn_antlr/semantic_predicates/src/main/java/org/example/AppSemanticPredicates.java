package org.example;

import org.example.ChatParser.ChatLexer;
import org.example.ChatParser.ChatParser;
import org.example.ChatParser.HtmlChatListener;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppSemanticPredicates
{
    static void writeHtml(String text) {
        CharStream inputStream = CharStreams.fromString(text);
        ChatLexer ChatLexer = new ChatLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(ChatLexer);
        ChatParser chatParser = new ChatParser(commonTokenStream);
        HtmlChatListener htmlChatListener = new HtmlChatListener();
        ParseTree parseTree = chatParser.chat();
        ParseTreeWalker.DEFAULT.walk(htmlChatListener, parseTree);

        try {
            File file = new File("./1.html");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(htmlChatListener.getHtmlText());
            System.out.println(htmlChatListener.getHtmlText());
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String text = "john SHOUTS: hello @michael /pink/this will work/ :-) [The ANTLR Mega Tutorial](https://tomassetti.me/antlr-mega-tutorial/) \n";
        System.out.println("\n\n<writeHtml>================================================");
        writeHtml(text);
    }
}
