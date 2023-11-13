package com.landray.kmss.sys.metadata.interfaces;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;

public class ExtendDataServiceImp extends BaseServiceImp implements IExtendDataService {

    @Override
    public String add(IBaseModel form, RequestContext requestContext) throws Exception {
        return null;
    }

    @Override
    public IBaseModel convertFormToModel(IBaseModel form, IBaseModel model, RequestContext requestContext) throws Exception {
        return null;
    }

    @Override
    public IBaseModel convertFormToModel(IBaseModel form, IBaseModel model, ConvertorContext context) throws Exception {
        return null;
    }
}
