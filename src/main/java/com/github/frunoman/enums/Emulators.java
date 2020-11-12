package com.github.frunoman.enums;

import com.google.common.io.Resources;

public enum Emulators {
    PIXEL_4_26_API(
            "PIXEL_4_26_API",
            Resources.getResource("pixel4_26_api.properties").getPath()
    ),
    NEXUS_10_30_API(
            "NEXUS_10_30_API",
            Resources.getResource("nexus10_30_api.properties").getPath()
    ),

    NEXUS_9_26_API(
            "NEXUS_9_26_API",
            Resources.getResource("nexus9_26_api.properties").getPath()
    );
    private String properPath;
    private String emulatorName;

    Emulators(String emulatorName, String properPath) {
        this.properPath = properPath;
        this.emulatorName = emulatorName;
    }

    public String getPath() {
        return properPath;
    }

    public String getName() {
        return emulatorName;
    }
}
