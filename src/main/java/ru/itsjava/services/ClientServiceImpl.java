package ru.itsjava.services;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientServiceImpl implements ClientService {
    public static final int PORT = 8081;
    public static final String HOST = "localhost";

    @SneakyThrows
    @Override
    public void start() {
        Socket socket = new Socket(HOST, PORT);

        if (socket.isConnected()) {
            new Thread(new SocketRunnable(socket)).start();

            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream());

            MessageInputService messageInputService = new MessageInputServiceImpl(System.in);

            System.out.println("Введите свой логин:");
            String login = messageInputService.getMessage();

            System.out.println("Введите свой пароль:");
            String password = messageInputService.getMessage();

            //!autho!login:password
            serverWriter.println("!autho!" + login + ":" + password);
            serverWriter.flush();

//            while (!messageInputService.getMessage().equalsIgnoreCase("exit")) {
            while (true) {
                //System.out.println("Введите сообщение");
                String consoleMessage = messageInputService.getMessage();

                serverWriter.println(consoleMessage);
                serverWriter.flush();

                if (consoleMessage.equalsIgnoreCase("exit")) {
//                    break;
                    System.exit(0);
                }
            }
        }
    }
}
