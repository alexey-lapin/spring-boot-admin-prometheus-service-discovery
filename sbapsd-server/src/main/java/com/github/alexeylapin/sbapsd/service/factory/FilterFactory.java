package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.FilterDef;
import com.github.alexeylapin.sbapsd.model.Instance;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Predicate;

public interface FilterFactory {

    Predicate<Instance> EMPTY_FILTER = instance -> true;

    Predicate<Instance> create(FilterDef filterDef);

    default Predicate<Instance> createAll(List<FilterDef> definitions) {
        Assert.notNull(definitions, "definitions must not be null");
        if (definitions.isEmpty()) {
            return EMPTY_FILTER;
        } else if (definitions.size() == 1) {
            return create(definitions.get(0));
        } else {
            Predicate<Instance> predicate = create(definitions.get(0));
            for (int i = 1; i < definitions.size(); i++) {
                predicate = predicate.and(create(definitions.get(i)));
            }
            return predicate;
        }
    }

}
