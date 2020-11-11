package com.github.frunoman.helper;

import com.android.repository.api.ProgressIndicator;
import com.android.sdklib.internal.avd.AvdInfo;
import com.android.sdklib.internal.avd.AvdManager;
import com.android.sdklib.repository.AndroidSdkHandler;
import com.android.sdklib.repository.targets.SystemImageManager;
import com.android.utils.ILogger;
import com.github.frunoman.enums.Emulators;

import java.io.File;
import java.io.IOException;

public class Emulator {
    private String ANDROID_HOME;

    private AndroidSdkHandler sdkHandler;
    private SystemImageManager systemImageManager;
    private AvdManager avdManager;
    private ProgressIndicator progressIndicator;
    private ILogger iLogger;
    private String EMULATOR;
    private AvdInfo avdInfo;
    private Process process;

    public Emulator(String ANDROID_HOME,AndroidSdkHandler sdkHandler, SystemImageManager systemImageManager, AvdManager avdManager, ProgressIndicator progressIndicator, ILogger iLogger,AvdInfo avdInfo) {
        this.ANDROID_HOME = ANDROID_HOME;
        this.sdkHandler = sdkHandler;
        this.systemImageManager = systemImageManager;
        this.avdManager = avdManager;
        this.progressIndicator = progressIndicator;
        this.iLogger = iLogger;
        this.EMULATOR = ANDROID_HOME + File.separator + "tools" + File.separator + "emulator";
        this.avdInfo = avdInfo;

    }

    public AvdInfo run() throws Exception {
        if (avdInfo == null) {
//            avdInfo = createAvd(name, emulators);
        }
        try {
            process = Runtime.getRuntime().exec(new String[]{EMULATOR, "-avd", avdInfo.getName()});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return avdInfo;
    }


    public void stop() {
        if (avdInfo!=null) {
            avdManager.stopAvd(avdInfo);
            try {
                process.destroy();
            }catch (Exception e){

            }
        }
    }

    public String getName(){
        return avdInfo.getName();
    }

}
