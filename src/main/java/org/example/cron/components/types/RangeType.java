package org.example.cron.components.types;

import java.util.ArrayList;
import java.util.List;

public class RangeType implements NextValuesProvider {

    int start, end;

    public RangeType(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public List<String> getNextValues() {
        List<String> next = new ArrayList<>();
        for(int i=start; i<=end; ++i) {
            next.add(Integer.toString(i));
        }
        return next;
    }
}
