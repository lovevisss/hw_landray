package com.landray.kmss.code.struts;

import com.landray.kmss.code.util.XMLReaderUtil;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Data
public class StrutsConfig {

    public static StrutsConfig getInstance(File file) throws Exception {
        return (StrutsConfig) XMLReaderUtil.getInstance(file, StrutsConfig.class);
    }

    private List<FormBean> formBeans = new ArrayList<FormBean>();
    public void addFormBean(FormBean formBean) {
        this.formBeans.add(formBean);
    }
    private List<ActionMapping> actionMappings = new ArrayList<ActionMapping>();
    public void addActionMapping(ActionMapping actionMapping) {
        this.actionMappings.add(actionMapping);
    }
    private MessageResources messageResources;


}
