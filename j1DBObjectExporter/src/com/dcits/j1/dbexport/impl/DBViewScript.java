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

public class DBViewScript extends IDBObjectScript {
    private String owner;
    private String name;

    public DBViewScript(String objectOwner, String objectName) {
        this.owner = objectOwner.toUpperCase();
        this.name = objectName.toUpperCase();
    }

    public String getScript() {
        StringBuilder sb = new StringBuilder();
        sb.append("Prompt view ");
        sb.append(this.owner).append('.').append(this.name);
        sb.append(" created\n");

        try {
            sb.append(this.getViewScript2()).append(";\n");
        } catch (Exception var3) {
            Logger.getLogger(DBViewScript.class.getName()).log(Level.SEVERE, (String)null, var3);
            return "";
        }

        return sb.toString();
    }

    private String getViewScript2() throws Exception {
        Connection conn = ConnFactory.getConnection();
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT text_length, text from all_views where owner=? and view_name=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, this.owner);
        stmt.setString(2, this.name);
        ResultSet rs = stmt.executeQuery();
        sb.append("CREATE OR REPLACE FORCE VIEW ").append(this.owner).append(".").append(this.name).append(" AS\n");
        if (rs.next()) {
            long sLength = rs.getLong("text_length");
            String sText = rs.getString("text");
            sb.append(sText.replaceAll("[\\s|\\r|\\n]+$", ""));
        }

        return sb.toString();
    }

    private String getViewScript() throws Exception {
        Connection conn = ConnFactory.getConnection();
        String sql = "SELECT dbms_metadata.get_ddl('VIEW',?,?) script from dual";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, this.name);
        stmt.setString(2, this.owner);
        ResultSet rs = stmt.executeQuery();
        String viewScript = "";
        if (rs.next()) {
            viewScript = rs.getString("SCRIPT");
        }

        rs.close();
        stmt.close();
        return viewScript;
    }
}
