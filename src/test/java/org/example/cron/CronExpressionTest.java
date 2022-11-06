package org.example.cron;

import org.example.cron.components.exception.ParseException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.example.cron.TestUtils.getRange;
import static org.junit.jupiter.api.Assertions.*;

class CronExpressionTest {

    @Test
    void  ShouldFailEveryWithIntervalAsStar() {
        Exception exception = assertThrows(ParseException.class, () -> new CronExpression("* * * * /usr/bin/find"));
        assertTrue(exception.getMessage().contains("Parse error: needs 6 components in order min, hour, dom, month, dow, command"));
    }

    @Test
    void  ShouldPassProvidedTestCase() {
        var cron = new CronExpression("*/15 0 1,15 * 1-5 /usr/bin/find");
        Map<String, List<String>> expected = Map.of(
                "minute", List.of("0", "15", "30", "45"),
                "hour", List.of("0"),
                "day of month", List.of("1", "15"),
                "month", getRange(1,12),
                "day of week", getRange(1, 5),
                "command", List.of("/usr/bin/find")
        );
        assertEquals(expected, cron.getNextAsMap());
    }



}