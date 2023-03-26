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

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class DefaultFilterFactory implements FilterFactory {

    public static final String FILTER_TYPE_APP_NAME = "app-name";
    public static final String FILTER_TYPE_STATUS = "status";

    public static final String FILTER_PARAM_VALUE = "value";

    @Override
    public Predicate<Instance> create(FilterDef filterDef) {
        Validate.notNull(filterDef, "filterDef must not be null");
        String type = filterDef.getType();
        Validate.notNull(type, "filterDef.type must not be null");
        switch (type) {
            case FILTER_TYPE_APP_NAME:
                return appName(filterDef);
            case FILTER_TYPE_STATUS:
                return status(filterDef);
            default:
                throw new IllegalArgumentException("unknown filter type: " + type);
        }
    }

    private Predicate<Instance> appName(FilterDef filterDef) {
        String value = filterDef.getParams().get(FILTER_PARAM_VALUE);
        Validate.notNull(value, "filter.params.value must not be null");
        Pattern pattern = Pattern.compile(value);
        return instance -> pattern.matcher(instance.getName()).matches();
    }

    private Predicate<Instance> status(FilterDef filterDef) {
        String value = filterDef.getParams().get(FILTER_PARAM_VALUE);
        Validate.notNull(value, "filter.params.value must not be null");
        String[] statuses = value.split(",");
        return instance -> Arrays.stream(statuses).anyMatch(status -> instance.getStatus().equals(status));
    }

}
