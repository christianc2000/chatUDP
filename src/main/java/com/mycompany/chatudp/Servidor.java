/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatudp;

import java.io.IOException;
import java.net.DatagramSocket;

/**
 *
 * @author Christian
 */
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    // Configuración del socket

    DatagramSocket socket;
    private Map<String, Map<String, Object>> Clientes = new HashMap<>();//key, nombre, direccion ip

    public Servidor(int puerto) {
        try {
            this.socket = new DatagramSocket(puerto);
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iniciar() {

        byte[] buffer = new byte[1024];

        // Lista de clientes conectados
        List<InetAddress> clientesConectados = new ArrayList<>();

        while (true) {
            // Recibir mensaje y dirección del cliente
            DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(paqueteRecibido);
                String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());

                // Obtener dirección del cliente y agregar a lista de clientes conectados
                InetAddress direccionCliente = paqueteRecibido.getAddress();
                if (!clientesConectados.contains(direccionCliente)) {
                    clientesConectados.add(direccionCliente);
                }

                // Imprimir mensaje recibido
                System.out.println("Mensaje recibido de " + direccionCliente.getHostAddress() + ": " + mensaje);

                // Enviar mensaje a todos los clientes conectados excepto al que lo envió
                for (InetAddress cliente : clientesConectados) {
                    if (!cliente.equals(direccionCliente)) {
                        DatagramPacket paqueteEnviado = new DatagramPacket(buffer, buffer.length, cliente, 5005);
                        socket.send(paqueteEnviado);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
