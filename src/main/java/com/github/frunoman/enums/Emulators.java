package com.github.frunoman.enums;

import com.google.common.io.Resources;

import java.io.InputStream;

public enum Emulators {
    PIXEL_4_26_API(
            "PIXEL_4_26_API",
            Thread.currentThread().getContextClassLoader().getResourceAsStream("pixel4_26_api.properties")
    ),
    NEXUS_10_30_API(
            "NEXUS_10_30_API",
            Thread.currentThread().getContextClassLoader().getResourceAsStream("nexus10_30_api.properties")
    ),

    NEXUS_9_26_API(
            "NEXUS_9_26_API",
            Thread.currentThread().getContextClassLoader().getResourceAsStream("nexus9_26_api.properties")
    );
    private InputStream streamProps;
    private String emulatorName;

    Emulators(String emulatorName, InputStream streamProps) {
        this.streamProps = streamProps;
        this.emulatorName = emulatorName;
    }

    public InputStream getStreamProps() {
        return streamProps;
    }

    public String getName() {
        return emulatorName;
    }
}
