package com.landray.kmss.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;

import java.util.List;

public class BaseServiceImp implements IBaseService {

    @Override
    public String getModelName() {
        return null;
    }

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        return null;
    }

    @Override
    public void delete(IBaseModel modelObj) throws Exception {

    }

    @Override
    public void flushHibernateSession() {

    }

    @Override
    public void clearHibernateSession() {

    }

    @Override
    public IBaseModel findByPrimaryKey(String id) throws Exception {
        return null;
    }

    @Override
    public IBaseModel findByPrimaryKey(String id, Object modelInfo, boolean noLazy) throws Exception {
        return null;
    }

    @Override
    public List findByPrimaryKeys(String[] ids) throws Exception {
        return null;
    }

    @Override
    public List findList(HQLInfo hqlInfo) throws Exception {
        return null;
    }

    @Override
    public List findList(String whereBlock, String orderBy) throws Exception {
        return null;
    }

    @Override
    public Page findPage(HQLInfo hqlInfo) throws Exception {
        return null;
    }

    @Override
    public Page findPage(String whereBlock, String orderBy, int pageNo, int pageSize) throws Exception {
        return null;
    }

    @Override
    public List findValue(HQLInfo hqlInfo) throws Exception {
        return null;
    }

    @Override
    public List findValue(String selectBlock, String whereBlock, String orderBy) throws Exception {
        return null;
    }

    @Override
    public void update(IBaseModel modelObj) throws Exception {

    }

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
