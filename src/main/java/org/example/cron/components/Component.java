package org.example.cron.components;

import org.example.cron.components.exception.ParseException;
import org.example.cron.components.types.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public abstract class Component {

    static final String ALPHA_PATTERN = "^[A-Z]*$";
    static final String NUM_PATTERN = "^[0-9]*$";

    static final String RANGE_PATTERN_NUM = "([0-9]{1,2}-[0-9]{1,2})";
    static final String RANGE_PATTERN_STR = "([A-Z]{3}-[A-Z]{3})";

    protected NextValuesProvider valuesProvider;

    protected String componentName;

    public String getComponentName() {
        return componentName;
    }

    Component(String in, int min, int max, String componentName, Map<String, Integer> altMap) {
        this.componentName = componentName;
        String invalidMsg = String.format("Invalid %s component", componentName);
        try {
            if (in.matches(RANGE_PATTERN_NUM) || in.matches(RANGE_PATTERN_STR)) {
                String[] splits = in.split("-");
                String from = splits[0].trim(), to = splits[1].trim();
                int first,second;
                if(altMap.containsKey(from)) {
                    if(!altMap.containsKey(to)) {
                        throw new ParseException(invalidMsg);
                    }
                    first = altMap.get(from);
                    second = altMap.get(to);
                } else {
                    first = Integer.parseInt(splits[0].trim());
                    second = Integer.parseInt(splits[1].trim());
                }
                if (first < min || second > max || min > max || first > second) {
                    throw new ParseException(invalidMsg);
                }
                valuesProvider = new RangeType(first, second);
            } else if (in.contains("/")) {
                String[] splits = in.split("/");
                String fromS = splits[0].trim(), intervalS = splits[1].trim();
                int from, interval;

                if(altMap.containsKey(fromS)) {
                    from = altMap.get(fromS);
                } else {
                    from = "*".equals(splits[0]) ? min : Integer.parseInt(splits[0]);
                }
                interval = Integer.parseInt(intervalS);


                if (from < min || from > max || interval < min || interval > max || interval==0) {
                    throw new ParseException(invalidMsg);
                }
                valuesProvider = new EveryType(from,
                        interval,
                        max
                );
            } else if (in.contains(",")) {
                if(in.equals(",") ) {
                    throw new ParseException(invalidMsg);
                }
                String[] splits = in.split(",");
                valuesProvider = new ListType(Arrays.stream(splits).map(String::trim).map(Integer::parseInt).toList());
            } else if (in.equals("*")) {
                valuesProvider = new RangeType(min, max);
            } else if (in.matches(ALPHA_PATTERN) || in.matches(NUM_PATTERN)) {
                int val;
                if(altMap.containsKey(in)) {
                    val = altMap.get(in);
                } else {
                    val = Integer.parseInt(in);
                }
                if(val < min || val > max) {
                    throw  new ParseException(invalidMsg);
                }
                valuesProvider = new SingleType(val);
            } else if(in.equals("?") && ("day of week".equals(componentName) || ("day of month".equals(componentName)))) {
                valuesProvider = new EmptyType();
            } else {
                throw new ParseException(invalidMsg);
            }
        } catch (ParseException e) {
            throw  e;
        } catch (Exception e) {
            throw new ParseException(invalidMsg + " -> "+ e.getMessage());
        }
    }

    public List<String> getNext() {
        return valuesProvider.getNextValues();
    }

}
