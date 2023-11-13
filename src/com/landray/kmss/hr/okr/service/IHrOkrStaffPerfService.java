package com.landray.kmss.hr.okr.service;

import com.landray.kmss.hr.okr.model.HrOkrMain;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
public interface IHrOkrStaffPerfService extends IExtendDataService{

    public abstract List<HrOkrStaffPerf> findByFdMain(HrOkrMain fdMain) throws Exception;

    public abstract List<HrOkrStaffPerf> findByFdMain(String fdId, String fdGroupId) throws Exception;

}
