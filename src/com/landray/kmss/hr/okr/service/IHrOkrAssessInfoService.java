package com.landray.kmss.hr.okr.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IHrOkrAssessInfoService extends IExtendDataService {
    void initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception;
}
