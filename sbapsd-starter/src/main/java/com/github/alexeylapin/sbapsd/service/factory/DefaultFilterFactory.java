package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.FilterDef;
import com.github.alexeylapin.sbapsd.model.Instance;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class DefaultFilterFactory implements FilterFactory {

    public static final String FILTER_TYPE_APP_NAME = "app-name";
    public static final String FILTER_TYPE_STATUS = "status";

    public static final String FILTER_PARAM_VALUE = "value";

    @Override
    public Predicate<Instance> create(FilterDef filterDef) {
        String type = filterDef.getType();
        switch (type) {
            case FILTER_TYPE_APP_NAME: return appName(filterDef);
            case FILTER_TYPE_STATUS: return status(filterDef);
            default: throw new IllegalArgumentException();
        }
    }

    private Predicate<Instance> appName(FilterDef filterDef) {
        String regex = filterDef.getParams().get(FILTER_PARAM_VALUE);
        Pattern pattern = Pattern.compile(regex);
        return instance -> pattern.matcher(instance.getName()).matches();
    }

    private Predicate<Instance> status(FilterDef filterDef) {
        String value = filterDef.getParams().get(FILTER_PARAM_VALUE);
        String[] statuses = value.split(",");
        return instance -> Arrays.stream(statuses).anyMatch(status -> instance.getStatus().equals(status));
    }

}
