package ru.itsjava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {
    public static final int PORT = 8081;
    public static final String HOST = "localhost";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOST, PORT);

        if (socket.isConnected()){
            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream());
            serverWriter.println("Hi from client!");
            serverWriter.flush();

            BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true){
                System.out.println(serverReader.readLine());
            }
        }

    }
}
