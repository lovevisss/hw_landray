package com.landray.kmss.code.fix;

import com.landray.kmss.code.dict.DataDictTool;
import com.landray.kmss.code.hbm.HbmClass;
import com.landray.kmss.code.hbm.HbmId;
import com.landray.kmss.code.hbm.HbmManyToOne;
import com.landray.kmss.code.hbm.HbmOneToOne;
import com.landray.kmss.sys.config.dict.util.XmlJsonDictType;
import com.landray.kmss.util.ObjectUtil;
import com.landray.kmss.util.StringUtil;
import lombok.Data;
import net.sf.json.JSONObject;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Data
public class FixContext {
    File file;
    String simpleName;
    private String bundle;
    public boolean modify;
    public Class<?> clazz;
    private HbmClass hbm;

    boolean logFix;
    private ResourceBundle resource;

    public HbmClass getHbm() {
        return hbm;
    }

    public boolean isModify() {
        return modify;
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public void setHbm(HbmClass hbm) {
        this.hbm = hbm;
    }

    public FixContext(File file, String modelName, boolean logFix) {
    }

    public void log(String s) {
    }

    public void logFix(String obj, String oldKey, String newKey) {
        modify = true;
        if(!logFix){
            return;
        }
        String message = "修复" + obj + "：" + oldKey + " -> " + newKey;
        log(message);
    }

    /**
     * 修复messageKey
     *
     * @param json
     * @param fieldName
     * @param dataDictTool
     */
    public void fixMessage(JSONObject json, String fieldName, DataDictTool dataDictTool) {
        String oldKey = json.optString("messageKey");
        if(StringUtil.isNotNull(oldKey)){  //包含是否为null 和空字符串的情况
            int index = oldKey.indexOf(":");  //获取格式"sys-doc:sysDocBaseInfo.docSubject"
            String bundle = "ApplicationResources";
            String key = oldKey;
            if(index > -1){
                bundle = "com.landray.kmss." + oldKey.substring(0,index).replace("-", ".").trim() + ".ApplicationResources"; // com.landray.kmss.sys.doc
                key = oldKey.substring(index +1).trim(); // sysDocBaseInfo.docSubject
            }
            try {
                /**
                 * 获取对应资源文件 ApplicationResources.properties 中的值  查找是否存在key
                 */
                if(ResourceBundle.getBundle(bundle).containsKey(key)){
                    dataDictTool.fixCanDisplay(this, json, fieldName, false);
                    return;
                }
            }catch (MissingResourceException e){
                log("错误：无法找到资源文件：" + bundle);
            }
        }

//        语言包
        if(getResource() != null){
            if(fieldName == null){
                String newKey = "table." + getSimpleName();
                if(getResource().containsKey(newKey)){
                    newKey = getBundle() + ":" + newKey;
                    json.put("messageKey", newKey);
                    logFix("model.messageKey", oldKey, newKey);
                    return;
                }
            } else {
                String newKey = getSimpleName() + "." + fieldName;
                if(getResource().containsKey(newKey)){
                    newKey = getBundle() + ":" + newKey;
                }else if(getResource().containsKey(newKey + "Id")){
                    newKey = getBundle() + ":" + newKey + "Id";
                }else if(getResource().containsKey(newKey + "Name")){
                    newKey = getBundle() + ":" + newKey + "Name";
                }else{
                    newKey = DataDictTool.MESSAGE_KEYS.get(fieldName);
                }
                if(newKey != null){
                    json.put("messageKey", newKey);
                    logFix(fieldName + ".messageKey", oldKey, newKey);
                    dataDictTool.fixCanDisplay(this, json, fieldName, false);
                    return;
                }
            }
        }
        dataDictTool.fixCanDisplay(this, json, fieldName, true);
//        读取不了打印错误
        if(StringUtil.isNull(oldKey)){
            if( DataDictTool.LOG_WARN && fieldName == null){
                log("警告：messageKey为空");
            } else if(DataDictTool.LOG_WARN && !"fdId".equals(fieldName) && !"false".equals(json.optString("canDisplay"))){
                log("警告：" + fieldName + ".messageKey为空");
            }
        } else{
            log("错误：" + (fieldName == null ? "model":fieldName) + ".messageKey" + oldKey);
        }
    }

    public void fixHbmId(JSONObject jsonProperty, HbmId obj) {
        String assigned = obj.getGenerator() == null ? "assigned" : obj.getGenerator().getType();
        String value = "{\"propertyType\":\"id\",\"generator\":{\"type\":\"" + assigned + "\"}}";
        String oldValue = jsonProperty.toString();
        if(value.equals(oldValue)){
            return;
        }
        jsonProperty.clear();
        jsonProperty.putAll(JSONObject.fromObject(value));
        logFix("fdId", oldValue, value);
    }

    public void replaceFix(JSONObject jsonProperty, String key, String value, String string) {
        String oldVal = jsonProperty.optString(key, null);
        if(StringUtil.isNull(oldVal)){
            oldVal = null;
        }
        if(ObjectUtil.equals(oldVal, value)){
            return;
        }
        jsonProperty.put(key, value);
        logFix(string + "." +key, oldVal, value);

    }

    public void fixPropertyType(JSONObject json, String name, String type, DataDictTool dataDictTool) {
        String oldVal = json.optString("type");
        String newVal = dataDictTool.getPropertyType(type);
        if(newVal == null && getClazz() != null){
            PropertyDescriptor descriptor = ObjectUtil.getPropertyDescriptor(getClazz(), name);
            if(descriptor != null){
                newVal = dataDictTool.getPropertyType(descriptor.getPropertyType().getName());
            }
        }
        if(newVal == null || "DateTime".equals(newVal) && "Date".equals(oldVal) || "Time".equals(oldVal) ){
            return;
        }
        replaceFix(json, "type", newVal, name);
    }

    public void fixHbmOneToOne(JSONObject jsonProperty, HbmOneToOne obj, DataDictTool dataDictTool) {
        replaceFix(jsonProperty, "propertyType", XmlJsonDictType.COMPLEX.getJsonName(), obj.getName());
        fixMessage(jsonProperty, obj.getName(), dataDictTool);
        fixPropertyType(jsonProperty, obj.getName(), obj.getType(), dataDictTool);
        replaceFix(jsonProperty, "column", "fdId", obj.getName());
        replaceFix(jsonProperty, "notNull", "true", obj.getName());
        replaceFix(jsonProperty, "unique", "true", obj.getName());
        replaceFix(jsonProperty, "cascade", obj.getCascade(), obj.getName());
        replaceFix(jsonProperty, "constrained", obj.getConstrained(), obj.getName());
    }

    public void fixHbmManyToOne(JSONObject jsonProperty, HbmManyToOne obj, DataDictTool dataDictTool) {
        replaceFix(jsonProperty, "propertyType", XmlJsonDictType.MODEL.getJsonName(), obj.getName());
        fixMessage(jsonProperty, obj.getName(), dataDictTool);
        fixPropertyType(jsonProperty, obj.getName(), obj.getType(), dataDictTool);
        replaceFix(jsonProperty, "column", obj.getColumn(), obj.getName());
        replaceFix(jsonProperty, "notNull", "true", obj.getName());
        replaceFix(jsonProperty, "unique", "true", obj.getName());
        replaceFix(jsonProperty, "cascade", obj.getCascade(), obj.getName());
    }
}
