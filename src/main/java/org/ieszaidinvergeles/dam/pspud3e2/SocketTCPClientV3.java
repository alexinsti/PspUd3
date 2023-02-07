/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.ieszaidinvergeles.dam.pspud3e2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTCPClientV3 {

    private String serverIP;
    private int serverPort;
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    private InputStreamReader isr;
    private BufferedReader br;
    private PrintWriter pw;

    public SocketTCPClientV3(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public void start() throws UnknownHostException, IOException {
        System.out.println("(Cliente) Estableciendo conexión...");
        socket = new Socket(serverIP, serverPort);
        os = socket.getOutputStream();
        is = socket.getInputStream();
        System.out.println("(Cliente) Conexión establecida.");
    }

    public void stop() throws IOException {
        System.out.println("(Cliente) Cerrando conexiones.");
        is.close();
        os.close();
        socket.close();
        System.out.println(" (Cliente) Conexiones cerradas.");
    }

    public void abrirCanalesDeTexto() {
        System.out.println(" (Cliente) Abriendo canales de texto...");
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
        pw = new PrintWriter(os, true);
        System.out.println("(Cliente) Cerrando canales de texto.");
    }

    public void cerrarCanalesDeTexto() throws IOException {
        System.out.println(" (Cliente) Cerrando canales de texto...");
        br.close();
        isr.close();
        pw.close();
        System.out.println("(Cliente) Cerrando canales de texto.");
    }

    public String leerMensajeTexto() throws IOException {
        System.out.println(" (Cliente) Leyendo mensaje...");
        String mensaje = br.readLine();
        System.out.println(" (Cliente) Mensaje leido.");
        return mensaje;
    }

    public void enviarMensajeTexto(String mensaje) {
        System.out.println(" (Cliente) Enviando mensaje...");
        pw.println(mensaje);
        System.out.println(" (Cliente) Mensaje enviado.");
    }

    public static void main(String[] args) {
        SocketTCPClientV3 cliente = new SocketTCPClientV3("localhost", 49171);
        try {
            cliente.start();
            cliente.abrirCanalesDeTexto();

            cliente.enviarMensajeTexto("Hola");

            String mensajeRecibido = cliente.leerMensajeTexto();
            System.out.println("Mensaje del servidor:" + mensajeRecibido);

            cliente.cerrarCanalesDeTexto();
            cliente.stop();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
