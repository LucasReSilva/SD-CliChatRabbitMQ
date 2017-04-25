/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.lucasresilva.clichatrabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @authors
 * Dimitri Carvalho Menezes
 * Gilliard De Jesus Santo
 * Keomas Silva Santos
 * Lucas Renato Aragão Silva
 * 
 */
public class Receiver implements Runnable {

    private static Channel cn;
    private static String queueName;
    private static String destinatario;
    private static char statusDestinatario;

    public Receiver(Channel channel, String queueName, String destinatario, char statusDestinatario) {
        cn = channel;
        Receiver.queueName = queueName;
        Receiver.destinatario = destinatario;
        Receiver.statusDestinatario = statusDestinatario;
    }

    @Override
    public void run() {

        Consumer consumer = new DefaultConsumer(cn) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {

                String message = new String(body, StandardCharsets.UTF_8);

                JSONObject my_obj = new JSONObject(message);

                boolean show = true;

                switch (my_obj.get("sender").toString().split("/").length) {
                    case 1:
                        if (my_obj.get("sender").toString().split("/")[0].equals(queueName)) {

                            show = false;

                        }
                        break;
                    case 2:
                        if (my_obj.get("sender").toString().split("/")[1].equals(queueName)) {

                            show = false;

                        }
                        break;
                }

                if (show) {

                    if (!ChatCli.ANTERIOR.equals(">> ")) {
                        System.out.println();
                    }

                    System.out.println("(" + my_obj.getString("date") + " ás " + my_obj.get("hora") + ") " + my_obj.get("sender") + " diz: " + my_obj.get("content"));

                    if (!ChatCli.ANTERIOR.equals(">> ")) {
                        System.out.print(ChatCli.ANTERIOR + ">> ");
                    }

                }

            }
        };
        try {
            cn.basicConsume(queueName, true, consumer);
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
