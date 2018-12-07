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

public class DBSynonymScript extends IDBObjectScript {
    private String owner;
    private String name;

    public DBSynonymScript(String objectOwner, String objectName) {
        this.owner = objectOwner.toUpperCase();
        this.name = objectName.toUpperCase();
    }

    public String getScript() {
        StringBuilder sb = new StringBuilder();
        sb.append("-- Create Synonym \n");

        try {
            sb.append(this.getSynonymScript());
        } catch (Exception var3) {
            Logger.getLogger(DBSynonymScript.class.getName()).log(Level.SEVERE, (String)null, var3);
            return "";
        }

        return sb.toString();
    }

    private StringBuilder getSynonymScript() throws Exception {
        Connection conn = ConnFactory.getConnection();
        String sql = "SELECT * from all_synonyms t where t.owner=? and t.synonym_name=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, this.owner);
        stmt.setString(2, this.name);
        ResultSet rs = stmt.executeQuery();
        StringBuilder script = new StringBuilder();
        if (rs.next()) {
            script.append("create or replace synonym ").append(this.owner).append('.').append(this.name).append('\n');
            script.append("  for ").append(rs.getString("TABLE_OWNER")).append('.').append(rs.getString("TABLE_NAME"));
            script.append(";\n");
        }

        rs.close();
        stmt.close();
        return script;
    }
}
