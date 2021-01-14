package com.github.frunoman;

import com.android.repository.api.ProgressIndicator;
import com.android.sdklib.devices.Device;
import com.android.sdklib.devices.DeviceManager;
import com.android.sdklib.internal.avd.AvdInfo;
import com.android.sdklib.internal.avd.AvdManager;
import com.android.sdklib.repository.AndroidSdkHandler;
import com.android.sdklib.repository.targets.SystemImage;
import com.android.sdklib.repository.targets.SystemImageManager;
import com.android.utils.ILogger;
import com.github.frunoman.enums.Emulators;
import com.github.frunoman.helper.ProgressIndicatorImpl;
import com.github.frunoman.helper.Utils;
import com.github.frunoman.helper.iLoggerImpl;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class EmulatorManager {
    private String ANDROID_HOME;
    private String EMULATOR;
    private AndroidSdkHandler sdkHandler;
    private SystemImageManager systemImageManager;
    private AvdManager avdManager;
    private DeviceManager deviceManager;
    private ProgressIndicator progressIndicator = new ProgressIndicatorImpl();
    private ILogger iLogger = new iLoggerImpl();
    private static final Logger logger = Logger.getLogger(EmulatorManager.class);

    public EmulatorManager(String ANDROID_HOME) {
        logger.debug("Create emulator manager with ANDROID_HOME ["+ANDROID_HOME+"]");
        try {
            this.ANDROID_HOME = ANDROID_HOME;
            this.EMULATOR = ANDROID_HOME + File.separator + "emulator" + File.separator + "emulator";
            this.sdkHandler = AndroidSdkHandler.getInstance(new File(ANDROID_HOME));
            this.deviceManager = DeviceManager.createInstance(sdkHandler,iLogger);
            this.systemImageManager = sdkHandler.getSystemImageManager(progressIndicator);
            this.avdManager = AvdManager.getInstance(sdkHandler, iLogger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Device> getAvailableDevices(DeviceManager.DeviceFilter deviceFilter){
        return new ArrayList<>(deviceManager.getDevices(deviceFilter));
    }

    public List<Device> getAvailableDevices(EnumSet<DeviceManager.DeviceFilter> deviceFilters){
        return new ArrayList<>(deviceManager.getDevices(deviceFilters));
    }

    public Emulator createEmulator(Emulators emulator) {
        return createEmulator(emulator.getName(), emulator);
    }

    public Emulator createEmulator(String name, Properties properties,String skinFile) {
        logger.debug("Create emulator with name ["+name+"] and properties ["+properties.toString()+"]");
        try {
            properties.setProperty("avd.ini.displayname", name);
            properties.setProperty("AvdId", name);
            properties.setProperty("skin.path",  ANDROID_HOME+"\\skins\\"+skinFile);
//            properties.setProperty("skin.path",  ANDROID_HOME+"/skins");

//            properties.load(new FileInputStream(new File("C:\\Users\\Nati\\IdeaProjects\\AndroidEmulator\\src\\main\\resources\\nexus9_26_api.properties")));

            Map<String, String> bootConfig = new HashMap<>();
            Map<String, String> hardwareProperties = new HashMap<>();
            hardwareProperties.putAll(((Map) properties));
            SystemImage currentSystemImage = null;
            for (SystemImage systemImage : systemImageManager.getImages()) {
                if (systemImage.getAndroidVersion().getApiLevel() == 26) {
                    currentSystemImage = systemImage;
                    break;
                }
            }
            AvdInfo avdInfo = avdManager
                    .createAvd(
                            new File(avdManager.getBaseAvdFolder().getAbsolutePath() + File.separator + name + ".avd"),
                            name,
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
            return new Emulator(EMULATOR,avdInfo,avdManager);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Emulator createEmulator(String name, Properties properties) {
        logger.debug("Create emulator with name ["+name+"] and properties ["+properties.toString()+"]");
        try {
            properties.setProperty("avd.ini.displayname", name);
            properties.setProperty("AvdId", name);
            properties.setProperty("skin.path",  ANDROID_HOME+"\\skins\\"+properties.getProperty("hw.device.name"));
//            properties.setProperty("skin.path",  ANDROID_HOME+"/skins");

//            properties.load(new FileInputStream(new File("C:\\Users\\Nati\\IdeaProjects\\AndroidEmulator\\src\\main\\resources\\nexus9_26_api.properties")));

            Map<String, String> bootConfig = new HashMap<>();
            Map<String, String> hardwareProperties = new HashMap<>();
            hardwareProperties.putAll(((Map) properties));
            SystemImage currentSystemImage = null;
            for (SystemImage systemImage : systemImageManager.getImages()) {
                if (systemImage.getAndroidVersion().getApiLevel() == 26) {
                    currentSystemImage = systemImage;
                    break;
                }
            }
            AvdInfo avdInfo = avdManager
                    .createAvd(
                            new File(avdManager.getBaseAvdFolder().getAbsolutePath() + File.separator + name + ".avd"),
                            name,
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
            return new Emulator(EMULATOR,avdInfo,avdManager);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Emulator createEmulator(String name, Emulators emulator) {
        Properties properties = new Properties();
        try {
            properties.load(emulator.getStreamProps());
            properties.setProperty("avd.ini.displayname", name);
            String skinPath = properties.getProperty("skin.path");
            properties.setProperty("skin.path", String.format(skinPath, ANDROID_HOME));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createEmulator(name,properties);
    }

    public List<Emulator> getAllEmulators(){
        List<Emulator> emulators = new ArrayList<>();
        for (AvdInfo avdInfo:avdManager.getAllAvds()){
            emulators.add(new Emulator(EMULATOR,avdInfo,avdManager));
        }
        return emulators;
    }


}
