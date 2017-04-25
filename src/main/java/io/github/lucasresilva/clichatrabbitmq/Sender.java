/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.lucasresilva.clichatrabbitmq;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.GregorianCalendar;
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
public class Sender {

    private static JSONObject jsonObj = new JSONObject();

    private static GregorianCalendar calendar = new GregorianCalendar();
    private static int day, month, year, hour, minute;
    private static String date, hours;

    private static String formatMsg(String message, String user, String group) {

        day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        month = calendar.get(GregorianCalendar.MONTH);
        year = calendar.get(GregorianCalendar.YEAR);
        hour = calendar.get(GregorianCalendar.HOUR_OF_DAY);
        minute = calendar.get(GregorianCalendar.MINUTE);

        date = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
        hours = Integer.toString(hour) + ":" + Integer.toString(minute);

        jsonObj.put("sender", group + user);
        jsonObj.put("date", date);
        jsonObj.put("hora", hours);
        jsonObj.put("content", message);

        return jsonObj.toString();
    }

    public static void sendToUser(Channel channel, String sender, String destination, String message) throws UnsupportedEncodingException, IOException {
        String msg = formatMsg(message, sender, "");
        channel.basicPublish("", destination, null, msg.getBytes(StandardCharsets.UTF_8));
    }

    public static void sendToGroup(Channel channel, String sender, String group, String message) throws UnsupportedEncodingException, IOException {
        String msg = formatMsg(message, sender, group + "/");
        channel.basicPublish(group, "", null, msg.getBytes(StandardCharsets.UTF_8));
    }

}
