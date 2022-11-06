package org.example.cron;

import org.example.cron.components.exception.ParseException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.example.cron.TestUtils.*;

import static org.junit.jupiter.api.Assertions.*;

class CronExpressionMinTest {

    @Test
    void  ShouldPassEveryWithStartForMinute() {
        var cron = new CronExpression("2/15 0 1,15 * 1-5 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("2", "17", "32", "47"),
                "hour", List.of("0"),
                "day of month", List.of("1", "15"),
                "month", getRange(1,12),
                "day of week", getRange(1, 5),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailEveryWithInvalidStartForMinute() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("-1/15 0 1,15 * 1-5 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid minute component"));
    }

    @Test
    void  ShouldFailEveryWithInvalidIntervalForMinute() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("9/75 0 1,15 * 1-5 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid minute component"));
    }

    @Test
    void  ShouldFailEveryWithInvalidIntervalForMinute1() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("9/-75 0 1,15 * 1-5 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid minute component"));
    }

    @Test
    void  ShouldPassEveryWithBigStartForMinute() {
        var cron = new CronExpression("45/15 0 1,15 * 1-5 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("45"),
                "hour", List.of("0"),
                "day of month", List.of("1", "15"),
                "month", getRange(1,12),
                "day of week", getRange(1, 5),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassEveryWithIntervalOfKForMinute() {
        var cron = new CronExpression("*/2 * * * * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(0, 59,2),
                "hour", getRange(0, 23),
                "day of month", getRange(1, 31),
                "month", getRange(1,12),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }


    @Test
    void  ShouldFailEveryWithIntervalAsStar() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("*/* * * * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid minute component"));
    }

    @Test
    void  ShouldPassRangeForMinute() {
        var cron = new CronExpression("2-4 * * * * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(2,4),
                "hour", getRange(0, 23),
                "day of month", getRange(1, 31),
                "month", getRange(1,12),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailRangeInvalidForMinute() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("4-2 * * * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid minute component"));
    }

    @Test
    void  ShouldFailRangeInvalidMinuteForMinute() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("2-90 * * * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid minute component"));
    }

    @Test
    void  ShouldFailRangeInvalidStartMinuteForMinute() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("-2-45 * * * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid minute component"));
    }

    // List

    @Test
    void  ShouldPassListForMinute() {
        var cron = new CronExpression("2,4 * * * * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("2", "4"),
                "hour", getRange(0, 23),
                "day of month", getRange(1, 31),
                "month", getRange(1,12),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassListAnyOrderForMinute() {
        var cron = new CronExpression("4,2 * * * * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("2", "4"),
                "hour", getRange(0, 23),
                "day of month", getRange(1, 31),
                "month", getRange(1,12),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailListInvalidForMinute() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression(", * * * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid minute component"));
    }

    @Test
    void  ShouldPassSingleValueForMinute() {
        var cron = new CronExpression("4 * * * * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("4"),
                "hour", getRange(0, 23),
                "day of month", getRange(1, 31),
                "month", getRange(1,12),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailSingleInvalidForMinute() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("- * * * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid minute component"));
    }



}