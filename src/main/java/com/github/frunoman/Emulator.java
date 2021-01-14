package com.github.frunoman;

import com.android.prefs.AndroidLocation;
import com.android.sdklib.internal.avd.AvdInfo;
import com.android.sdklib.internal.avd.AvdManager;
import com.github.frunoman.helper.Utils;
import com.github.frunoman.helper.iLoggerImpl;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Emulator {
    private AvdManager avdManager;
    private String EMULATOR;
    private AvdInfo avdInfo;
    private Process process;
    private iLoggerImpl iLogger;
    private int port;
    private String serial;
    private static final Logger logger = Logger.getLogger(Emulator.class);

    public Emulator(String EMULATOR, AvdInfo avdInfo, AvdManager avdManager) {
        this.EMULATOR = EMULATOR;
        this.avdInfo = avdInfo;
        this.avdManager = avdManager;
        this.iLogger = new iLoggerImpl();
    }

    public AvdInfo run() {
        try {
            this.port = Utils.getFreePortInRange(5554, 5654);
            process = Runtime.getRuntime().exec(new String[]{EMULATOR, "-avd", avdInfo.getName(), "-port", String.valueOf(port)});
            Thread.sleep(2000);
            this.serial = "emulator-" + port;
            logger.debug("Run emulator ["+avdInfo.getName()+"] with serial ["+serial+"]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avdInfo;
    }


    public void stop() {
        try {
            logger.debug("Stop emulator ["+avdInfo.getName()+"] with serial ["+serial+"]");
            avdManager.stopAvd(avdInfo);
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        logger.debug("Get emulator name");
        return avdInfo.getName();
    }

    public AvdInfo getAvdInfo() {
        logger.debug("Get emulator AvdInfo");
        return avdInfo;
    }

    public boolean delete() {
        logger.debug("Delete emulator ["+avdInfo.getName()+"]");
        return avdManager.deleteAvd(avdInfo, iLogger);
    }

    public AvdInfo reload() {
        try {
            logger.debug("Reload emulator ["+avdInfo.getName()+"]");
            return avdManager.reloadAvd(avdInfo, iLogger);
        } catch (AndroidLocation.AndroidLocationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isRunning() {
        boolean run = avdManager.isAvdRunning(avdInfo, iLogger);
        logger.debug("Emulator running ["+run+"]");
        return run;
    }

    public String getSerial() {
        if (!isRunning()) {
          throw new RuntimeException("Emulator not running");
        }
        return serial;
    }

    @Override
    public String toString() {
        return avdInfo.getName();
    }
}
