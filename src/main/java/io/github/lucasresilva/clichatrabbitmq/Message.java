/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.lucasresilva.clichatrabbitmq;

/**
 *
 * @author lucas
 */
public class Message {
    
    String sender, date, content;

    public Message(String sender, String date, String content) {
        this.sender = sender;
        this.date = date;
        this.content = content;
    } 
    
}
