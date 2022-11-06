package org.example.cron;

import org.example.cron.components.exception.ParseException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.example.cron.TestUtils.getRange;
import static org.junit.jupiter.api.Assertions.*;

class CronExpressionDOWTest {
    
    @Test
    void  ShouldPassEveryWithStartForDOW() {
        var cron = new CronExpression("1-2 1-2 2/2 * 2/2 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(1,2),
                "hour", getRange(1,2),
                "day of month", getRange(2,31,2),
                "month", getRange(1,12),
                "day of week", getRange(2, 7,2),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailEveryWithInvalidStartForDOW() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * * -2/3 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of week component"));
    }

    @Test
    void  ShouldFailEveryWithInvalidIntervalForDOW() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * * 1/13 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of week component"));
    }

    @Test
    void  ShouldFailEveryWithInvalidIntervalForDOW1() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * * 2/-23 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of week component"));
    }

    @Test
    void  ShouldPassEveryWithBigStartForDOW() {
        var cron = new CronExpression("1-2 1-2 30/5 1-2 7/3 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(1,2),
                "hour", getRange(1,2),
                "day of month", List.of("30"),
                "month", List.of("1", "2"),
                "day of week", List.of("7"),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassEveryWithIntervalOfKForDOW() {
        var cron = new CronExpression("1 1 3 1 1/3 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("1"),
                "hour", List.of("1"),
                "day of month", List.of("3"),
                "month", List.of("1"),
                "day of week", List.of("1", "4", "7"),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }


    @Test
    void  ShouldFailEveryWithIntervalAsStar() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * * */* /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of week component"));
    }

    @Test
    void  ShouldPassRangeForDOW() {
        var cron = new CronExpression("* * * * 1-3 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(0,59),
                "hour", getRange(0,23),
                "day of month", getRange(1, 31),
                "month", getRange(1, 12),
                "day of week", getRange(1, 3),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailRangeInvalidForDOW() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * * 7-6 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of week component"));
    }

    @Test
    void  ShouldFailRangeInvalidMonthForDOW() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * * 1-10 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of week component"));
    }

    @Test
    void  ShouldFailRangeInvalidStartHourForDOW() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * * 12-14 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of week component"));
    }

    // List

    @Test
    void  ShouldPassListForDOW() {
        var cron = new CronExpression("2,4 3,5 1,2 * 1,2 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("2", "4"),
                "hour", List.of("3", "5"),
                "day of month", getRange(1, 2),
                "month", getRange(1,12),
                "day of week", List.of("1", "2"),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassListAnyOrderForDOW() {
        var cron = new CronExpression("* * 23,22 * 4,3 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", getRange(0, 59),
                "hour", getRange(0,23),
                "day of month", List.of("22", "23"),
                "month", getRange(1,12),
                "day of week", List.of("3", "4"),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailListInvalidForDOW() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * * , /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of week component"));
    }

    @Test
    void  ShouldPassSingleValueForDOW() {
        var cron = new CronExpression("4 5 3 4 1 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("4"),
                "hour", List.of("5"),
                "day of month", List.of("3"),
                "month", List.of("4"),
                "day of week", List.of("1"),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldFailSingleInvalidForDOW() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * * - /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of week component"));
    }

    @Test
    void  ShouldFailSingleInvalidForDOW1() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * * 9 /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of week component"));
    }

    @Test
    void  ShouldPassIgnoreValueForDOW() {
        var cron = new CronExpression("4 5 4 4 ? /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("4"),
                "hour", List.of("5"),
                "day of month", List.of("4"),
                "month", List.of("4"),
                "day of week", List.of(),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassSingleValueWordForDOW() {
        var cron = new CronExpression("4 5 3 * TUE /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("4"),
                "hour", List.of("5"),
                "day of month", List.of("3"),
                "month", getRange(1,12),
                "day of week", List.of("3"),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassRangeValueWordForDOW() {
        var cron = new CronExpression("4 5 3 1 MON-FRI /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("4"),
                "hour", List.of("5"),
                "day of month", List.of("3"),
                "month", List.of("1"),
                "day of week", getRange(2, 6),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }

    @Test
    void  ShouldPassEveryValueWordForDOW() {
        var cron = new CronExpression("4 5 3 1 TUE/2 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("4"),
                "hour", List.of("5"),
                "day of month", List.of("3"),
                "month", List.of("1"),
                "day of week", getRange(3, 7, 2),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }


    @Test
    void  ShouldFailSingleInvalidWordDateForDOW() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * * XYZ /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of week component"));
    }

    @Test
    void  ShouldFailRangeInvalidWordDateForDOW1() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * * MON-XYZ /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of week component"));
    }

    @Test
    void  ShouldFailEveryValueWordStringIntervalForMonth() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("4 5 3 * MON/XYZ /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Invalid day of week component"));
    }
}