package org.example.cron.components.types;

import java.util.List;
import java.util.stream.Collectors;

public class ListType implements NextValuesProvider {

    List<Integer> list;

    public ListType(List<Integer> list) {
        this.list = list;
    }

    @Override
    public List<String> getNextValues() {
        return list.stream().sorted().map(Object::toString).collect(Collectors.toList());
    }
}
