package com.github.alexeylapin.sbapsd.service;

import com.github.alexeylapin.sbapsd.model.Service;

import java.util.Map;

/**
 * Contributes labels to the {@link Service}
 */
public interface LabelContributor {

    void contribute(Map<String, String> labels, LabelContribution labelContribution);

}
