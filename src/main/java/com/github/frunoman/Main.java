package com.github.frunoman;

import com.android.sdklib.internal.avd.AvdInfo;
import com.github.frunoman.enums.Emulators;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Emulator emulator = new Emulator("/home/dmytroafrolov/Android/Sdk");
        AvdInfo avdInfo = emulator.createAvd(Emulators.PIXEL_4_26_API);
        System.out.println(avdInfo.getName());
        emulator.run(Emulators.PIXEL_4_26_API);
        Thread.sleep(34000);
        emulator.stop(Emulators.PIXEL_4_26_API);

    }
}
