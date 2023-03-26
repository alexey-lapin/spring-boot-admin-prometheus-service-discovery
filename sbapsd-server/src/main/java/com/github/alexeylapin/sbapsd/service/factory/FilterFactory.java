/*
 * MIT License
 *
 * Copyright (c) 2023 Alexey Lapin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.FilterDef;
import com.github.alexeylapin.sbapsd.model.Instance;
import com.github.alexeylapin.sbapsd.service.Validate;

import java.util.List;
import java.util.function.Predicate;

/**
 * Instantiates {@link Predicate} based on {@link FilterDef}
 */
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
