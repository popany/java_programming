package org.example;

import org.springframework.beans.factory.annotation.Value;

public class Bar {
    @Value("bar")
    String info;

    public String getInfo() {
        return info;
    }
}
