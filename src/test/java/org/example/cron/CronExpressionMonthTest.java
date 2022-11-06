package org.example.cron;

import org.example.cron.components.exception.ParseException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.example.cron.TestUtils.getRange;
import static org.junit.jupiter.api.Assertions.*;

class CronExpressionMonthTest {
    
    @Test
    void  ShouldPassEveryWithStartForMonth() {
        var cron = new CronExpression("1-2 1-2 1-2 3/2 * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(1,2),
                "hour", getRange(1,2),
                "day of month", getRange(1,2),
                "month", getRange(3, 12, 2),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailEveryWithInvalidStartForMonth() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * 23/2 1-5 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid month component"));
    }

    @Test
    void  ShouldFailEveryWithInvalidIntervalForMonth() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * 1/23 1-5 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid month component"));
    }

    @Test
    void  ShouldFailEveryWithInvalidIntervalForMonth1() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * 1/-3 1-5 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid month component"));
    }

    @Test
    void  ShouldPassEveryWithBigStartForMonth() {
        var cron = new CronExpression("1-2 1-2 1-2 11/3 1-5 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(1,2),
                "hour", getRange(1,2),
                "day of month", getRange(1,2),
                "month", List.of("11"),
                "day of week", getRange(1, 5),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassEveryWithIntervalOfKForMonth() {
        var cron = new CronExpression("*/2 1/3 * */2 * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(0, 59,2),
                "hour", getRange(1, 23, 3),
                "day of month", getRange(1, 31),
                "month", getRange(1,12,2),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }


    @Test
    void  ShouldFailEveryWithIntervalAsStar() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * */* * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid month component"));
    }

    @Test
    void  ShouldPassRangeForMonth() {
        var cron = new CronExpression("* * * 1-10 * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(0,59),
                "hour", getRange(0,23),
                "day of month", getRange(1, 31),
                "month", getRange(1,10),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailRangeInvalidForMonth() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * 11-9 * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid month component"));
    }

    @Test
    void  ShouldFailRangeInvalidMonthForMonth() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * 13-34 * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid month component"));
    }

    @Test
    void  ShouldFailRangeInvalidStartHourForMonth() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * -1-12 * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid month component"));
    }

    // List

    @Test
    void  ShouldPassListForMonth() {
        var cron = new CronExpression("2,4 3,5 * 2,3 * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("2", "4"),
                "hour", List.of("3", "5"),
                "day of month", getRange(1, 31),
                "month", getRange(2,3),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassListAnyOrderForMonth() {
        var cron = new CronExpression("* * * 10,9 * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(0, 59),
                "hour", getRange(0,23),
                "day of month", getRange(1, 31),
                "month", List.of("9", "10"),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailListInvalidForMonth() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * , * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid month component"));
    }

    @Test
    void  ShouldPassSingleValueForMonth() {
        var cron = new CronExpression("4 5 3 4 * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("4"),
                "hour", List.of("5"),
                "day of month", List.of("3"),
                "month", List.of("4"),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailSingleInvalidForMonth() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * - * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid month component"));
    }

    @Test
    void  ShouldFailSingleInvalidDateForMonth() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * 31 * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid month component"));
    }

    @Test
    void  ShouldPassSingleValueWordForMonth() {
        var cron = new CronExpression("4 5 3 APR * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("4"),
                "hour", List.of("5"),
                "day of month", List.of("3"),
                "month", List.of("4"),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassRangeValueWordForMonth() {
        var cron = new CronExpression("4 5 3 APR-MAY * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("4"),
                "hour", List.of("5"),
                "day of month", List.of("3"),
                "month", List.of("4", "5"),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassEveryValueWordForMonth() {
        var cron = new CronExpression("4 5 3 APR/2 * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("4"),
                "hour", List.of("5"),
                "day of month", List.of("3"),
                "month", getRange(4, 12,2),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }


    @Test
    void  ShouldFailSingleInvalidWordDateForMonth() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * KLJ * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid month component"));
    }

    @Test
    void  ShouldFailRangeInvalidWordDateForMonth1() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * APR-JKL * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid month component"));
    }

    @Test
    void  ShouldFailEveryValueWordStringIntervalForMonth() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("4 5 3 APR/OP * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid month component"));
    }

}