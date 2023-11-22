package org.example;

import java.io.IOException;

public interface Writable {
    void write(Object o,String name) throws RuntimeException, IOException;
}

