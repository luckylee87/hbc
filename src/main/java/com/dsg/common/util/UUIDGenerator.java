package com.dsg.common.util;

import java.io.Serializable;
import java.net.InetAddress;

public class UUIDGenerator {
    private static final int IP;
    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);
    private final static String sep = "";
    private static UUIDGenerator uuidGenerator = new UUIDGenerator();
    private static short counter = (short) 0;

    static {
        int ipadd;
        try {
            ipadd = IptoInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }

    public UUIDGenerator() {
    }

    public static String getUUID() {
        return uuidGenerator.generate().toString();
    }

    public static int IptoInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

    /**
     * Unique across JVMs on this machine (unless they load this class
     * in the same quater second - very unlikely)
     */
    protected int getJVM() {
        return JVM;
    }

    /**
     * Unique in a millisecond for this JVM instance (unless there
     * are > Short.MAX_VALUE instances created in a millisecond)
     */
    protected short getCount() {
        synchronized (UUIDGenerator.class) {
            if (counter < 0) counter = 0;
            return counter++;
        }
    }

    /**
     * Unique in a local network
     */
    protected int getIP() {
        return IP;
    }

    /**
     * Unique down to millisecond
     */
    protected short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    protected int getLoTime() {
        return (int) System.currentTimeMillis();
    }

    protected String format(int intval) {
        String formatted = Integer.toHexString(intval);
        StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    protected String format(short shortval) {
        String formatted = Integer.toHexString(shortval);
        StringBuffer buf = new StringBuffer("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    public Serializable generate() {
        return new StringBuffer(36)
                .append(format(getIP())).append(sep)
                .append(format(getJVM())).append(sep)
                .append(format(getHiTime())).append(sep)
                .append(format(getLoTime())).append(sep)
                .append(format(getCount()))
                .toString();
    }
}
