package com.github.alexeylapin.sbapsd.service;

import com.github.alexeylapin.sbapsd.model.Item;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Getter
public abstract class AbstractInstanceProvider implements InstanceProvider {

    private final Predicate<Item> filter;
    private final Map<String, String> labels;

    protected AbstractInstanceProvider(Predicate<Item> filter, Map<String, String> labels) {
        this.filter = filter;
        this.labels = Collections.unmodifiableMap(labels);
    }

    public Map<String, String> getLabels() {
        return new HashMap<>(labels);
    }

}
