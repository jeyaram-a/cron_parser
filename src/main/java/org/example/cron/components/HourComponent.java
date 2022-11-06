package org.example.cron.components;

import java.util.Map;


public class HourComponent extends Component {

    static final int INDEX = 1;

    public HourComponent(String[] parts) {
        super(parts[INDEX], 0, 23, "hour", Map.of());
    }

}
