package org.example.hello.world.common.utils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
    
        public static void closeQuietly(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
            } catch (IOException ignore) {
                // nothing need to do
            }
        }
    }
}
