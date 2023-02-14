package com.github.alexeylapin.sbapsd.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class Instance {

    private String name;
    private String status;
    private String url;
    private Map<String, String> tags;
    private Map<String, String> metadata;

}
