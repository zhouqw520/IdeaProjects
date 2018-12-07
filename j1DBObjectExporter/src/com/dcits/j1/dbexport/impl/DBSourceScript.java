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

public class DBSourceScript extends IDBObjectScript {
    private String owner;
    private String name;
    private String type;

    public DBSourceScript(String owner, String name, String type) {
        this.owner = owner;
        this.name = name;
        this.type = type;
    }

    public String getScript() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append("prompt ").append(this.type).append(" ").append(this.owner).append(".");
        sb.append(this.name).append(" created\n");
        sb.append("set define off\n");
        sb.append("create or replace ");

        try {
            Connection conn = ConnFactory.getConnection();
            String sql = "select * from all_source t where t.owner=? and t.name=? and t.type =? order by LINE";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, this.owner);
            stmt.setString(2, this.name);
            stmt.setString(3, this.type);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                sb.append(rs.getString("TEXT"));
            }

            rs.close();
            stmt.close();
        } catch (Exception var6) {
            Logger.getLogger(DBSourceScript.class.getName()).log(Level.SEVERE, (String)null, var6);
        }

        sb.append("\n/\nset define on\n");
        return sb.toString();
    }
}
