/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.lucasresilva.clichatrabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author lucas
 */
public class ServerConnection {

    static ConnectionFactory FACTORY;

    static Connection CONN;

    static Channel CHANNEL;

    public ServerConnection() throws IOException, TimeoutException {
        // Inicializando FACTORY
        ServerConnection.FACTORY = new ConnectionFactory();

        // Stenado propriedades da FACTORY
        ServerConnection.FACTORY.setUsername("userName");
        ServerConnection.FACTORY.setPassword("password");
        ServerConnection.FACTORY.setVirtualHost("virtualHost");
        ServerConnection.FACTORY.setHost("hostName");
        ServerConnection.FACTORY.setPort(5672);

        ServerConnection.CONN = ServerConnection.FACTORY.newConnection();
        ServerConnection.CHANNEL = ServerConnection.CONN.createChannel();
    }

}
