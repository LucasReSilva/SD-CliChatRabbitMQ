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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author lucas
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

                String message = new String(body, "UTF-8");

                JSONObject my_obj = new JSONObject(message);
                System.out.println();
                System.out.print("(" + my_obj.getString("date") + " ás " + my_obj.get("hora") + ") " + my_obj.get("sender") + " diz: " + my_obj.get("content"));

                /* switch (statusDestinatario) {
                    case 'G':
                        System.out.print(destinatario + " (grupo)>> ");
                        break;

                    case 'P':
                        System.out.print(destinatario + ">> ");
                        break;
                    default:
                        System.out.print(">> ");

                } */
            }
        };
        try {
            cn.basicConsume(queueName, true, consumer);
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
