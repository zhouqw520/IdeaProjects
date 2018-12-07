//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dcits.j1.dbexport;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnFactory {
    private static Connection CONN;

    public ConnFactory() {
    }

    public static Connection getConnection() throws Exception {
        if (CONN == null) {
            ConfigInfo config = ConfigInfo.getInstance();
            StringBuilder connStr = new StringBuilder();
            connStr.append("jdbc:oracle:thin:@//");
            connStr.append(config.getString("host"));
            connStr.append("/").append(config.getString("serviceid"));
            String user = config.getString("dbuser");
            String password = config.getString("dbpwd");
            CONN = DriverManager.getConnection(connStr.toString(), user, password);
        }

        return CONN;
    }

    public static void closeConnection() throws Exception {
        if (CONN != null) {
            CONN.close();
            CONN = null;
        }

    }
}
