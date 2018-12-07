//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dcits.j1.dbexport.impl;

import com.dcits.j1.dbexport.ConfigInfo;
import com.dcits.j1.dbexport.ConnFactory;
import com.dcits.j1.dbexport.IDBObjectScript;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBTableDataScript extends IDBObjectScript {
    private String owner;
    private String name;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DBTableDataScript(String objectOwner, String objectName) {
        this.owner = objectOwner;
        this.name = objectName;
    }

    public String getScript() {
        ConfigInfo config = ConfigInfo.getInstance();
        StringBuilder sb = new StringBuilder();
        System.out.println(this.owner + "." + this.name + " <<------------------- 导出");
        sb.append("prompt ").append(this.owner).append('.').append(this.name).append(" Created on \n");
        sb.append("set feedback off\n");
        sb.append("set define off\n");
        sb.append("delete from ").append(this.owner).append('.').append(this.name).append(";\n");
        sb.append("commit;\n");
        StringBuilder sb_sql = new StringBuilder();
        sb_sql.append("select * from ").append(this.owner).append('.').append(this.name);
        sb_sql.append(this.getPKOrderSql(this.owner, this.name));

        try {
            Connection conn = ConnFactory.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(sb_sql.toString());
            ResultSetMetaData tableMetaData = rs.getMetaData();
            StringBuilder sb_insertSqlPre = new StringBuilder();
            sb_insertSqlPre.append("insert into ");
            sb_insertSqlPre.append(this.owner).append('.').append(this.name);
            sb_insertSqlPre.append("(");

            int i;
            for(i = 1; i <= tableMetaData.getColumnCount(); ++i) {
                if (i > 1) {
                    sb_insertSqlPre.append(',');
                }

                sb_insertSqlPre.append(tableMetaData.getColumnName(i));
            }

            sb_insertSqlPre.append(") values(\n");

            while(rs.next()) {
                sb.append(sb_insertSqlPre);

                for(i = 1; i <= tableMetaData.getColumnCount(); ++i) {
                    if (i > 1) {
                        sb.append(',');
                    }

                    String colName = tableMetaData.getColumnName(i);
                    String tmpData;
                    switch(tableMetaData.getColumnType(i)) {
                    case -16:
                    case -15:
                    case -9:
                    case -1:
                    case 1:
                    case 12:
                        byte[] tmpB = rs.getBytes(colName);
                        if (rs.wasNull()) {
                            sb.append("NULL");
                        } else {
                            sb.append("'");
                            tmpData = new String(tmpB, config.getString("encoding"));
                            tmpData = tmpData.replace("'", "''");
                            sb.append(tmpData);
                            sb.append("'");
                        }
                        break;
                    case -6:
                    case -5:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 8:
                        tmpData = rs.getString(colName);
                        if (rs.wasNull()) {
                            sb.append("NULL");
                        } else {
                            sb.append(tmpData);
                        }
                        break;
                    case 91:
                    case 92:
                    case 93:
                        Timestamp tmpDate = rs.getTimestamp(colName);
                        if (rs.wasNull()) {
                            sb.append("NULL");
                        } else {
                            String datestr = this.sdf.format(tmpDate);
                            sb.append("to_date('").append(datestr);
                            sb.append("','YYYY-MM-DD HH24:MI:SS')");
                        }
                    }
                }

                sb.append(");\n");
            }

            rs.close();
        } catch (Exception var14) {
            Logger.getLogger(DBTableDataScript.class.getName()).log(Level.SEVERE, (String)null, var14);
        }

        sb.append("commit;\n");
        sb.append("set feedback on\n");
        sb.append("set define on\n");
        return sb.toString();
    }

    public void getScriptAndWrite(String objectOwner, String objectType, String objectName, File file) throws Exception {
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        ConfigInfo config = ConfigInfo.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append("prompt ").append(objectOwner).append('.').append(objectName).append(" Created on \n");
        sb.append("set feedback off\n");
        sb.append("set define off\n");
        sb.append("delete from ").append(objectOwner).append('.').append(objectName).append(";\n");
        sb.append("commit;\n");
        StringBuilder sb_sql = new StringBuilder();
        sb_sql.append("select * from ").append(objectOwner).append('.').append(objectName);
        sb_sql.append(this.getPKOrderSql(objectOwner, objectName));

        try {
            Connection conn = ConnFactory.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(sb_sql.toString());
            ResultSetMetaData tableMetaData = rs.getMetaData();
            StringBuilder sb_insertSqlPre = new StringBuilder();
            sb_insertSqlPre.append("insert into ");
            sb_insertSqlPre.append(objectOwner).append('.').append(objectName);
            sb_insertSqlPre.append("(");

            int i;
            for(i = 1; i <= tableMetaData.getColumnCount(); ++i) {
                if (i > 1) {
                    sb_insertSqlPre.append(',');
                }

                sb_insertSqlPre.append(tableMetaData.getColumnName(i));
            }

            sb_insertSqlPre.append(") values(\n");
            fw.write(sb.toString());

            while(rs.next()) {
                sb = new StringBuilder();
                sb.append(sb_insertSqlPre);

                for(i = 1; i <= tableMetaData.getColumnCount(); ++i) {
                    if (i > 1) {
                        sb.append(',');
                    }

                    String colName = tableMetaData.getColumnName(i);
                    String tmpData;
                    switch(tableMetaData.getColumnType(i)) {
                    case -16:
                    case -15:
                    case -9:
                    case -1:
                    case 1:
                    case 12:
                        byte[] tmpB = rs.getBytes(colName);
                        if (rs.wasNull()) {
                            sb.append("NULL");
                        } else {
                            sb.append("'");
                            tmpData = new String(tmpB, config.getString("encoding"));
                            tmpData = tmpData.replace("'", "''");
                            sb.append(tmpData);
                            sb.append("'");
                        }
                        break;
                    case -6:
                    case -5:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 8:
                        tmpData = rs.getString(colName);
                        if (rs.wasNull()) {
                            sb.append("NULL");
                        } else {
                            sb.append(tmpData);
                        }
                        break;
                    case 91:
                    case 92:
                    case 93:
                        Timestamp tmpDate = rs.getTimestamp(colName);
                        if (rs.wasNull()) {
                            sb.append("NULL");
                        } else {
                            String datestr = this.sdf.format(tmpDate);
                            sb.append("to_date('").append(datestr);
                            sb.append("','YYYY-MM-DD HH24:MI:SS')");
                        }
                    }
                }

                sb.append(");\n");
                fw.write(sb.toString());
            }

            sb = new StringBuilder();
            rs.close();
        } catch (Exception var19) {
            Logger.getLogger(DBTableDataScript.class.getName()).log(Level.SEVERE, (String)null, var19);
        }

        sb.append("commit;\n");
        sb.append("set feedback on\n");
        sb.append("set define on\n");
        fw.write(sb.toString());
        fw.close();
    }

    private String getPKOrderSql(String objectOwner, String objectName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select b.COLUMN_NAME from all_constraints a ,all_cons_columns b");
        sb.append(" where a.owner='").append(objectOwner).append("' ");
        sb.append(" and a.TABLE_NAME='").append(objectName).append("' and CONSTRAINT_TYPE='P'");
        sb.append(" and a.OWNER=b.OWNER");
        sb.append(" and a.CONSTRAINT_NAME=b.CONSTRAINT_NAME");
        sb.append(" and a.TABLE_NAME=b.TABLE_NAME");
        sb.append(" order by b.POSITION");
        StringBuilder orderSql = new StringBuilder();

        try {
            Connection conn = ConnFactory.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(sb.toString());
            if (!rs.isAfterLast()) {
                orderSql.append(" order by ");

                for(; rs.next(); orderSql.append(rs.getString("COLUMN_NAME"))) {
                    if (!rs.isFirst()) {
                        orderSql.append(',');
                    }
                }
            }

            rs.close();
        } catch (Exception var7) {
            Logger.getLogger(DBTableDataScript.class.getName()).log(Level.SEVERE, (String)null, var7);
            return "";
        }

        return orderSql.toString();
    }
}
