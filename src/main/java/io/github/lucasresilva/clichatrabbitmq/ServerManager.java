/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.lucasresilva.clichatrabbitmq;

import com.rabbitmq.client.Address;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public class ServerManager {

    private static final ArrayList<Server> SERVERLIST = new ArrayList<Server>();

    public ServerManager() {
        
        // Dimitri's Servers
        ServerManager.SERVERLIST.add(
                new Server("salamander.rmq.cloudamqp.com",
                        "cspimzij",
                        "lRoXHJYwBXo0ImeBAUQr27Smdbti-TG_",
                        "amqp://cspimzij:lRoXHJYwBXo0ImeBAUQr27Smdbti-TG_@salamander.rmq.cloudamqp.com/cspimzij",
                        5672,
                        StandardCharsets.UTF_8)
        );
    }

    public static Server getServer(int i) {
        return ServerManager.SERVERLIST.get(i);
    }

    public static ArrayList<Address> getServersAddress() {
        ArrayList<Address> servers = new ArrayList<>();
        for (int i = 0; i < ServerManager.SERVERLIST.size(); i++) {
            servers.add(new Address(ServerManager.SERVERLIST.get(i).HOST, ServerManager.SERVERLIST.get(i).PORT));
        }
        return servers;
    }

}
