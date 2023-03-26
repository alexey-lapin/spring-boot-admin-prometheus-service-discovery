package com.github.alexeylapin.sbapsd.config.def;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration representation of Filter
 * @see com.github.alexeylapin.sbapsd.service.factory.FilterFactory
 */
@Data
public class FilterDef {

    private String type;
    private Map<String, String> params = new HashMap<>();

}
