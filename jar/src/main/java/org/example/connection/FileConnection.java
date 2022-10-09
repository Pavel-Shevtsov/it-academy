package org.example.connection;

import java.io.*;

public class FileConnection {

    public BufferedReader getReader(String fileName) throws FileNotFoundException {
        return new BufferedReader(new FileReader(fileName));
    }

    public PrintWriter getWriter(String fileName) throws IOException {
        return new PrintWriter(new FileWriter(fileName));
    }
}
