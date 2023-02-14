package com.github.alexeylapin.sbapsd.config.def;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FilterDef {

    private String type;
    private Map<String, String> params = new HashMap<>();

}
