package com.github.frunoman;

import com.github.frunoman.enums.Emulators;

public class Main {
    public static void main(String[] args) throws Exception {
        EmulatorManager emulatorManager = new EmulatorManager("/home/dmytroafrolov/Android/Sdk");
        Emulator nexus = emulatorManager.createEmulator(Emulators.NEXUS_10_30_API);
        nexus.run();
        Thread.sleep(10000);
        System.out.println(nexus.getSerial());
        Thread.sleep(10000);
        nexus.stop();


    }
}
