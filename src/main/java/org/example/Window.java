package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Window extends JFrame {
    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_POSX = 500;
    private static final int WINDOW_POSY = 50;
    JTextField jTextFieldLogin = new JTextField("Login");
    JTextField jTextFieldPassword = new JTextField("Password");
    JTextField jTextFieldIp = new JTextField("IP address");
    JTextField jTextFieldPort = new JTextField("Port");
    JButton jButtonLogin = new JButton("Login");
    JPanel jPanel1 = new JPanel(new GridLayout(2, 2, 3, 0));
    JPanel jPanel2 = new JPanel(new BorderLayout());
    JTextArea jTextArea = new JTextArea();
    JTextField jTextFieldMessage = new JTextField("Message");
    JButton jButtonSend = new JButton("Send");
    Service service = new Service();

    Window() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setTitle("Chat Client");

        jPanel1.add(jTextFieldIp);
        jPanel1.add(jTextFieldPort);
        jPanel1.add(jTextFieldLogin);
        jPanel1.add(jTextFieldPassword);
        jPanel1.add(jButtonLogin);
        add(jPanel1, BorderLayout.NORTH);

        jPanel2.add(jTextFieldMessage, BorderLayout.CENTER);
        jPanel2.add(jButtonSend, BorderLayout.EAST);
        add(jPanel2, BorderLayout.SOUTH);

        jTextArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        add(jScrollPane);
        setVisible(true);

        //при нажатии Enter фокусируется следующее окно
        jTextFieldIp.addActionListener(actionEvent -> jTextFieldPort.requestFocusInWindow());
        //при нажатии Enter фокусируется следующее окно
        jTextFieldPort.addActionListener(actionEvent -> jTextFieldLogin.requestFocusInWindow());
        //при нажатии Enter фокусируется следующее окно
        jTextFieldLogin.addActionListener(actionEvent -> jTextFieldPassword.requestFocusInWindow());
        //при нажатии Enter фокусируется следующее окно
        jTextFieldPassword.addActionListener(actionEvent -> jButtonLogin.requestFocusInWindow());
        //слушатель кнопки логин на нажатие мышкой
        jButtonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isConnect()) {
                    readFileToApp();
                } else {
                    jTextArea.setText("");
                    jTextArea.append("Установите соединение с сервером!");
                }
            }
        });
        //слушатель текстового поля сообщение на нажатие Enter
        jTextFieldMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isConnect()) {
                    writeFileAndJTextArea();
                } else {
                    jTextArea.setText("");
                    jTextArea.append("Установите соединение с сервером!");
                }
            }
        });
        //слушатель кнопки Send на нажатие мышкой
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isConnect()) {
                    writeFileAndJTextArea();
                } else {
                    jTextArea.setText("");
                    jTextArea.append("Установите соединение с сервером!");
                }
            }
        });
    }
    //метод проверки соединения с сервером с конкретными данными
    public boolean isConnect() {
        return jTextFieldIp.getText().equals("10.10.10.10") &&
                jTextFieldPort.getText().equals("1010") &&
                jTextFieldLogin.getText().equals("Admin") &&
                jTextFieldPassword.getText().equals("admin");
    }
    //метод записи сообщения в файл и окно лога
    private void writeFileAndJTextArea() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            service.write(formatter.format(date) + " " + jTextFieldLogin.getText() + ": " +
                    jTextFieldMessage.getText(), "fileDB.txt");
            jTextArea.append(formatter.format(date) + " " + jTextFieldLogin.getText() + ": " +
                    jTextFieldMessage.getText() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //метод чтения из файла с выводом в окно лога
    private void readFileToApp() {
        try {
//            System.out.println(service.readFile("fileDB.txt"));
            jTextArea.setText(service.readFile("fileDB.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

