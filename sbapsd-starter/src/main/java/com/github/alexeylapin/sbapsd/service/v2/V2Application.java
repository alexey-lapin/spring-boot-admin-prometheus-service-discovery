package com.github.alexeylapin.sbapsd.service.v2;

import com.github.alexeylapin.sbapsd.model.Instance;
import lombok.Data;

import java.util.List;

@Data
public class V2Application {

    private String name;
    private List<Instance> instances;

}
