package ru.itsjava.services;

import lombok.SneakyThrows;

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

            printMenu();
            System.out.println("Введите номер меню");
            int numMenu = Integer.parseInt(messageInputService.getMessage());

            if (numMenu == 1) {
                System.out.println("Авторизация");
                System.out.println("Введите свой логин:");
                String login = messageInputService.getMessage();

                System.out.println("Введите свой пароль:");
                String password = messageInputService.getMessage();

                //!autho!login:password
                serverWriter.println("!autho!" + login + ":" + password);
                serverWriter.flush();
            } else if (numMenu == 2) {
                System.out.println("Регистрация");
                System.out.println("Введите логин:");
                String login = messageInputService.getMessage();

                System.out.println("Введите пароль:");
                String password = messageInputService.getMessage();
                //!reg!login:password
                serverWriter.println("!reg!" + login + ":" + password);
                serverWriter.flush();
            }

            while (true) {
                //System.out.println("Введите сообщение");
                String consoleMessage = messageInputService.getMessage();

                serverWriter.println(consoleMessage);
                serverWriter.flush();

                if (consoleMessage.equalsIgnoreCase("exit")) {
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public void printMenu() {
        System.out.println("1 - Авторизация; 2 - Регистрация");
    }
}
