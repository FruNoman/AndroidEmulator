package com.github.frunoman;

import com.android.prefs.AndroidLocation;
import com.android.sdklib.internal.avd.AvdInfo;
import com.android.sdklib.internal.avd.AvdManager;
import com.github.frunoman.helper.iLoggerImpl;

import java.io.IOException;

public class Emulator {
    private AvdManager avdManager;
    private String EMULATOR;
    private AvdInfo avdInfo;
    private Process process;
    private iLoggerImpl iLogger;

    public Emulator(String EMULATOR, AvdInfo avdInfo, AvdManager avdManager) {
        this.EMULATOR = EMULATOR;
        this.avdInfo = avdInfo;
        this.avdManager = avdManager;
        this.iLogger = new iLoggerImpl();

    }

    public AvdInfo run() {
        try {
            process = Runtime.getRuntime().exec(new String[]{EMULATOR, "-avd", avdInfo.getName()});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return avdInfo;
    }


    public void stop() {
        try {
            avdManager.stopAvd(avdInfo);
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return avdInfo.getName();
    }

    public AvdInfo getAvdInfo() {
        return avdInfo;
    }

    public boolean delete(){
        return avdManager.deleteAvd(avdInfo,iLogger);
    }

    public AvdInfo reload(){
        try {
            return avdManager.reloadAvd(avdInfo,iLogger);
        } catch (AndroidLocation.AndroidLocationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isRunning(){
        return avdManager.isAvdRunning(avdInfo,iLogger);
    }

    @Override
    public String toString() {
        return avdInfo.getName();
    }
}
