package org.example;

import org.example.ChatParser.ChatLexer;
import org.example.ChatParser.ChatParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class AppChatParser
{
    static void printTokens(String text) {
        CharStream inputStream = CharStreams.fromString(text);
        ChatLexer ChatLexer = new ChatLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(ChatLexer);
        ChatParser chatParser = new ChatParser(commonTokenStream);
        chatParser.chat();
        for (Token token : commonTokenStream.getTokens()) {
            Vocabulary vocabulary = chatParser.getVocabulary();
            String tokenName = vocabulary.getDisplayName(token.getType());
            System.out.println("----------------------------------------------");
            System.out.println("token index: " + token.getTokenIndex());
            System.out.println("token pos:   [" + token.getStartIndex() + ", " + token.getStopIndex() + "]");
            System.out.println("token text:  \"" + token.getText() + "\"");
            System.out.println("token name:  " + tokenName);
        }
    }

    public static void main( String[] args )
    {
        //String text = "john SHOUTS: hello @michael /pink/this will work/ :-) \n";
        String text = "john SHOUTS: hello \n";
        printTokens(text);
    }
}
