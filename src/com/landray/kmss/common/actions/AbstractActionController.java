package com.landray.kmss.common.actions;

import com.landray.kmss.code.struts.ActionMapping;
import com.landray.kmss.web.config.ActionConfig;
import com.landray.kmss.web.config.GlobalModuleConfig;
import com.landray.kmss.web.util.KmssSimpleTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 整合StructsAction 与 Spring MVC的Action
 *
 */
abstract class AbstractActionController extends AbstractController implements IStrutsActionSupport, InitializingBean, BeanNameAware {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 全局配置  单例
     */
    private GlobalModuleConfig globalModuleConfig = null;

    /**
     * 本action对应的配置实例
     */
    protected final ActionConfig actionConfig = new ActionConfig();

    /**
     * 只读版本的actionConfig
     */
    private ActionMapping localActionMapping;

    private ParameterNameDiscoverer parameterNameDiscoverer = null;

    private KmssSimpleTypeConverter simpleTypeConverter;

//    用于转换请求的参数  每个action都有各自的表单对象
    private WebBindingInitializer webBindingInitializer;

//    getters && setters
    public final void setGlobalModuleConfig(GlobalModuleConfig globalModuleConfig) {
        this.globalModuleConfig = globalModuleConfig;
    }

    public final GlobalModuleConfig getGlobalModuleConfig() {
        return globalModuleConfig;
    }

    public final void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }

    public final ParameterNameDiscoverer getParameterNameDiscoverer() {
        return parameterNameDiscoverer;
    }

    public final void setSimpleTypeConverter(KmssSimpleTypeConverter simpleTypeConverter) {
        this.simpleTypeConverter = simpleTypeConverter;
    }

    public final KmssSimpleTypeConverter getSimpleTypeConverter() {
        return simpleTypeConverter;
    }

    public final void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }

    public final WebBindingInitializer getWebBindingInitializer() {
        return webBindingInitializer;
    }

    /**
     * 如果在Bean里没有定义formName属性，则使用Bean的名称作为formName
     */
    public final String getFormName() {
        return actionConfig.getFormName();
    }

    public final void setFormName(String formName) {
        actionConfig.setFormName(formName);
    }

    public final String getPath() {
        return actionConfig.getPath();
    }

    public final void setPath(String path) {
        actionConfig.setPath(path);
    }

}
