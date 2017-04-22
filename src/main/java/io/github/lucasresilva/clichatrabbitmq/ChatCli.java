/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.lucasresilva.clichatrabbitmq;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author lucas
 */
public class ChatCli {

    static public void main(String args[]) throws IOException, TimeoutException {

        ServerConnection sc = new ServerConnection();

        /* FACTORY.setHost(SERVERS.getServer(0).HOST);
        FACTORY.setUsername(SERVERS.getServer(0).USER);
        FACTORY.setVirtualHost(SERVERS.getServer(0).USER);
        FACTORY.setPassword(SERVERS.getServer(0).PASSWORD);
        

        Connection CONNECTION = FACTORY.newConnection(SERVERS.getServersAddress());
        Channel CHANNEL = CONNECTION.createChannel();
        
        System.out.println("io.github.lucasresilva.clichatrabbitmq.ChatCli.main()"); */
        Scanner input = new Scanner(System.in);

        System.out.print("User: ");
        String user = input.nextLine();

        String message;

        do {
            System.out.print(">> ");
            message = input.nextLine();

        } while (!message.trim().equals("!exit"));

        System.out.println("Hasta la vista, baby");

    }

}
