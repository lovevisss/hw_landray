package com.landray.kmss.code.dict;

import com.landray.kmss.code.fix.FixContext;
import com.landray.kmss.code.hbm.*;
import com.landray.kmss.code.spring.SpringBean;
import com.landray.kmss.code.spring.SpringBeans;
import com.landray.kmss.util.ClassUtils;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.code.util.XMLReaderUtil;
import com.landray.kmss.sys.config.dict.util.XmlJsonDictType;
import lombok.Data;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;



@Data
public abstract class DataDictTool {
    static String LOG_FILE; //静态变量  类型为String  用于存储日志文件  所有示例共享同一个值

    public static boolean LOG_WARN = true; //静态变量  类型为boolean  用于指示是否记录警告信息  所有示例共享同一个值

    private Logger logger = LoggerFactory.getLogger(this.getClass()); //实例变量  类型为Logger  用于记录日志  每个示例都有自己的值

    /*
     * 用于存储类型  这个 TYPES 变量的作用是提供一种方便的方式来进行类型映射，可以在需要的时候根据特定的类型字符串获取对应的目标类型字符串。
     */
    private static Map<String, String> TYPES = new HashMap<String, String>(); //静态变量  类型为Map<String,String>

    static {
        TYPES.put("boolean", "Boolean");
        TYPES.put("byte", "Byte");
        TYPES.put("short", "Integer");
        TYPES.put("int", "Integer");
        TYPES.put("long", "Long");
        TYPES.put("float", "Double");
        TYPES.put("double", "Double");
        TYPES.put("string", "String");
        TYPES.put("date", "DateTime");
        TYPES.put("clob", "RTF");
        TYPES.put("blob", "BLOB");
    }

    /**
     * 常见隐藏字段
     * 它们的区别在于参数的传递方式，一个是直接传递多个字符串参数，另一个是通过字符串数组来传递。效果上是相同的，生成的 List 对象都包含相同的元素。
     * Arrays
     * .asList(new String[] { "fdModelName", "fdModelId", "fdKey" });
     */
    private static List<String> HIDDEN_FIELDS = Arrays
            .asList("fdHierarchyId", "fdLastModified", "extendDataXML",
                    "extendFilePath", "authOtherReaders", "authOtherEditors",
                    "authAllReaders", "authAllEditors", "authNotReaderFlag",
                    "authReaderFlag", "authEditorFlag");
    private static List<String> MORE_HIDDEN_FIELDS = Arrays
            .asList("fdModelName", "fdModelId", "fdKey");
    public static Map<String, String> MESSAGE_KEYS = new HashMap<String, String>();

