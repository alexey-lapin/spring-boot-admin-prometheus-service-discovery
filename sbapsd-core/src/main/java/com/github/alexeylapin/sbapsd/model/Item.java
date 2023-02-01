package com.github.alexeylapin.sbapsd.model;

import java.util.List;
import java.util.Map;

public class Item {

    public Item(List<String> targets, Map<String, String> labels) {
        this.targets = targets;
        this.labels = labels;
    }

    private List<String> targets;
    private Map<String, String> labels;

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }
}
