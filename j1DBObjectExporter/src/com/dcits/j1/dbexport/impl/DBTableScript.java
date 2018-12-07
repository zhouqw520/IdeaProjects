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

public class DBTableScript extends IDBObjectScript {
    private String owner;
    private String name;
    private boolean isTemporary;
    private String temptablescript;
    private String tableComments;

    public DBTableScript(String objectOwner, String objectName) {
        this.owner = objectOwner.toUpperCase();
        this.name = objectName.toUpperCase();
    }

    private void initTableComment() throws Exception {
        Connection conn = ConnFactory.getConnection();
        String sql = "select * from all_tab_comments t where t.owner=? and t.table_name=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, this.owner);
        stmt.setString(2, this.name);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            this.tableComments = rs.getString("COMMENTS");
            if (this.tableComments == null) {
                this.tableComments = "";
            }

            this.tableComments = this.tableComments.replace("'", "''");
            rs.close();
            stmt.close();
        } else {
            this.tableComments = "";
            rs.close();
            stmt.close();
        }

    }

    private void initTableInfo() throws Exception {
        Connection conn = ConnFactory.getConnection();
        String sql = "select * from all_tables t where t.owner=? and t.table_name=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, this.owner);
        stmt.setString(2, this.name);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            this.isTemporary = "Y".equalsIgnoreCase(rs.getString("TEMPORARY"));
            this.temptablescript = "";
            if (this.isTemporary) {
                if ("SYS$SESSION".equalsIgnoreCase(rs.getString("DURATION"))) {
                    this.temptablescript = "on commit preserve rows";
                } else {
                    this.temptablescript = "on commit delete rows";
                }
            }

            rs.close();
            stmt.close();
            this.initTableComment();
        } else {
            rs.close();
            stmt.close();
            throw new Exception("找不到表" + this.name);
        }
    }

    private String getColInfo() throws Exception {
        Connection conn = ConnFactory.getConnection();
        StringBuilder colInfo = new StringBuilder();
        String sql = "select COLUMN_NAME,DATA_TYPE,CHAR_LENGTH,CHAR_USED,DATA_PRECISION,DATA_SCALE,DATA_DEFAULT,NULLABLE from all_tab_columns a where a.owner = ? and a.table_name = ? order by COLUMN_ID";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, this.owner);
        stmt.setString(2, this.name);
        ResultSet rs = stmt.executeQuery();

        while(rs.next()) {
            if (colInfo.length() > 0) {
                colInfo.append(",\n");
            }

            String colName = rs.getString("COLUMN_NAME");
            colInfo.append("    ").append(colName);

            for(int i = colName.length() + 4; i < 40; ++i) {
                colInfo.append(' ');
            }

            String colType = rs.getString("DATA_TYPE");
            String colCharLength = rs.getString("CHAR_LENGTH");
            String colCharUsed = rs.getString("CHAR_USED");
            String colDataPrecision = rs.getString("DATA_PRECISION");
            String colDataScale = rs.getString("DATA_SCALE");
            if (!"CHAR".equalsIgnoreCase(colType) && !"NCHAR".equalsIgnoreCase(colType) && !colType.startsWith("NVARCHAR")) {
                if (colType.startsWith("VARCHAR")) {
                    colInfo.append(colType);
                    colInfo.append("(").append(colCharLength);
                    if ("C".equalsIgnoreCase(colCharUsed)) {
                        colInfo.append(" CHAR");
                    }

                    colInfo.append(")");
                } else if (colType.startsWith("NUMBER")) {
                    if (colDataPrecision != null) {
                        colInfo.append(colType);
                        colInfo.append("(");
                        colInfo.append(colDataPrecision);
                        if (colDataScale != null && !"0".equalsIgnoreCase(colDataScale)) {
                            colInfo.append(",").append(colDataScale);
                        }

                        colInfo.append(")");
                    } else if ("0".equalsIgnoreCase(colDataScale)) {
                        colInfo.append("INTEGER");
                    } else {
                        colInfo.append(colType);
                    }
                } else {
                    colInfo.append(colType);
                }
            } else {
                colInfo.append(colType);
                colInfo.append("(").append(colCharLength).append(")");
            }

            String colDefault = rs.getString("DATA_DEFAULT");
            if (colDefault != null) {
                colInfo.append(" DEFAULT ").append(colDefault.trim());
            }

            String colNullable = rs.getString("NULLABLE");
            if (colNullable.equalsIgnoreCase("N")) {
                colInfo.append(" NOT NULL");
            }
        }

        rs.close();
        stmt.close();
        colInfo.append("\n");
        return colInfo.toString();
    }

    public String getScript() {
        StringBuilder script = new StringBuilder();

        try {
            this.initTableInfo();
        } catch (Exception var6) {
            Logger.getLogger(DBTableScript.class.getName()).log(Level.SEVERE, (String)null, var6);
            return "";
        }

        script.append("set feedback off\n");
        script.append("define TABLESPACE_NAME=&1;\n");
        script.append("define INDEX_TABLESPACE_NAME=&2;\n\n");
        script.append("-- ============================================================\n");
        script.append("--   Table:  ").append(this.name).append("\n");
        script.append("--   汉字名称:  ").append(this.tableComments).append("\n");
        script.append("--   创建时间:\n");
        script.append("--   创建人:\n");
        script.append("--   修改日期:\n");
        script.append("--   修改人：\n");
        script.append("--   修改内容：\n");
        script.append("\n");
        script.append("-- ============================================================\n");
        if (this.isTemporary) {
            script.append("create global temporary table ");
        } else {
            script.append("create table ");
        }

        script.append(this.name);
        script.append("(\n");

        try {
            script.append(this.getColInfo());
        } catch (Exception var5) {
            Logger.getLogger(DBTableScript.class.getName()).log(Level.SEVERE, (String)null, var5);
        }

        if (this.isTemporary) {
            script.append(")   ").append(this.temptablescript).append(";\n");
        } else {
            script.append(") TABLESPACE &TABLESPACE_NAME;\n\n");
        }

        try {
            script.append(this.getComments());
        } catch (Exception var4) {
            Logger.getLogger(DBTableScript.class.getName()).log(Level.SEVERE, (String)null, var4);
        }

        try {
            script.append(this.getIndex());
        } catch (Exception var3) {
            Logger.getLogger(DBTableScript.class.getName()).log(Level.SEVERE, (String)null, var3);
        }

        script.append("\n");
        return script.toString();
    }

    private StringBuilder getComments() throws Exception {
        Connection conn = ConnFactory.getConnection();
        new StringBuilder();
        String sql = "SELECT c.* FROM all_col_comments c, all_tab_cols t WHERE c.OWNER = t.owner AND c.TABLE_NAME = t.TABLE_NAME and c.COLUMN_NAME = t.COLUMN_NAME and c.owner=? and c.TABLE_NAME=? ORDER BY t.COLUMN_ID";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, this.owner);
        stmt.setString(2, this.name);
        ResultSet rs = stmt.executeQuery();
        StringBuilder sb = new StringBuilder();
        sb.append("comment on table ").append(this.name).append(" is '");
        sb.append(this.tableComments).append("';\n\n");

        while(rs.next()) {
            String colName = rs.getString("COLUMN_NAME");
            String colComment = rs.getString("COMMENTS");
            if (colComment == null) {
                colComment = "";
            }

            colComment = colComment.replace("'", "''");
            sb.append("comment on column ").append(this.name).append('.').append(colName);
            sb.append(" is '");
            sb.append(colComment).append("';\n");
        }

        sb.append("\n");
        rs.close();
        stmt.close();
        return sb;
    }

    private StringBuilder getIndex() throws Exception {
        Connection conn = ConnFactory.getConnection();
        StringBuilder sb = new StringBuilder();
        String sql = "select * from all_indexes a where a.owner=? and a.TABLE_NAME=? and a.index_name not in (select CONSTRAINT_NAME from all_constraints c where c.owner=a.table_owner and c.table_name=a.table_name) order by index_name";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, this.owner);
        stmt.setString(2, this.name);

        ResultSet rs;
        for(rs = stmt.executeQuery(); rs.next(); sb.append(";")) {
            if (sb.length() > 0) {
                sb.append("\n");
            }

            sb.append("create ");
            String indexType = rs.getString("INDEX_TYPE");
            if (indexType.contains("FUNCTION-BASED")) {
                indexType = indexType.substring(15);
            }

            if (!indexType.contains("NORMAL")) {
                sb.append(indexType);
            }

            sb.append(" index ").append(rs.getString("INDEX_NAME"));
            sb.append(" on ").append(rs.getString("TABLE_NAME"));
            sb.append("(");
            sb.append(this.getIndexCol(rs.getString("OWNER"), rs.getString("INDEX_NAME")));
            if (this.isTemporary) {
                sb.append(") ");
            } else {
                sb.append(") tablespace &INDEX_TABLESPACE_NAME");
            }
        }

        rs.close();
        stmt.close();
        StringBuilder sb1 = this.getConstraint();
        if (sb1.length() > 0) {
            if (sb.length() > 0) {
                sb.append("\n");
            }

            sb.append(sb1);
        }

        return sb;
    }

    private StringBuilder getIndexCol(String indexOwner, String indexName) throws Exception {
        Connection conn = ConnFactory.getConnection();
        StringBuilder sb = new StringBuilder();
        String sql = "select * from all_ind_columns a where a.index_owner=? and a.index_NAME=? order by column_position";
        String sql2 = "select column_expression from all_ind_expressions a where a.index_owner=? and a.index_NAME=? and a.column_position = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        PreparedStatement stmt2 = conn.prepareStatement(sql2);
        stmt.setString(1, indexOwner);
        stmt.setString(2, indexName);

        ResultSet rs;
        String colName;
        for(rs = stmt.executeQuery(); rs.next(); sb.append(colName)) {
            if (sb.length() > 0) {
                sb.append(',');
            }

            colName = rs.getString("COLUMN_NAME");
            if (colName.startsWith("SYS_")) {
                stmt2.setString(1, indexOwner);
                stmt2.setString(2, indexName);
                stmt2.setInt(3, rs.getInt("COLUMN_POSITION"));
                ResultSet rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    colName = rs2.getString("COLUMN_EXPRESSION");
                }

                rs2.close();
            }
        }

        rs.close();
        stmt.close();
        return sb;
    }

    private StringBuilder getConstraint() throws Exception {
        Connection conn = ConnFactory.getConnection();
        StringBuilder sb = new StringBuilder();
        String sql = "select * from all_constraints a where a.owner=? and a.TABLE_NAME=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, this.owner);
        stmt.setString(2, this.name);
        ResultSet rs = stmt.executeQuery();

        while(rs.next()) {
            String consName = rs.getString("CONSTRAINT_NAME");
            String consType = rs.getString("CONSTRAINT_TYPE");
            if (!consName.startsWith("SYS_") && !consName.startsWith("BIN") && consType.equals("P")) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }

                sb.append("alter table ").append(this.name).append("\n");
                sb.append("add constraint ");
                sb.append(consName);
                sb.append(" primary key (");
                sb.append(this.getConstraintCol(rs.getString("OWNER"), consName));
                sb.append(") using INDEX TABLESPACE &INDEX_TABLESPACE_NAME;");
            }
        }

        rs.close();
        stmt.close();
        return sb;
    }

    private StringBuilder getConstraintCol(String consOwner, String consName) throws Exception {
        Connection conn = ConnFactory.getConnection();
        StringBuilder sb = new StringBuilder();
        String sql = "select * from all_cons_columns a where a.owner=? and a.constraint_NAME=? order by position";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, consOwner);
        stmt.setString(2, consName);

        ResultSet rs;
        for(rs = stmt.executeQuery(); rs.next(); sb.append(rs.getString("COLUMN_NAME"))) {
            if (sb.length() > 0) {
                sb.append(',');
            }
        }

        rs.close();
        stmt.close();
        return sb;
    }
}
