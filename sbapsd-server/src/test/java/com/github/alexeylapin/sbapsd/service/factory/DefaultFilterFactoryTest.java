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
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultFilterFactoryTest {

    @Test
    void should_createCorrectPredicate_when_filterDefOfAppNameType() {
        FilterFactory filterFactory = new DefaultFilterFactory();

        FilterDef filterDef = new FilterDef();
        filterDef.setType(DefaultFilterFactory.FILTER_TYPE_APP_NAME);
        filterDef.getParams().put(DefaultFilterFactory.FILTER_PARAM_VALUE, "app-.*");

        Predicate<Instance> predicate = filterFactory.create(filterDef);

        assertThat(predicate.test(Instance.builder().name("app-1").build())).isTrue();
        assertThat(predicate.test(Instance.builder().name("application-1").build())).isFalse();
    }

    @Test
    void should_createCorrectPredicate_when_filterDefOfStatusType() {
        FilterFactory filterFactory = new DefaultFilterFactory();

        FilterDef filterDef = new FilterDef();
        filterDef.setType(DefaultFilterFactory.FILTER_TYPE_STATUS);
        filterDef.getParams().put(DefaultFilterFactory.FILTER_PARAM_VALUE, "UP,DOWN");

        Predicate<Instance> predicate = filterFactory.create(filterDef);

        assertThat(predicate.test(Instance.builder().status("UP").build())).isTrue();
        assertThat(predicate.test(Instance.builder().status("DOWN").build())).isTrue();
        assertThat(predicate.test(Instance.builder().status("OFFLINE").build())).isFalse();
    }

    @Test
    void should_createCorrectPredicate_when_filterDefOfAppNameAndStatusType() {
        FilterFactory filterFactory = new DefaultFilterFactory();

        FilterDef filterDef1 = new FilterDef();
        filterDef1.setType(DefaultFilterFactory.FILTER_TYPE_APP_NAME);
        filterDef1.getParams().put(DefaultFilterFactory.FILTER_PARAM_VALUE, "app-.*");

        FilterDef filterDef2 = new FilterDef();
        filterDef2.setType(DefaultFilterFactory.FILTER_TYPE_STATUS);
        filterDef2.getParams().put(DefaultFilterFactory.FILTER_PARAM_VALUE, "UP,DOWN");

        Predicate<Instance> predicate = filterFactory.createAll(Arrays.asList(filterDef1, filterDef2));

        assertThat(predicate.test(Instance.builder().name("app-1").status("UP").build())).isTrue();
        assertThat(predicate.test(Instance.builder().name("app-2").status("DOWN").build())).isTrue();
        assertThat(predicate.test(Instance.builder().name("application-1").status("DOWN").build())).isFalse();
        assertThat(predicate.test(Instance.builder().name("app-3").status("OFFLINE").build())).isFalse();
    }

}
