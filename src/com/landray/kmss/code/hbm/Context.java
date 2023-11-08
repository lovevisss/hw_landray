package com.landray.kmss.code.hbm;

import com.landray.kmss.util.ClassUtils;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;

import java.io.BufferedWriter;

public class Context {
    public final static int OUTPUTEMPTYATT = 1;
    public final static int CHECKRESOURCEKEY = 2;
    public final static int ENCODEATTVALUE = 4;
    public BufferedWriter output;
    public Class modelClass;
    public String tablename;
    public String bundle;

    public Context(HbmClass hbmClass, BufferedWriter output) throws Exception {
        this.output = output;
        modelClass = ClassUtils.forNameNullable(hbmClass.getName());
        tableName = ModelUtil.getModelTableName(hbmClass.getName());
        int i = hbmClass.getName().lastIndexOf(".model.");
        bundle = hbmClass.getName().substring("com.landray.kmss.".length(), i);
        bundle = StringUtil.replace(bundle, ".", "-");
    }
}
