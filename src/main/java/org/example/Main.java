package org.example;


import org.example.cron.CronExpression;

public class Main {
    public static void main(String[] args) {
        String in = args[0];
        var ob = new CronExpression(in);
        ob.prettyPrint();
    }
}