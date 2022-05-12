package org.pignat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Version {
    static private String version = "ERROR:NO_VERSION";

    static {
        init();
    }

    private static void init() {
        final String f = "/res/generated/version.txt";
        String v = null;
        try {
            InputStream in = Version.class.getResourceAsStream(f);
            if (in == null) {
                System.err.println(String.format("resource '%s' not found, not using the .jar? version will be wrong", f));
                return;
            }
            BufferedReader b = new BufferedReader(new InputStreamReader(in));
            if (b.ready()) {
                v = b.readLine();
            }
            if (v.contains("dirty") || v.contains("-")) {
                System.err.println(String.format("WARNING: using non-release version '%s'", v));
            }
            version = v;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(String.format("Failed to parse : %s'", v));
            e.printStackTrace();
        }
    }

    public static String getVersion() {
        return version;
    }
}
