package com.landray.kmss.hr.okr.model;


import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.lbmpservice.interfaces.IsysLbmpMainModel;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import lombok.Data;

import java.util.Date;

@Data
public class HrOkrMain extends ExtendAuthModel implements IsysLbmpMainModel, IAttachment {
    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdAssessPeriod;

    private String fdAssessTime;

    private String docSubject;

    private String fdGradeInfo;

    private String fdGradeResult;

    private Date docAlterTime;

    private Integer fdNumber;

//    private SysOrgPerson docAlteror;
//
//    private HrOkrTemplate docTemplate;

    private String fdDescription;

}
