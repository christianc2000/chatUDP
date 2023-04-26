/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.chatudp;

/**
 *
 * @author Christian
 */
public class ChatUDP {

    public static void main(String[] args) {
        //System.out.println("Hello World!");
        Servidor server=new Servidor(5005);
        server.iniciar();
    }
}
