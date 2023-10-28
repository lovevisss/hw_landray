package com.landray.kmss.code.dict;

import com.landray.kmss.code.hbm.HbmClass;
import com.landray.kmss.code.spring.SpringBeans;
import com.landray.kmss.code.struts.ActionMapping;
import com.landray.kmss.code.struts.StrutsConfig;
import com.landray.kmss.code.util.XMLReaderUtil;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class DataDictTool {
    static String LOG_FILE; //静态变量  类型为String  用于存储日志文件  所有示例共享同一个值

    static boolean LOG_WARN = true; //静态变量  类型为boolean  用于指示是否记录警告信息  所有示例共享同一个值

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
    private static Map<String, String> MESSAGE_KEYS = new HashMap<String, String>();

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

    /**
     * 准备数据字典工具
     *
     * @param path
     * @throws Exception 获取SysDocBaseInfo.json 下面的attrs属性
     */
    void prepare(String path) throws Exception {
        String _path = path == null ? "" : "/" + path;
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
    private void scan(File dir, boolean isConfig) {
        if (!dir.exists() || !dir.isFile()) {
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
                        loadSpringXml(file);
                    } else if("struts.xml".equals(fileName)){ // 目前这个文件好像已经被spring-mvc.xml替代了
                        loadStrutsXml(file);
                    } else if ("data-dict".equals(dir.getName())) {
                        if (fileName.endsWith(".xml") || fileName.endsWith(".json")) {
                            dictFiles.add(file);
                        }
                    }
                }else {
                    if (fileName.endsWith(".hbm.xml")) {
                        loadHbmXml(file);
                    }
                }
            }
        }
    }

    private void loadHbmXml(File file) {
    }

    private void loadStrutsXml(File file) {
        StrutsConfig config = (StrutsConfig) XMLReaderUtil.getInstance(file, StrutsConfig.class);
        for(ActionMapping action : config.getActionMappings()){
            strutsPaths.add(action.getPath());
        }
    }

    private void loadSpringXml(File file) {
        SpringBeans beans = (SpringBeans) XMLReaderUtil.getInstance(file, SpringBeans.class);
        for (SpringBeans bean : beans.getBeans()) {
            springBeans.add(bean.getId());
        }
    }
}
