package org.example;

import java.io.IOException;

public interface Readable {
    String readFile(String name) throws IOException;
}