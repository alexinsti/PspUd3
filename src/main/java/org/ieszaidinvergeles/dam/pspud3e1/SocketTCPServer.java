/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.ieszaidinvergeles.dam.pspud3e1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTCPServer {

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    public SocketTCPServer(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
    }

    public void start() throws IOException {
        System.out.println(" (Servidor) Esperando conexiones...");
        socket = serverSocket.accept();
        is = socket.getInputStream();
        os = socket.getOutputStream();
        System.out.println(" (Servidor) Conexión establecida.");
    }

    public void stop() throws IOException {
        System.out.println(" (Servidor) Cerrando conexiones...");
        is.close();
        os.close();
        socket.close();
        serverSocket.close();
        System.out.println(" (Servidor) Conexiones cerradas.");
    }

    public static void main(String[] args) {
        try {
            SocketTCPServer servidor = new SocketTCPServer(49171);
            servidor.start();
            System.out.println("Mensaje del cliente: " + servidor.is.read());
            servidor.os.write(200);
            servidor.stop();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
