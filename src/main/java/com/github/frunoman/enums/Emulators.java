package com.github.frunoman.enums;

public enum Emulators {
    PIXEL_4_26_API(
            "PIXEL_4_26_API",
            "/home/dmytroafrolov/IdeaProjects/AndroidEmulator/src/main/resources/pixel4_26_api.properties"
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
