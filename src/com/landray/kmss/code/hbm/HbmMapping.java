package com.landray.kmss.code.hbm;

import com.landray.kmss.code.util.XMLReaderUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class HbmMapping {

    public static HbmMapping getInstance(String filePath) throws Exception {
        return (HbmMapping) XMLReaderUtil.getInstance(new File(filePath), HbmMapping.class);
    }

    private List classes = new ArrayList();
    private Map<String, HbmClass> hbmClasses;
    public List getClasses() {
        return classes;
    }

    public Map<String, HbmClass> getList(){
        if(hbmClasses.isEmpty())
        {
            initHbmClass();
        }
        return hbmClasses;
    }

    public void  initHbmClass(){
        for (int i=0; i < this.getClasses().size(); i++) {
            HbmClass hbm = (HbmClass) this.getClasses().get(i);
            hbmClasses.put(hbm.getName(), hbm);
        }
    }

    public void addClass(HbmClass hbmClass) {
        this.classes.add(hbmClass);
    }

    public void addSubclass(HbmSubclass subclass) {
        this.classes.add(subclass);
    }
}
