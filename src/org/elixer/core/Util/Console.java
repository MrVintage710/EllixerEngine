package org.elixer.core.Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by aweso on 2/13/2017.
 */
public class Console {

    public static void printerr(String message) {
        System.err.println(getDate() + "| " + message);
    }

    public static void printerr(Object message) {
        System.err.println(getDate() + "| " + message.toString());
    }

    public static void printend(String message) {
        System.err.println(getDate() + "| " + message);
        System.exit(1);
    }

    public static void println(String message) {
        System.out.println(getDate() + "| " + message);
    }

    public static void println(Object message) {
        System.out.println(getDate() + "| " + message.toString());
    }

    private static String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
