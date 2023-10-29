package com.landray.kmss.code.hbm;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HbmSubClass extends HbmClass{

    private String extendClass;
    private String discriminatorValue;
    private HbmJoin join;



}
