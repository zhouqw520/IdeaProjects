//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dcits.j1.dbexport;

import java.io.File;

public abstract class IDBObjectScript {
    public IDBObjectScript() {
    }

    public abstract String getScript();

    public void getScriptAndWrite(String objectOwner, String objectType, String objectName, File file) throws Exception {
    }
}
