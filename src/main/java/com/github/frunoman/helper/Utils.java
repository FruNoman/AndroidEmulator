package com.github.frunoman.helper;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class Utils {

    public static boolean available(int port) {
        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }
        return false;
    }

    public static int getFreePortInRange(int start,int end){
        for (int x=start;x<end;x++){
            if (available(x)){
                return x;
            }
        }
        return 5800;
    }

}
