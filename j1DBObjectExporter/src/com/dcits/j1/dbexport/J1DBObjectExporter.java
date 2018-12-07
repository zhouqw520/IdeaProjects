//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dcits.j1.dbexport;

import com.dcits.j1.dbexport.impl.CommonScriptFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class J1DBObjectExporter {
    public J1DBObjectExporter() {
    }

    private static String getSqlList(String[] list) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < list.length; ++i) {
            if (sb.length() > 0) {
                sb.append(",");
            }

            sb.append("'");
            sb.append(list[i].toUpperCase());
            sb.append("'");
        }

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        ConfigInfo config = ConfigInfo.init(args[0]);

        try {
            Connection conn = ConnFactory.getConnection();
            IScriptFactory scriptFactory = new CommonScriptFactory();
            StringBuffer sql_buf = new StringBuffer("select * from all_objects");
            String[] owners = config.getArray("owners");
            String[] tables = config.getArrayUpperCase("data_table");
            List<String> dataTableList = new ArrayList();
            Collections.addAll(dataTableList, tables);
            sql_buf.append(" where owner in (");
            sql_buf.append(getSqlList(config.getArray("owners")));
            sql_buf.append(")");
            sql_buf.append(" and object_type not in (");
            sql_buf.append(getSqlList(config.getArray("types_ignore")));
            sql_buf.append(")");
            if (!config.getString("types_only").equals("")) {
                sql_buf.append(" and object_type in (");
                sql_buf.append(getSqlList(config.getArray("types_only")));
                sql_buf.append(")");
            }

            DBOutput scriptOut = new DBOutput(config.getString("output_path"));
            ResultSet rs = conn.createStatement().executeQuery(sql_buf.toString());

            while(true) {
                String objectOwner;
                String objectType;
                String objectName;
                IDBObjectScript scriptGen;
                do {
                    do {
                        if (!rs.next()) {
                            rs.close();
                            return;
                        }

                        objectOwner = rs.getString("OWNER");
                        objectType = rs.getString("OBJECT_TYPE");
                        objectName = rs.getString("OBJECT_NAME");
                        scriptGen = scriptFactory.getScriptObject(objectOwner, objectType, objectName);
                        if (scriptGen != null) {
                            String filepath = scriptOut.writeScript(objectOwner, objectType, objectName, scriptGen.getScript());
                            System.out.print(objectName);
                            System.out.print(':');
                            System.out.println(filepath);
                        }
                    } while(!"TABLE".equalsIgnoreCase(objectType));
                } while(!dataTableList.contains(objectOwner + "." + objectName) && !dataTableList.contains(objectName));

                scriptGen = scriptFactory.getScriptObject(objectOwner, "DATA", objectName);
                File oFile = scriptOut.createFile(objectOwner, "DATA", objectName);
                System.out.print("表数据" + objectName);
                System.out.print(':');
                System.out.println(oFile.getPath());
                scriptGen.getScriptAndWrite(objectOwner, "DATA", objectName, oFile);
            }
        } catch (Exception var18) {
            var18.printStackTrace();
        } finally {
            ConnFactory.closeConnection();
        }

    }
}
