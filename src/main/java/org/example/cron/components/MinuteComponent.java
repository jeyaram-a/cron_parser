package org.example.cron.components;

import java.util.Map;


public class MinuteComponent extends Component {

    static final int INDEX = 0;
    
    public MinuteComponent(String[] parts) {
        super(parts[INDEX], 0, 59, "minute", Map.of());
    }

}
