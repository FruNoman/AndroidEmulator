package com.github.frunoman;

import com.android.prefs.AndroidLocation;
import com.android.repository.api.ProgressIndicator;
import com.android.sdklib.internal.avd.AvdInfo;
import com.android.sdklib.internal.avd.AvdManager;
import com.android.sdklib.repository.AndroidSdkHandler;
import com.android.sdklib.repository.LoggerProgressIndicatorWrapper;
import com.android.sdklib.repository.targets.SystemImage;
import com.android.sdklib.repository.targets.SystemImageManager;
import com.android.utils.ILogger;
import com.github.frunoman.enums.Emulators;
import com.github.frunoman.helper.ProgressIndicatorImpl;
import com.github.frunoman.helper.iLoggerImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Predicate;

public class Emulator {
    private String ANDROID_HOME;
    private String EMULATOR;
    private AndroidSdkHandler sdkHandler;
    private SystemImageManager systemImageManager;
    private AvdManager avdManager;
    private ProgressIndicator progressIndicator = new ProgressIndicatorImpl();
    private ILogger iLogger = new iLoggerImpl();

    public Emulator(String ANDROID_HOME) {
        try {
            this.ANDROID_HOME = ANDROID_HOME;
            this.EMULATOR = ANDROID_HOME+File.separator+"tools"+File.separator+"emulator";
            this.sdkHandler = AndroidSdkHandler.getInstance(new File(ANDROID_HOME));
            this.systemImageManager = sdkHandler.getSystemImageManager(progressIndicator);
            this.avdManager = AvdManager.getInstance(sdkHandler, iLogger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AvdInfo createAvd(Emulators emulator) {
        try {
            Map<String, String> bootConfig = new HashMap<>();
            Map<String, String> hardwareProperties = new HashMap<>();
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(emulator.getPath())));
            hardwareProperties.putAll(((Map) properties));
            SystemImage currentSystemImage = null;
            for (SystemImage systemImage : systemImageManager.getImages()) {
                if (systemImage.getAndroidVersion().getApiLevel() == Integer.parseInt(properties.getProperty("image.androidVersion.api"))) {
                    currentSystemImage = systemImage;
                    break;
                }
            }
            return avdManager
                    .createAvd(
                            new File(avdManager.getBaseAvdFolder().getAbsolutePath() + File.separator + emulator.name() + ".avd"),
                            emulator.name(),
                            currentSystemImage,
                            null,
                            null,
                            "512M",
                            hardwareProperties,
                            bootConfig,
                            true,
                            false,
                            true,
                            false,
                            iLogger);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void run(Emulators emulators) {
        AvdInfo avdInfo = null;
        for (AvdInfo info:avdManager.getAllAvds()){
            if (info.getName().equals(emulators.getName())){
                avdInfo = info;
                break;
            }
        }
        if (avdInfo==null){
            throw new RuntimeException("No found emulator");
        }

        try {
            Process process = Runtime.getRuntime().exec(new String[]{EMULATOR, "-avd", avdInfo.getName()});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(Emulators emulators){
        AvdInfo avdInfo = null;
        for (AvdInfo info:avdManager.getAllAvds()){
            if (info.getName().equals(emulators.getName())){
                avdInfo = info;
                break;
            }
        }
        avdManager.stopAvd(avdInfo);
    }
}
