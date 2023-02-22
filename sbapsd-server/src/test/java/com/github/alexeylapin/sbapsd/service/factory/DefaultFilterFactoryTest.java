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