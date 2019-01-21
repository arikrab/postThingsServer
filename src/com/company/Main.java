package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class Main {

    //two maps one for user management and one for item mangement


    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        System.out.println("chack");

        try {
            serverSocket = new ServerSocket(3001);
            while (true) {

                Socket socket = serverSocket.accept();
                new ClientThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