    static {
        MESSAGE_KEYS.put("docCreator", "sys-doc:sysDocBaseInfo.docCreator");
        MESSAGE_KEYS.put("docCreateTime", "sys-doc:sysDocBaseInfo.docCreateTime");
        MESSAGE_KEYS.put("docAlteror", "sys-doc:sysDocBaseInfo.docAlteror");
        MESSAGE_KEYS.put("docAlterTime", "sys-doc:sysDocBaseInfo.docAlterTime");
        MESSAGE_KEYS.put("docDept", "sys-doc:sysDocBaseInfo.docDept");
        MESSAGE_KEYS.put("docStatus", "sys-doc:sysDocBaseInfo.docStatus");
        MESSAGE_KEYS.put("docAuthor", "sys-doc:sysDocBaseInfo.docAuthor");
        MESSAGE_KEYS.put("docPublishTime", "sys-doc:sysDocBaseInfo.docPublishTime");
        MESSAGE_KEYS.put("docSubject", "sys-doc:sysDocBaseInfo.docSubject");
        MESSAGE_KEYS.put("docAlterClientIp", "sys-doc:sysDocBaseInfo.docAlterClientIP");
        MESSAGE_KEYS.put("docReadCount", "sys-doc:sysDocBaseInfo.docReadCount");
        MESSAGE_KEYS.put("docIsIntroduced", "sys-doc:sysDocBaseInfo.docIsIntroduced");
        MESSAGE_KEYS.put("docCategoryMain", "sys-doc:sysDocBaseInfo.docCategoryMain");
        MESSAGE_KEYS.put("docKeyword", "sys-doc:sysDocBaseInfo.docKeyword");
        MESSAGE_KEYS.put("docExpire", "sys-doc:sysDocBaseInfo.docExpire");
        MESSAGE_KEYS.put("docContent", "sys-doc:sysDocBaseInfo.docContent");
        MESSAGE_KEYS.put("docReaders", "sys-doc:sysDocBaseInfo.docReaders");
        MESSAGE_KEYS.put("docEditors", "sys-doc:sysDocBaseInfo.docEditors");
        MESSAGE_KEYS.put("docIsNewVersion", "sys-doc:sysDocBaseInfo.docIsNewVersion");
        MESSAGE_KEYS.put("docIsLocked", "sys-doc:sysDocBaseInfo.docIsLocked");
        MESSAGE_KEYS.put("docMainVersion", "sys-doc:sysDocBaseInfo.docMainVersion");
        MESSAGE_KEYS.put("docAuxiVersion", "sys-doc:sysDocBaseInfo.docAuxiVersion");
        MESSAGE_KEYS.put("docHistoryEditions", "sys-doc:sysDocBaseInfo.docHistoryEditions");
        MESSAGE_KEYS.put("docOriginDoc", "sys-doc:sysDocBaseInfo.docOriginDoc");
        MESSAGE_KEYS.put("authReaders", "sys-right:right.read.authReaders");
        MESSAGE_KEYS.put("authTmpReaders", "sys-right:right.read.authTmpReaders");
        MESSAGE_KEYS.put("authOtherReaders", "sys-right:right.read.authOtherReaders");
        MESSAGE_KEYS.put("authAllReaders", "sys-right:right.read.authAllReaders");
        MESSAGE_KEYS.put("authReaderFlag", "sys-right:right.read.authReaderFlag");
        MESSAGE_KEYS.put("authEditors", "sys-right:right.edit.authEditors");
        MESSAGE_KEYS.put("authTmpEditors", "sys-right:right.edit.authTmpEditors");
        MESSAGE_KEYS.put("authOtherEditors", "sys-right:right.edit.authOtherEditors");
        MESSAGE_KEYS.put("authAllEditors", "sys-right:right.edit.authAllEditors");
        MESSAGE_KEYS.put("authEditorFlag", "sys-right:right.edit.authEditorFlag");
        MESSAGE_KEYS.put("authAttCopys", "sys-right:right.att.authAttCopys");
        MESSAGE_KEYS.put("authAttNocopy", "sys-right:right.att.authAttNocopy");
        MESSAGE_KEYS.put("authAttDownloads", "sys-right:right.att.authAttDownloads");
        MESSAGE_KEYS.put("authAttNodownload", "sys-right:right.att.authAttNodownload");
        MESSAGE_KEYS.put("authAttPrints", "sys-right:right.att.authAttPrints");
        MESSAGE_KEYS.put("authAttNoprint", "sys-right:right.att.authAttNoprint");
        MESSAGE_KEYS.put("authTmpAttCopys", "sys-right:right.att.authAttCopys");
        MESSAGE_KEYS.put("authTmpAttNocopy", "sys-right:right.att.authAttNocopy");
        MESSAGE_KEYS.put("authTmpAttDownloads", "sys-right:right.att.authAttDownloads");
        MESSAGE_KEYS.put("authTmpAttNodownload", "sys-right:right.att.authAttNodownload");
        MESSAGE_KEYS.put("authTmpAttPrints", "sys-right:right.att.authAttPrints");
        MESSAGE_KEYS.put("authTmpAttNoprint", "sys-right:right.att.authAttNoprint");
        MESSAGE_KEYS.put("authArea", "sys-authorization:sysAuthArea.authArea");
    }

    Map<String, HbmClass> hbmClasses = new HashMap<String, HbmClass>();
    List<String> springBeans = new ArrayList<String>();
    List<String> strutsPaths = new ArrayList<String>();
    List<File> dictFiles = new ArrayList<File>();

    JSONObject docBaseAttrs;

    String _path;

    /**
     * 准备数据字典工具
     *
     * @param path
     * @throws Exception 获取SysDocBaseInfo.json 下面的attrs属性
     */
    void prepare(String path) throws Exception {
        _path = path == null ? "" : "/" + path;
        scan(new File("src/com/landray/kmss" + _path), false); //扫描文件
        scan(new File("WebContent/WEB-INF/KmssConfig" + _path), true); //扫描配置文件

        File docFile = new File("C:\\javaKF\\eclipseProject\\workspaceJXNew\\SPRJ20221027_JX\\trunk\\WebContent\\WEB-INF\\KmssConfig\\sys\\doc\\data-dict\\SysDocBaseInfo.json");
        if (docFile.exists()) {
            try {
                JSONObject json = JSONObject.fromObject(FileUtils.readFileToString(docFile, "UTF-8"));
                docBaseAttrs = json.optJSONObject("attrs");  //取到该文件下面的attrs属性
            } catch (IOException e) {
                return;
            }
        }
    }

