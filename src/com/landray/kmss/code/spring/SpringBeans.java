package com.landray.kmss.code.spring;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.landray.kmss.code.struts.StrutsConfig;
import com.landray.kmss.code.util.XMLReaderUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpringBeans {

    private static Logger logger = LoggerFactory.getLogger(SpringBeans.class);

    public static SpringBeans getInstance(File file) throws Exception {
        return (SpringBeans) XMLReaderUtil.getInstance(file, SpringBeans.class);
    }

    private List<SpringBean> beans = new ArrayList<SpringBean>();

    public void addBean(SpringBean bean) {
        beans.add(bean);
    }

    public static void main(String[] args) throws Exception {
        String path ="WebContent/WEB-INF/KmssConfig/dbcenter/flowstat/struts.xml";
        StrutsConfig beans =StrutsConfig.getInstance(new File(path));
        logger.info(beans.toString());
    }

}
