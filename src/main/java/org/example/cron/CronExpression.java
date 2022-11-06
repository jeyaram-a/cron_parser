package org.example.cron;

import org.example.cron.components.*;
import org.example.cron.components.exception.ParseException;

import java.util.List;
import java.util.Map;

public class CronExpression {

    private final MinuteComponent minuteComponent;
    private final HourComponent hourComponent;
    private final DayOfMonthComponent domComponent;
    private final MonthComponent monthComponent;
    private final DayOfWeekComponent dowComponent;

    private final String command;

    public CronExpression(String in) {
        in = in.trim();
        String[] parts = in.split("\\s+");
        if(parts.length != 6) {
            throw new ParseException("Parse error: needs 6 components in order min, hour, dom, month, dow, command");
        }
        minuteComponent = new MinuteComponent(parts);
        hourComponent = new HourComponent(parts);
        monthComponent = new MonthComponent(parts);
        domComponent = new DayOfMonthComponent(parts, monthComponent.getMaxDays());
        dowComponent = new DayOfWeekComponent(parts);
        command = parts[parts.length-1].trim();
    }

    private String getDisplayString(List<String> list) {
        return String.join(" ", list);
    }
    public void prettyPrint() {
        System.out.format("%-14s %s\n", minuteComponent.getComponentName(), getDisplayString(minuteComponent.getNext()));
        System.out.format("%-14s %s\n", hourComponent.getComponentName(), getDisplayString(hourComponent.getNext()));
        System.out.format("%-14s %s\n", domComponent.getComponentName(), getDisplayString(domComponent.getNext()));
        System.out.format("%-14s %s\n", monthComponent.getComponentName(), getDisplayString(monthComponent.getNext()));
        System.out.format("%-14s %s\n", dowComponent.getComponentName(), getDisplayString(dowComponent.getNext()));
        System.out.format("%-14s %s\n", "command", command.trim());

        System.out.println();
    }

    public Map<String, List<String>> getNextAsMap() {
        return Map.of(
                minuteComponent.getComponentName(), minuteComponent.getNext(),
                hourComponent.getComponentName(), hourComponent.getNext(),
                domComponent.getComponentName(), domComponent.getNext(),
                monthComponent.getComponentName(), monthComponent.getNext(),
                dowComponent.getComponentName(), dowComponent.getNext(),
                "command", List.of(command)
        );
    }
}