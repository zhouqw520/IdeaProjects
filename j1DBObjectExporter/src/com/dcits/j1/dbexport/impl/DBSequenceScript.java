//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dcits.j1.dbexport.impl;

import com.dcits.j1.dbexport.ConnFactory;
import com.dcits.j1.dbexport.IDBObjectScript;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBSequenceScript extends IDBObjectScript {
    private String owner;
    private String name;

    public DBSequenceScript(String objectOwner, String objectName) {
        this.owner = objectOwner.toUpperCase();
        this.name = objectName.toUpperCase();
    }

    public String getScript() {
        StringBuilder sb = new StringBuilder();
        sb.append("-- Create sequence \n");

        try {
            sb.append(this.getSequenceScript());
        } catch (Exception var3) {
            Logger.getLogger(DBSequenceScript.class.getName()).log(Level.SEVERE, (String)null, var3);
            return "";
        }

        return sb.toString();
    }

    private StringBuilder getSequenceScript() throws Exception {
        Connection conn = ConnFactory.getConnection();
        String sql = "SELECT * from all_sequences t where t.SEQUENCE_owner=? and t.SEQUENCE_name=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, this.owner);
        stmt.setString(2, this.name);
        ResultSet rs = stmt.executeQuery();
        StringBuilder script = new StringBuilder();
        if (rs.next()) {
            script.append("create sequence ").append(this.name).append('\n');
            script.append("minvalue ").append(rs.getString("MIN_VALUE")).append('\n');
            script.append("maxvalue ").append(rs.getString("MAX_VALUE")).append('\n');
            script.append("start with ").append(rs.getString("MIN_VALUE")).append('\n');
            script.append("increment by ").append(rs.getString("INCREMENT_BY")).append('\n');
            String cacheSize = rs.getString("CACHE_SIZE");
            if ("0".equals(cacheSize)) {
                script.append("nocache");
            } else {
                script.append("cache ").append(cacheSize);
            }

            if ("Y".equalsIgnoreCase(rs.getString("CYCLE_FLAG"))) {
                script.append("\ncycle");
            }

            script.append(";\n");
        }

        rs.close();
        stmt.close();
        return script;
    }
}
