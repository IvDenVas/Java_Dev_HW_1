package org.example;

import java.io.*;
public class Service implements Writable,Readable {
    Service(){
    }
    @Override
    public void write(Object o, String name) throws RuntimeException, IOException {
        FileWriter writer = new FileWriter(name, true);
        writer.write(o.toString() + "\n");
        System.out.println("Запись в файл завершена!");
        writer.close();
    }
    @Override
    public String readFile(String name) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(name));
        String line = reader.readLine();
        StringBuilder res = new StringBuilder();
        while (line != null) {
            res.append(line).append("\n");
            line = reader.readLine();
        }
        return res.toString();
    }
}
