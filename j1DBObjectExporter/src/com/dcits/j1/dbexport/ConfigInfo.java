//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dcits.j1.dbexport;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConfigInfo {
    private static ConfigInfo config;
    Properties prop = new Properties();

    public static ConfigInfo init(String filepath) {
        config = new ConfigInfo(filepath);
        return config;
    }

    public static ConfigInfo getInstance() {
        return config;
    }

    private ConfigInfo(String filepath) {
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(filepath), "UTF-8");
            this.prop.load(in);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public String getString(String key) {
        return this.prop.getProperty(key);
    }

    public String[] getArray(String key) {
        String value = this.getString(key);
        if (value.isEmpty()) {
            String[] empty = new String[0];
            return empty;
        } else {
            return this.prop.getProperty(key).split(",");
        }
    }

    public String[] getArrayUpperCase(String key) {
        String[] tmp = this.getArray(key);

        for(int i = 0; i < tmp.length; ++i) {
            tmp[i] = tmp[i].toUpperCase();
        }

        return this.prop.getProperty(key).split(",");
    }
}
