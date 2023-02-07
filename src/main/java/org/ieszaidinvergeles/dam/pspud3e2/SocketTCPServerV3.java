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
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class SocketTCPServerV3 {

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private InputStreamReader isr;
    private BufferedReader br;
    private PrintWriter pw;

    public SocketTCPServerV3(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
    }

    public void start() throws IOException {
        System.out.println(" (Servidor) Esperando conexiones...");
        socket = serverSocket.accept();
        is = socket.getInputStream();
        os = socket.getOutputStream();
        System.out.println(" (Servidor) Conexión establecida con cliente " + socket.getRemoteSocketAddress());
    }

    public void stop() throws IOException {
        System.out.println(" (Servidor) Cerrando conexiones...");
        is.close();
        os.close();
        socket.close();
        serverSocket.close();
        System.out.println(" (Servidor) Conexiones cerradas.");
    }

    public void abrirCanalesDeTexto() {
        System.out.println(" (Servidor) Abriendo canales de texto...");
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
        pw = new PrintWriter(os, true);
        System.out.println("(Servidor) Cerrando canales de texto.");
    }

    public void cerrarCanalesDeTexto() throws IOException {
        System.out.println(" (Servidor) Cerrando canales de texto...");
        br.close();
        isr.close();
        pw.close();
        System.out.println("(Servidor) Cerrando canales de texto.");
    }

    public String leerMensajeTexto() throws IOException {
        System.out.println(" (Servidor) Leyendo mensaje...");
        String mensaje = br.readLine();
        System.out.println(" (Servidor) Mensaje leido.");
        return mensaje;
    }

    public void enviarMensajeTexto(String mensaje) {
        System.out.println(" (Servidor) Enviando mensaje...");
        pw.println(mensaje);
        System.out.println(" (Servidor) Mensaje enviado.");
    }

    public static void main(String[] args) {
        try {
            SocketTCPServerV3 servidor = new SocketTCPServerV3(49171);
            servidor.start();
            servidor.abrirCanalesDeTexto();

            String mensajeRecibido = servidor.leerMensajeTexto();
            System.out.println("(Servidor) Mensaje del cliente: " + mensajeRecibido);
            LocalDateTime horaLocal = LocalDateTime.now();
            int horas = horaLocal.getHour();
            int minutos = horaLocal.getMinute();
            int segundos = horaLocal.getSecond();
            servidor.enviarMensajeTexto("ACK " + horas + ":" + minutos + ":" + segundos);

            servidor.cerrarCanalesDeTexto();
            servidor.stop();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
