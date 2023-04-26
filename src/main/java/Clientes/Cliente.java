/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clientes;

/**
 *
 * @author Christian
 */
import java.net.*;
import java.util.Scanner;
public class Cliente {

    public static void main(String args[]) throws Exception {
        // Configuración del socket
        DatagramSocket socket = new DatagramSocket();
        InetAddress direccionServidor = InetAddress.getByName("localhost");
        byte[] buffer = new byte[1024];

        // Obtener nombre de usuario del cliente
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce tu nombre de usuario: ");
        String nombreUsuario = sc.nextLine();

        // Enviar mensaje de bienvenida al servidor
        String mensajeBienvenida = "¡Bienvenido, " + nombreUsuario + "!";
        DatagramPacket paqueteBienvenida = new DatagramPacket(mensajeBienvenida.getBytes(), mensajeBienvenida.length(), direccionServidor, 5005);
        socket.send(paqueteBienvenida);

        // Recibir mensajes del servidor y otros clientes
        Thread hiloEscucha = new Thread(() -> {
            while (true) {
                try {
                    DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
                    socket.receive(paqueteRecibido);
                    String mensajeRecibido = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                    // Imprimir mensaje recibido
                    System.out.println(mensajeRecibido);
                } catch (Exception e) {
                    System.err.println("Error al recibir mensaje: " + e.getMessage());
                }
            }
        });
        hiloEscucha.start();

        // Enviar mensajes al servidor
        while (true) {
            String mensaje = sc.nextLine();
            mensaje = nombreUsuario + ": " + mensaje;
            DatagramPacket paqueteEnviado = new DatagramPacket(mensaje.getBytes(), mensaje.length(), direccionServidor, 5005);
            socket.send(paqueteEnviado);
        }
    }

}
