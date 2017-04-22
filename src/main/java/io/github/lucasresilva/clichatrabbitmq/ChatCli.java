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

        // Instancia conexão
        ServerConnection sc = new ServerConnection();

        // Instancia Scanner         
        Scanner input = new Scanner(System.in);

        System.out.print("User: ");
        String user = input.nextLine();

        sc.createUserQueue(user);

        String message;

        do {
            // Pega proxami entrada de texto
            System.out.print(">> ");
            message = input.nextLine();

            // Verifica se algum comando foi digitado
            switch (message.split(" ")[0]) {

                // Comando Help
                case "!help":
                    break;

                // Criar um grupo
                case "!addGroup":

                    sc.crateGroup(user, message.split(" ")[1]);
                    break;

                // Deletar grupo
                case "!delGroup":

                    sc.deleteGroup(message.split(" ")[1]);
                    break;

                // Adicionar usuario a um grupo
                case "!addUserToGroup":

                    sc.addUserToGroup(message.split(" ")[1], message.split(" ")[2]);
                    break;
                    
                // Remove usuario de um grupo
                case "!delFromGroup":

                   sc.removeUserFromGroup(message.split(" ")[1], message.split(" ")[2]);
                    break;

                case "!exit":
                    break;

                default:
                    break;
            }

        } while (!message.equals("!exit"));

        System.out.println("Hasta la vista, baby");

    }

}
