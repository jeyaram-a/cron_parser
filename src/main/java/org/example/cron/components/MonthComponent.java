package org.example.cron.components;

import org.example.cron.components.types.SingleType;

import java.util.HashMap;
import java.util.Map;


public class MonthComponent extends Component {
    static final int INDEX = 3;

    private static final int[] dayCount = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public int getMaxDays() {
        if(this.valuesProvider instanceof SingleType st) {
            return dayCount[st.getVal()];
        }
        return 31;
    }

    static Map<String, Integer> getMonthMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("JAN", 1);
        map.put("FED", 2);
        map.put("MAR", 3);
        map.put("APR", 4);
        map.put("MAY", 5);
        map.put("JUN", 6);
        map.put("JUL", 7);
        map.put("AUG", 8);
        map.put("SEP", 9);
        map.put("OCT", 10);
        map.put("NOV", 11);
        map.put("DEC", 12);

        return map;
    }

    private static final Map<String, Integer> altMap = getMonthMap();

    public MonthComponent(String[] parts) {
       super(parts[INDEX], 1, 12, "month", altMap);
    }

}
