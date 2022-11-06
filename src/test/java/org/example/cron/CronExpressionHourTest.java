package org.example.cron;

import org.example.cron.components.exception.ParseException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.example.cron.TestUtils.getRange;
import static org.junit.jupiter.api.Assertions.*;

class CronExpressionHourTest {


    @Test
    void  ShouldPassEveryWithStartForHour() {
        var cron = new CronExpression("2/15 2/2 1,15 * 1-5 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("2", "17", "32", "47"),
                "hour", getRange(2, 23, 2),
                "day of month", List.of("1", "15"),
                "month", getRange(1,12),
                "day of week", getRange(1, 5),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailEveryWithInvalidStartForHour() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("1/15 -1/2 1,15 * 1-5 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid hour component"));
    }

    @Test
    void  ShouldFailEveryWithInvalidIntervalForHour() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("9/23 0/-2 1,15 * 1-5 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid hour component"));
    }

    @Test
    void  ShouldFailEveryWithInvalidIntervalForHour1() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("9/23 0/45 1,15 * 1-5 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid hour component"));
    }

    @Test
    void  ShouldPassEveryWithBigStartForHour() {
        var cron = new CronExpression("45/15 22/12 1,15 * 1-5 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("45"),
                "hour", List.of("22"),
                "day of month", List.of("1", "15"),
                "month", getRange(1,12),
                "day of week", getRange(1, 5),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassEveryWithIntervalOfKForHour() {
        var cron = new CronExpression("*/2 1/3 * * * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(0, 59,2),
                "hour", getRange(1, 23, 3),
                "day of month", getRange(1, 31),
                "month", getRange(1,12),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }


    @Test
    void  ShouldFailEveryWithIntervalAsStar() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* */* * * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid hour component"));
    }

    @Test
    void  ShouldPassRangeForHour() {
        var cron = new CronExpression("* 9-15 * * * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(0,59),
                "hour", getRange(9,15),
                "day of month", getRange(1, 31),
                "month", getRange(1,12),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailRangeInvalidForHour() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* 23-4 * * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid hour component"));
    }

    @Test
    void  ShouldFailRangeInvalidHourForHour() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* 10-56 * * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid hour component"));
    }

    @Test
    void  ShouldFailRangeInvalidStartHourForHour() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* -1-23 * * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid hour component"));
    }

    // List

    @Test
    void  ShouldPassListForHour() {
        var cron = new CronExpression("2,4 3,5 * * * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("2", "4"),
                "hour", List.of("3", "5"),
                "day of month", getRange(1, 31),
                "month", getRange(1,12),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassListAnyOrderForHour() {
        var cron = new CronExpression("* 4,2 * * * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(0, 59),
                "hour", List.of("2", "4"),
                "day of month", getRange(1, 31),
                "month", getRange(1,12),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailListInvalidForHour() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* , * * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid hour component"));
    }

    @Test
    void  ShouldPassSingleValueForHour() {
        var cron = new CronExpression("4 5 * * * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("4"),
                "hour", List.of("5"),
                "day of month", getRange(1, 31),
                "month", getRange(1,12),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailSingleInvalidForHour() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* - * * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid hour component"));
    }


}