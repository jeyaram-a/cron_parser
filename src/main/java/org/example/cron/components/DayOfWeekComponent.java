package org.example.cron.components;

import java.util.Map;


public class DayOfWeekComponent extends Component {

    static final int INDEX = 4;

    private static final Map<String, Integer> altMap = Map.of(
            "SUN", 1,
            "MON", 2,
            "TUE", 3,
            "WED", 4,
            "THU", 5,
            "FRI", 6,
            "SAT", 7
            );
    public DayOfWeekComponent(String[] parts) {
        super(parts[INDEX], 1, 7, "day of week", altMap);
    }

}
