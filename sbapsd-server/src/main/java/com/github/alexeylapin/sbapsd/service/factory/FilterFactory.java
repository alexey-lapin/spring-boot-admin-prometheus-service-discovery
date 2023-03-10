package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.FilterDef;
import com.github.alexeylapin.sbapsd.model.Instance;
import com.github.alexeylapin.sbapsd.service.Validate;

import java.util.List;
import java.util.function.Predicate;

public interface FilterFactory {

    Predicate<Instance> EMPTY_FILTER = instance -> true;

    Predicate<Instance> create(FilterDef filterDef);

    default Predicate<Instance> createAll(List<FilterDef> filterDefs) {
        Validate.notNull(filterDefs, "filterDefs must not be null");
        if (filterDefs.isEmpty()) {
            return EMPTY_FILTER;
        } else if (filterDefs.size() == 1) {
            return create(filterDefs.get(0));
        } else {
            Predicate<Instance> predicate = create(filterDefs.get(0));
            for (int i = 1; i < filterDefs.size(); i++) {
                predicate = predicate.and(create(filterDefs.get(i)));
            }
            return predicate;
        }
    }

}
