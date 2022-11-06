package org.example.cron.components.types;

import java.util.ArrayList;
import java.util.List;

public class EveryType implements NextValuesProvider {
    int start ;
    int interval;
    int max;

    public EveryType(int start, int interval, int max) {
        this.start = start;
        this.interval = interval;
        this.max = max;
    }

    @Override
    public List<String> getNextValues() {
        int curr = start;
        var nextValues = new ArrayList<String>();
        while(curr <= max) {
            nextValues.add(Integer.toString(curr));
            curr += interval;
        }
        return nextValues;
    }
}
