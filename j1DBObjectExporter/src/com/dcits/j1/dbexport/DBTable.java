//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dcits.j1.dbexport;

import java.sql.Connection;
import java.sql.ResultSet;

public class DBTable {
    private Connection conn;

    public DBTable(Connection conn) {
        this.conn = conn;
    }

    public ResultSet getTables() throws Exception {
        String sql = "SELECT * FROM ALL_TABLES WHERE OWNER='CTAIS2'";
        ResultSet rs = this.conn.prepareStatement(sql).executeQuery();
        return rs;
    }
}
