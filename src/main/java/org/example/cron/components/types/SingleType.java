package org.example.cron.components.types;

import java.util.List;

public class SingleType implements NextValuesProvider {

    private final int val;

    public SingleType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    @Override
    public List<String> getNextValues() {
        return List.of(Integer.toString(val));
    }
}
