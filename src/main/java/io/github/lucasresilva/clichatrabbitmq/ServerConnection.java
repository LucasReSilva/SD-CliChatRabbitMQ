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

    public static Channel CHANNEL;

    public ServerConnection() throws IOException, TimeoutException {

        // Inicializando FACTORY
        ServerConnection.FACTORY = new ConnectionFactory();

        // Stenado propriedades da FACTORY
        ServerConnection.FACTORY.setUsername("cspimzij");
        ServerConnection.FACTORY.setPassword("lRoXHJYwBXo0ImeBAUQr27Smdbti-TG_");
        ServerConnection.FACTORY.setVirtualHost("cspimzij");
        ServerConnection.FACTORY.setHost("salamander.rmq.cloudamqp.com");
        ServerConnection.FACTORY.setPort(5672);

        // Inicializa a Conexao e o Canal
        ServerConnection.CONN = ServerConnection.FACTORY.newConnection();
        ServerConnection.CHANNEL = ServerConnection.CONN.createChannel();
    }

    public Channel getChannel() {
        return CHANNEL;
    }

    public void queueDeclare(String queueName) throws IOException {
        CHANNEL.queueDeclare(queueName, false, false, false, null);
    }

    public void createUserQueue(String userName) throws IOException {
        ServerConnection.CHANNEL.queueDeclare(userName, false, false, false, null);
    }

    public void crateGroup(String userName, String groupName) throws IOException {
        CHANNEL.exchangeDeclare(groupName, "fanout");
        CHANNEL.queueBind(userName, groupName, "");
        System.out.println(userName.toUpperCase() + " criou o grupo " + groupName.toUpperCase());
    }

    public void deleteGroup(String groupName) throws IOException {
        CHANNEL.exchangeDelete(groupName);
        System.out.println("Grupo " + groupName.toUpperCase() + " foi detelado");
    }

    public void addUserToGroup(String userName, String groupName) throws IOException {
        CHANNEL.queueBind(userName, groupName, "");
        System.out.println(userName.toUpperCase() + " foi adicionado ao grupo " + groupName.toUpperCase());
    }

    public void removeUserFromGroup(String userName, String groupName) throws IOException {
        CHANNEL.queueUnbind(userName, groupName, "");
        System.out.println(userName.toUpperCase() + " foi removido do grupo " + groupName.toUpperCase());
    }

    public void close() throws IOException, TimeoutException {
        CHANNEL.close();
        CONN.close();
    }

}
