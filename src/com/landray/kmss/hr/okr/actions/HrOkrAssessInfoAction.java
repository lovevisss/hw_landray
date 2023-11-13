package com.landray.kmss.hr.okr.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.okr.service.IHrOkrAssessInfoService;

import javax.servlet.http.HttpServletRequest;

public class HrOkrAssessInfoAction extends ExtendAction {
    private IHrOkrAssessInfoService hrOkrAssessInfoService;
    @Override
    protected IBaseService getServiceImp(HttpServletRequest httpServletRequest) {
        if(hrOkrAssessInfoService == null){
            hrOkrAssessInfoService = (IHrOkrAssessInfoService) getBean("hrOkrAssessInfoService");
        }
        return hrOkrAssessInfoService;
    }
}
