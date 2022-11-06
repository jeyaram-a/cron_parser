package org.example.cron.components;

import java.util.Map;


public class DayOfMonthComponent extends Component {

    private static final int INDEX = 2;

    public DayOfMonthComponent(String[] parts, int maxDays) {
      super(parts[INDEX], 1, maxDays, "day of month", Map.of());
    }
}
