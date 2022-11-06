package org.example.cron;

import org.example.cron.components.exception.ParseException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.example.cron.TestUtils.getRange;
import static org.junit.jupiter.api.Assertions.*;

class CronExpressionDOMTest {
    
    @Test
    void  ShouldPassEveryWithStartForDOM() {
        var cron = new CronExpression("1-2 1-2 2/2 * * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(1,2),
                "hour", getRange(1,2),
                "day of month", getRange(2,31,2),
                "month", getRange(1,12),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailEveryWithInvalidStartForDOM() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * -1/23 * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of month component"));
    }

    @Test
    void  ShouldFailEveryWithInvalidIntervalForDOM() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * 1/32 * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of month component"));
    }

    @Test
    void  ShouldFailEveryWithInvalidIntervalForDOM1() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * 1/-32 * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of month component"));
    }

    @Test
    void  ShouldPassEveryWithBigStartForDOM() {
        var cron = new CronExpression("1-2 1-2 30/5 1-2 1-5 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(1,2),
                "hour", getRange(1,2),
                "day of month", List.of("30"),
                "month", List.of("1", "2"),
                "day of week", getRange(1, 5),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassEveryWithIntervalOfKForDOM() {
        var cron = new CronExpression("1 1 */3 1 1 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("1"),
                "hour", List.of("1"),
                "day of month", getRange(1, 31, 3),
                "month", List.of("1"),
                "day of week", List.of("1"),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }


    @Test
    void  ShouldFailEveryWithIntervalAsStar() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * */* * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of month component"));
    }

    @Test
    void  ShouldPassRangeForDOM() {
        var cron = new CronExpression("* * 1-10 * * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(0,59),
                "hour", getRange(0,23),
                "day of month", getRange(1, 10),
                "month", getRange(1, 12),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailRangeInvalidForDOM() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * 31-2 * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of month component"));
    }

    @Test
    void  ShouldFailRangeInvalidMonthForDOM() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * 1-33 * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of month component"));
    }

    @Test
    void  ShouldFailRangeInvalidStartHourForDOM() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * 0-12 * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of month component"));
    }

    // List

    @Test
    void  ShouldPassListForDOM() {
        var cron = new CronExpression("2,4 3,5 1,2 * * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("2", "4"),
                "hour", List.of("3", "5"),
                "day of month", getRange(1, 2),
                "month", getRange(1,12),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassListAnyOrderForDOM() {
        var cron = new CronExpression("* * 23,22 * * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(0, 59),
                "hour", getRange(0,23),
                "day of month", List.of("22", "23"),
                "month", getRange(1,12),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailListInvalidForDOM() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * , * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of month component"));
    }

    @Test
    void  ShouldPassSingleValueForDOM() {
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
    void  ShouldFailSingleInvalidForDOM() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * - * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of month component"));
    }

    @Test
    void  ShouldFailSingleInvalidDateForDOM() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * 31 4 * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of month component"));
    }
    @Test
    void  ShouldPassIgnoreValueForDOM() {
        var cron = new CronExpression("4 5 ? 4 * /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("4"),
                "hour", List.of("5"),
                "day of month", List.of(),
                "month", List.of("4"),
                "day of week", getRange(1, 7),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

}