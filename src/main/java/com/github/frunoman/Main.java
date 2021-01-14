package com.github.frunoman;

import com.android.repository.api.Downloader;
import com.android.repository.api.ProgressIndicator;
import com.android.repository.api.RepoManager;
import com.android.repository.impl.downloader.LocalFileAwareDownloader;
import com.android.sdklib.devices.Device;
import com.android.sdklib.devices.DeviceManager;
import com.android.sdklib.internal.avd.AvdManager;
import com.android.sdklib.repository.AndroidSdkHandler;
import com.android.sdklib.repository.targets.AndroidTargetManager;
import com.android.sdklib.repository.targets.SystemImageManager;
import com.android.utils.ILogger;
import com.github.frunoman.enums.Emulators;
import com.github.frunoman.helper.ProgressIndicatorImpl;
import com.github.frunoman.helper.iLoggerImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        EmulatorManager emulatorManager = new EmulatorManager("C:\\Users\\Nati\\AppData\\Local\\Android\\Sdk");
//        Emulator nexus = emulatorManager.createEmulator(Emulators.NEXUS_9_26_API);

        List<Device> availableDevices = emulatorManager.getAvailableDevices(DeviceManager.ALL_DEVICES);
                Emulator second = emulatorManager.createEmulator("3",properties ,sk);
        Emulator first = emulatorManager.createEmulator("1",properties ,sk);

        second.run();


        first.run();
//        nexus.run();
        Thread.sleep(20000);
//        System.out.println(nexus.getSerial());
        System.out.println(second.getSerial());
        System.out.println(first.getSerial());

        Thread.sleep(20000);
//        nexus.stop();
        second.stop();
        first.stop();


    }
}
