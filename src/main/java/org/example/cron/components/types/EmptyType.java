package org.example.cron.components.types;

import java.util.List;

public class EmptyType implements NextValuesProvider {
    @Override
    public List<String> getNextValues() {
        return List.of();
    }
}
