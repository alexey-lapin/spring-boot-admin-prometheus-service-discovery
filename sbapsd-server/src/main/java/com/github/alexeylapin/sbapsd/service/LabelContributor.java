package com.github.alexeylapin.sbapsd.service;

import java.util.Map;

public interface LabelContributor {

    void contribute(Map<String, String> labels, LabelContribution labelContribution);

}
