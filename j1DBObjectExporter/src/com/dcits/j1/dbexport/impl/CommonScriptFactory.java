//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dcits.j1.dbexport.impl;

import com.dcits.j1.dbexport.IDBObjectScript;
import com.dcits.j1.dbexport.IScriptFactory;
import java.util.ArrayList;
import java.util.List;

public class CommonScriptFactory implements IScriptFactory {
    public CommonScriptFactory() {
    }

    public IDBObjectScript getScriptObject(String objectOwner, String objectType, String objectName) {
        List<String> sourceObjects = new ArrayList();
        sourceObjects.add("FUNCTION");
        sourceObjects.add("PACKAGE");
        sourceObjects.add("PACKAGE BODY");
        sourceObjects.add("PROCEDURE");
        sourceObjects.add("TRIGGER");
        sourceObjects.add("TYPE");
        if ("TABLE".equalsIgnoreCase(objectType)) {
            return new DBTableScript(objectOwner, objectName);
        } else if ("VIEW".equalsIgnoreCase(objectType)) {
            return new DBViewScript(objectOwner, objectName);
        } else if ("SEQUENCE".equalsIgnoreCase(objectType)) {
            return new DBSequenceScript(objectOwner, objectName);
        } else if (sourceObjects.contains(objectType)) {
            return new DBSourceScript(objectOwner, objectName, objectType);
        } else if ("DATA".equalsIgnoreCase(objectType)) {
            return new DBTableDataScript(objectOwner, objectName);
        } else {
            return "SYNONYM".equalsIgnoreCase(objectType) ? new DBSynonymScript(objectOwner, objectName) : null;
        }
    }
}
