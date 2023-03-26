package com.github.alexeylapin.sbapsd.config.def;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration representation of ServiceProvider
 * @see com.github.alexeylapin.sbapsd.service.factory.ServiceProviderFactory
 * @see com.github.alexeylapin.sbapsd.service.ServiceProvider
 */
@Data
public class ServiceProviderDef {

    private String name;
    private String type;
    private Map<String, String> params = new HashMap<>();
    private Map<String, String> labels = new HashMap<>();
    private List<FilterDef> filters = new ArrayList<>();

}
