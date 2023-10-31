package com.landray.kmss.code.struts;

import com.landray.kmss.code.util.XMLReaderUtil;

import java.io.File;

public class StrutsConfig {
    public ActionMapping[] getActionMappings() {
        return null;
    }

    public static StrutsConfig getInstance(File file) throws Exception {
        return (StrutsConfig) XMLReaderUtil.getInstance(file, StrutsConfig.class);
    }


}
