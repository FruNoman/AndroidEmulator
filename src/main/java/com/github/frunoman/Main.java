package com.github.frunoman;

import com.github.frunoman.enums.Emulators;
import com.github.frunoman.helper.Emulator;

public class Main {
    public static void main(String[] args) throws Exception {
        EmulatorManager emulatorManager = new EmulatorManager("/home/dmytroafrolov/Android/Sdk");
        Emulator pixel = emulatorManager.createEmulator(Emulators.PIXEL_4_26_API);
        Emulator mama = emulatorManager.createEmulator("mama",Emulators.PIXEL_4_26_API);
        Emulator papa = emulatorManager.createEmulator("papa",Emulators.PIXEL_4_26_API);
        pixel.run();
        mama.run();
        papa.run();
        Thread.sleep(20000);
        pixel.stop();
        mama.stop();
        papa.stop();
    }
}
