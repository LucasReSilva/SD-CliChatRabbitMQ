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

    public static String ANTERIOR = ">> ";

    static public void main(String args[]) throws IOException, TimeoutException, InterruptedException {

        // Instancia conexão
        ServerConnection sc = new ServerConnection();

        // Instancia Scanner         
        Scanner input = new Scanner(System.in);

        System.out.print("User: ");
        String user = input.nextLine();

        sc.createUserQueue(user);

        String message;

        // Seta o status do destinatario I(invalido) G(Grupo) P(Privado)
        char statusDestinatario = 'I';

        // Armazena nome do usuario
        String destinatario = "";

        // Recebendo msg
        Receiver receiver = new Receiver(sc.getChannel(), user, destinatario, statusDestinatario);
        Thread receiving = new Thread(receiver);
        receiving.start();

        Thread.sleep(250);

        do {
            // Pega proxima entrada de texto
            switch (statusDestinatario) {
                case 'G':
                    System.out.print(destinatario + " (grupo)>> ");
                    break;

                case 'P':
                    System.out.print(destinatario + ">> ");
                    break;
                default:
                    if (ANTERIOR.equals(">> ")) { 
                        System.out.print(">> ");
                    }

            }
            message = input.nextLine();

            // Verifica se algum comando foi digitado
            switch (message.split(" ")[0]) {

                // Comando Help
                case "!help":
                    break;

                // Criar um grupo
                case "!addGroup":

                    sc.createGroup(user, message.split(" ")[1]);
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

                // Finaliza o chat
                case "!exit":
                    break;

                default:

                    // Seta destinatario
                    if (message.length() > 2 && "@".equals(message.substring(0, 1))) {

                        // Verifica se destinatario é usuario ou grupo
                        if (message.length() > 3 && "@@".equals(message.substring(0, 2))) {

                            statusDestinatario = 'G';
                            destinatario = message.substring(2, message.length());
                            sc.queueDeclare(destinatario);
                            ANTERIOR = destinatario + " (grupo)"; 
                        } else {
                            statusDestinatario = 'P';
                            destinatario = message.substring(1, message.length());
                            sc.queueDeclare(destinatario);
                            ANTERIOR = destinatario; 
                        }

                    } else if (statusDestinatario != 'I') {

                        if (statusDestinatario == 'P') {
                            Sender.sendToUser(sc.getChannel(), user, destinatario, message);
                        } else {
                            Sender.sendToGroup(sc.getChannel(), user, destinatario, message);
                        }

                    } else {
                        statusDestinatario = 'I';
                        destinatario = "";
                        System.out.println("SEM DESTINATARIO!!!");
                    }

                    break;
            }

        } while (!message.equals("!exit"));

        System.out.println("Hasta la vista, baby");
        sc.close();

    }

}
