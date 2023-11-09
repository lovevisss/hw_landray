package com.landray.kmss.code.fix;

import com.landray.kmss.code.hbm.HbmClass;
import lombok.Data;

import java.io.File;
import java.util.ResourceBundle;

@Data
public class FixContext {
    File file;
    String simpleName;
    private String bundle;
    public boolean modify;
    public Class<?> clazz;
    private HbmClass hbm;

    boolean logFix;
    private ResourceBundle resource;

    public HbmClass getHbm() {
        return hbm;
    }

    public boolean isModify() {
        return modify;
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public void setHbm(HbmClass hbm) {
        this.hbm = hbm;
    }

    public FixContext(File file, String modelName, boolean logFix) {
    }

    public void log(String s) {
    }

    public void logFix(String s, String oldKey, String newKey) {
    }
}
