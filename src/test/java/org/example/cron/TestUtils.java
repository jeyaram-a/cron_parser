package org.example.cron;

import java.util.LinkedList;
import java.util.List;

public class TestUtils {
    static List<String> getRange(int min, int max) {
        List<String> months = new LinkedList<>();
        for(int i=min; i<=max; ++i) {
            months.add(Integer.toString(i));
        }
        return months;
    }

    static List<String> getRange(int min, int max, int interval) {
        List<String> months = new LinkedList<>();
        for(int i=min; i<=max; i+=interval) {
            months.add(Integer.toString(i));
        }
        return months;
    }


}
