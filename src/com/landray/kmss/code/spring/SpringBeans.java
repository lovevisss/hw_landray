package com.landray.kmss.code.spring;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SpringBeans {
    private List<SpringBean> beans = new ArrayList<SpringBean>();


    public String getId() {
        return null;
    }
}
