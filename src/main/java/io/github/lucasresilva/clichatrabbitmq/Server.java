/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.lucasresilva.clichatrabbitmq;

import java.nio.charset.Charset;

/**
 *
 * @author lucas
 */
public class Server {
    
    public final String HOST, USER, PASSWORD, URL;
    public final int PORT;
    public final Charset CHARSET;    

    public Server(String host, String user, String password, String url, int port, Charset charset) {
        this.HOST = host;
        this.USER = user;
        this.PASSWORD = password;
        this.URL = url;
        this.PORT = port;
        this.CHARSET = charset;
    }
    
    
    
    
}
