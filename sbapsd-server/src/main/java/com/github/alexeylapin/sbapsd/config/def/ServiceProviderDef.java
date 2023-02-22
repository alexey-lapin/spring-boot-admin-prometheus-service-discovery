package com.github.alexeylapin.sbapsd.config.def;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ServiceProviderDef {

    private String type;
    private Map<String, String> params = new HashMap<>();
    private Map<String, String> labels = new HashMap<>();
    private List<FilterDef> filters = new ArrayList<>();

}
