package com.github.alexeylapin.sbapsd.service;

import com.github.alexeylapin.sbapsd.model.Instance;

import java.util.List;
import java.util.Map;

public interface LabelContributor {

    void contribute(Map<String, String> labels, String name, List<Instance> instances);

}