    /**
     * 1.首先检查目录是否存在，如果目录不存在或者是一个文件而不是目录，方法将直接返回。
     *
     * 2.获取目录下的所有文件列表，如果文件列表为空，则直接返回。
     *
     * 3.遍历文件列表，对每个文件进行处理。
     *
     * 4.如果文件名以 "." 开头，则跳过该文件，继续处理下一个文件。
     *
     * 5.如果是一个目录，则递归调用 scan 方法，对该目录进行扫描。
     *
     * 6.如果是一个文件，则根据 isConfig 参数判断执行相应的操作：
     *
     * 如果 isConfig 为 true，则根据文件名执行不同的操作。如果文件名是 "spring.xml"，则调用 loadSpringXml 方法；如果文件名是 "struts.xml"，则调用 loadStrutsXml 方法；如果目录名是 "data-dict"，则判断文件名是否以 ".xml" 或 ".json" 结尾，如果是，则将文件添加到 dictFiles 列表中。
     * 如果 isConfig 为 false，则判断文件名是否以 ".hbm.xml" 结尾，如果是，则调用 loadHbmXml 方法。
     * @param dir
     * @param isConfig
     */
    public void scan(File dir, boolean isConfig) throws Exception {
        if (!dir.exists() || dir.isFile()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.startsWith(".")) {
                continue;
            }
            if (file.isDirectory()) {
                scan(file, isConfig);
            } else {
                if (isConfig) {
                    if ("spring.xml".equals(fileName)) {
//                        loadSpringXml(file);
                        loadXml(file, SpringBeans.class);
                    }  else if ("data-dict".equals(dir.getName())) {
                        if (fileName.endsWith(".xml") || fileName.endsWith(".json")) {
                            dictFiles.add(file);  //添加到数据字典文件列表中
                        }
                    }
                }else {
                    if (fileName.endsWith(".hbm.xml")) {
//                        loadHbmXml(file);
                        loadXml(file, HbmMapping.class);
                    }
                }
            }
        }
    }

    private <T> void loadXml(File file, Class<T> clazz) throws Exception{
        T obj = (T) XMLReaderUtil.getInstance(file, clazz);
        if(clazz == HbmMapping.class){
            hbmClasses = ((HbmMapping)obj).getList();
        } else if (clazz == SpringBeans.class) {
            for (SpringBean bean : ((SpringBeans)obj).getBeans()) {
                springBeans.add(bean.getId());
            }
        }

    }



    /** 修复数据字典 */
    boolean fixDict(JSONObject json, File file,  boolean logFix){
        String modelName = null;
        JSONObject global = json.optJSONObject("global");
        if(global != null){
            /** optString() 是一种用于从 JSON 对象中获取字符串值的方法。
             *
             * 相较于getString()，optString() 会在获取不到值的时候返回一个空字符串（""）。 而getString() 会在获取不到值的时候抛出异常。
             */
            modelName = global.optString("modelName"); //如果进入到这里  至少会返回“”  但是不会返回null
        }
        if(modelName == null){
            logDetail("错误：无法读取modelName信息：" + file.getPath());
            return false;
        }

        JSONObject baseAtts = "com.landray.kmss.sys.doc.model.SysDocBaseInfo".equals(global.optString("extendClass")) ? docBaseAttrs : null;

        FixContext ctx = new FixContext(file, modelName, logFix);
//修复model 数据
        ctx.fixMessage(global, null, this);
        fixModelHbmAttr(ctx, global);
        fixModelOtherAttr(ctx, global);
/**
 * 修复字段数据
 * getJSONObject() 方法在属性不存在或者属性类型不匹配时会抛出异常，
 * 而 optJSONObject() 方法则提供了更灵活的处理方式，可以在遇到异常情况时返回一个默认值（null）。
 */
        JSONObject attrs = json.optJSONObject("attrs");
        if(attrs == null){
            attrs = new JSONObject();
            json.put("attrs", attrs);
            attrs = json.getJSONObject("attrs");
        }

        List<String> properties = new ArrayList<String>();
        Iterator keys =attrs.keys();
        while (keys.hasNext()){
            properties.add(keys.next().toString());
        }
//数据字典中的字段
        if(baseAtts != null){
            keys = baseAtts.keys();
            while (keys.hasNext()){
                String key = keys.next().toString();
                if(!properties.contains(key)){
                    properties.add(key);
                }
            }
        }

//        根据HBM修复
        if(ctx.getHbm() != null){
            fixHbmClass(ctx, attrs, baseAtts, ctx.getHbm(), properties, true);
            if(ctx.getHbm() instanceof HbmSubClass){
                HbmSubClass hbm = (HbmSubClass) ctx.getHbm();
                if(hbm.getJoin() != null){
                    fixHbmClass(ctx, attrs, baseAtts, hbm.getJoin(), properties, true);
                }
                HbmClass extClass = hbmClasses.get(hbm.getExtendClass());
                if(extClass != null){
                    fixHbmClass(ctx, attrs, baseAtts, extClass, properties, false);
                }
            }
        }

        JSONObject attachments = json.optJSONObject("attachments");
        if(attachments == null){
            attachments = new JSONObject();
            json.put("attachments", attachments);
            attachments = json.getJSONObject("attachments");
        } else {
            /**
             *
             * keySet() 返回一个包含映射中所有键的 Set 集合，可以直接使用集合的方法来操作键。
             * keys() 返回一个枚举对象，需要使用枚举的方式来遍历键。
             */
            for (Object key: attachments.keySet()){
                JSONObject jsonProperty = attachments.getJSONObject(key.toString());
                ctx.replaceFix(jsonProperty, "propertyType", XmlJsonDictType.ATTACHMENT.getJsonName(),key.toString());
                ctx.fixMessage(global, key.toString(), this);
            }
        }

        for(String property: properties){
            JSONObject jsonProperty = attrs.optJSONObject(property);
            if(jsonProperty != null) {
                String propertyType = jsonProperty.optString("propertyType");
                if(XmlJsonDictType.ATTACHMENT.getJsonName().equals(propertyType)){
//                    附件修复
                    ctx.fixMessage(jsonProperty, property, this);
                    attachments.put(property, jsonProperty);
                    attrs.remove(property);
                    ctx.setModify(true);
                    continue;
                }
                if(XmlJsonDictType.COMPLEX.getJsonName().equals(propertyType)){
//                    复合属性
                    ctx.fixMessage(jsonProperty, property, this);
                    ctx.fixPropertyType(jsonProperty, propertyType, null, this);
                    continue;
                }
                if(ctx.getHbm() == null || ctx.getHbm().getId() == null){
//                    无HBM
                    if(ClassUtils.checkField(ctx.clazz, property) >= 0){
                        ctx.fixMessage(jsonProperty, property, this);
                        ctx.fixPropertyType(jsonProperty, propertyType, null, this);
                        continue;
                    }
                }
                ctx.setModify(true);
                attrs.remove(property);
                ctx.log("删除:" + property);
            }
//           上面对已经处理的属性做了删除，如果有基础属性中没有的属性，就会进入到这里
            if(baseAtts != null && baseAtts.containsKey(property)){
                ctx.log("错误:" + property + "属性在hbm.xml中未定义（继承SysDocBaseInfo的model必须拥有所有的字段）");
            }
        }
        if(attachments.isEmpty()){
            json.remove("attachments");
        }
        return ctx.isModify();
    }

    public void fixCanDisplay(FixContext ctx, JSONObject json, String fieldName, boolean checkMore) {
        String propertyType = json.optString("propertyType");
        if(fieldName == null || StringUtil.isNotNull(json.optString("canDisplay")) || XmlJsonDictType.ATTACHMENT.getJsonName().equals(propertyType)){
            return;
        }

        if(HIDDEN_FIELDS.contains(fieldName)){
            defaultFix(ctx, json, "canDisplay", "false", fieldName);
            return;
        }
        if(!checkMore){
            return;
        }
        if(MORE_HIDDEN_FIELDS.contains(fieldName)){
            defaultFix(ctx, json, "canDisplay", "false", fieldName);
            return;
        }
//        若在form中没有出现，则归于隐藏字段
        if(ctx.clazz == null || !XmlJsonDictType.SIMPLE.getJsonName().equals(propertyType)){
            return;
        }

        String formName = StringUtil.replace(ctx.clazz.getName(), ".model.", ".form.") + "Form";
        Class<?> formClazz = loadClass(formName);
        if(ClassUtils.checkField(formClazz, fieldName) == -1){
            defaultFix(ctx, json, "canDisplay", "false", fieldName);
        }
    }

    private void fixModelHbmAttr(FixContext ctx, JSONObject global) {
        if(ctx.getHbm() == null){
            return;
        }
        if(ctx.getHbm() instanceof HbmSubClass){
            /*
            * 继承关系， 子类没定义表 则用父类
            * */
            HbmSubClass hbm = (HbmSubClass) ctx.getHbm();
            String table = null;
            if(hbm.getJoin() != null){
                table = hbm.getJoin().getTable();
            } else {
                HbmClass sup = hbmClasses.get(hbm.getExtendClass());
                if(sup != null){
                    table = sup.getTable();
                }
            }

            ctx.replaceFix(global, "table", table, "model");
            defaultFix(ctx, global, "etendClass", hbm.getExtendClass(), "model");
            ctx.replaceFix(global, "discriminatorValue", hbm.getDiscriminatorValue(), "model");

        }else{
            ctx.replaceFix(global, "table", ctx.getHbm().getTable(), "model");
            ctx.replaceFix(global, "discriminatorValue", null, "model");
        }
    }

    private Class<?> loadClass(String formName) {
        return null;
    }

    private void defaultFix(FixContext ctx, JSONObject json, String canDisplay, String aFalse, String fieldName) {
    }

    public String getPropertyType(String type) {
        if(StringUtil.isNull(type)){
            return null;
        }
        if("com.landray.kmss.common.dao.ClobStringType".equals(type)){
            return "RTF";
        }
        if(type.startsWith("com.landray.kmss.")){
            return type;
        }

        String shortType = type.toLowerCase();
        int i = type.lastIndexOf(".");
        if(i > -1){
            shortType = type.substring(i + 1).toLowerCase();
        }
        return TYPES.get(shortType);
    }

    private void fixHbmClass(FixContext ctx, JSONObject attrs, JSONObject baseAtts, HbmClass hbm, List<String> properties, boolean addProperty) {
        boolean logFix = ctx.isLogFix();
        List<NamingProperty> hbmProperties = hbm.getProperties();
        if(hbmProperties != null){
            for (NamingProperty obj : hbmProperties) {
                String name = obj.getName();
                properties.remove(name);
//               创建字段
                JSONObject jsonProperty;
                if(attrs.containsKey(name)){
                    ctx.setLogFix(logFix);
                } else if (addProperty) {
                    jsonProperty = new JSONObject();
                    attrs.put(name, jsonProperty);
                    ctx.setLogFix(false);
                    ctx.setModify(true);
                    if(logFix){
                        ctx.log("添加:" + name);
                    }
                } else {
                    continue;
                }
//                初始化字段信息
                jsonProperty = attrs.getJSONObject(name);
                if(!"fdId".equals(name) && baseAtts != null && baseAtts.containsKey(name)){
                    JSONObject baseProperty = baseAtts.getJSONObject(name);
                    for(Object key: baseProperty.keySet()){
                        defaultFix(ctx, jsonProperty, key.toString(), baseProperty.optString(key.toString()), name);
                    }

                }

                switch (obj.getClass().getSimpleName()){
                    case "HbmId":
                        ctx.fixHbmId(jsonProperty, (HbmId)obj);
                        break;
                    case "HbmProperty":
                        fixHbmProperty(ctx, jsonProperty, (HbmProperty)obj);
                        break;
                    case "HbmOneToOne":
                        ctx.fixHbmOneToOne(jsonProperty, (HbmOneToOne)obj, this);
                        break;
                    case "HbmManyToOne":
                        ctx.fixHbmManyToOne(jsonProperty, (HbmManyToOne)obj, this);
                        break;
                    case "HbmBag":
                        fixHbmBag(ctx, jsonProperty, (HbmBag)obj);
                        break;

                }
            }
            ctx.setLogFix(logFix);
        }

    }

    private void fixHbmBag(FixContext ctx, JSONObject jsonProperty, HbmBag obj) {
        String table = obj.getTable();
        ctx.replaceFix(jsonProperty, "propertyType", XmlJsonDictType.LIST.getJsonName(), obj.getName());
        ctx.fixMessage(jsonProperty, obj.getName(), this);
        if(obj.getOneToMany() != null){
            ctx.fixPropertyType(jsonProperty, obj.getName(), obj.getOneToMany().getType(), this);
            ctx.replaceFix(jsonProperty, "culumn", obj.getKey().getColumn(), obj.getName());
            if(StringUtil.isNull(table)){
                HbmClass clz = hbmClasses.get(obj.getOneToMany().getType());
                if(clz != null){
                    table = clz.getTable();
                }
            }
        }else{
            if(obj.getManyToMany() != null){
                ctx.fixPropertyType(jsonProperty, obj.getName(), obj.getManyToMany().getType(), this);
                ctx.replaceFix(jsonProperty, "elementColumn", obj.getManyToMany().getColumn(), obj.getName());
            }
            ctx.replaceFix(jsonProperty, "culumn", obj.getManyToMany().getColumn(), obj.getName());
            if(obj instanceof HbmList && ((HbmList) obj).getIndex() != null){
                ctx.replaceFix(jsonProperty, "indexColumn", ((HbmList) obj).getIndex().getColumn(), obj.getName());
            }
        }
        if("true".equals(obj.getKey().getNotNull())){
            ctx.replaceFix(jsonProperty, "notNull", "true", obj.getName());
        }
        if("true".equals(obj.getKey().getUnique())){
            ctx.replaceFix(jsonProperty, "unique", "true", obj.getName());
        }
        ctx.replaceFix(jsonProperty, "orderyBy", obj.getOrderBy(), obj.getName());
        ctx.replaceFix(jsonProperty, "table", obj.getTable(), obj.getName());
        ctx.replaceFix(jsonProperty, "cascade", obj.getCascade(), obj.getName());
        ctx.replaceFix(jsonProperty, "inverse", obj.getInverse(), obj.getName());
    }

    private void fixHbmProperty(FixContext ctx, JSONObject jsonProperty, HbmProperty obj) {
        ctx.replaceFix(jsonProperty, "propertyType", XmlJsonDictType.SIMPLE.getJsonName(), obj.getName());
        ctx.fixMessage(jsonProperty, obj.getName(), this);
        ctx.fixPropertyType(jsonProperty, obj.getName(), obj.getType(), this);
        ctx.replaceFix(jsonProperty, "culumn", obj.getColumn(), obj.getName());
        ctx.replaceFix(jsonProperty, "length", obj.getLength(), obj.getName());
        if("true".equals(obj.getNotNull())){
            ctx.replaceFix(jsonProperty, "notNull", "true", obj.getName());
        }
        if("true".equals(obj.getUnique())){
            ctx.replaceFix(jsonProperty, "unique", "true", obj.getName());
        }
    }

    private void fixModelOtherAttr(FixContext ctx, JSONObject json) {
//        修复displayProperty
        if(ctx.clazz != null){ //按顺序找到第一个不为空的值
            String displayPropery = null;
            String[] opts = { json.optString("displayProperty"), json.optString("fdName"), json.optString("docSubject") };
            for (String opt : opts) {
                 if(StringUtil.isNull(opt)){
                     continue;
                 }

                if(ClassUtils.checkField(ctx.clazz, opt) >= 0){
                    displayPropery = opt;
                    break;
                }

            }
            ctx.replaceFix(json, "displayProperty", displayPropery, "model");
        }
//        修复serviceBean
        String bean = json.optString("serviceBean");
        if (StringUtil.isNull(bean) || !springBeans.contains(bean)) {
            bean = ctx.getSimpleName() + "Service";
            if(!springBeans.contains(bean)){
                bean = null;
            }
        }

        ctx.replaceFix(json, "serviceBean", bean, "model");
//        修复URL
        String url = json.optString("url");
        if(ctx.getBundle() != null && StringUtil.isNull(url)){
            url = "/" + ctx.getBundle().replace('-','/') + "/";  ///hr/ratify/hr_ratify_feedback/hrRatifyFeedback.do?method=view&fdId=${fdId}
            for(int i=0; i < ctx.getSimpleName().length(); i++){
                char c = ctx.getSimpleName().charAt(i);
                if(Character.isUpperCase(c)){
                    url += "_" + Character.toLowerCase(c);
                }else{
                    url += c;
                }
            }

            url += "/" + ctx.getSimpleName();
            if(strutsPaths.contains(url)){
                defaultFix(ctx,json, "url", url + ".do?method=view&fdId=${fdId}", "model");
            }
        }
    }





    private void logDetail(String s) {
    }
}
