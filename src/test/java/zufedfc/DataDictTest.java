package zufedfc;

import com.landray.kmss.code.dict.DataDictCreateTool;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DataDictTest{

   @Test
    public void testPrepare() throws Exception {  //load a module
       DataDictCreateTool dataDictCreateTool = new DataDictCreateTool();
       dataDictCreateTool.testPrepare(null); //load all modules
       assert(dataDictCreateTool.get_path() == "");
       dataDictCreateTool.testPrepare("sys/news"); //load a module
       assert("/sys/news".equals(dataDictCreateTool.get_path()));  //init docBaseAttrs
       assert("String".equals(dataDictCreateTool.getDocBaseAttrs().optJSONObject("fdId").optString("type")));
       assert("sys-doc:sysDocBaseInfo.docType".equals(dataDictCreateTool.getDocBaseAttrs().optJSONObject("docType").optString("messageKey")));
    }

    @Test
    public void testScan() throws Exception { //传入的必须是一个目录，如果是文件不会单独解析
        DataDictCreateTool dataDictCreateTool = new DataDictCreateTool();
        File none_dir = new File("test");
        assert(!none_dir.exists());
        File file = new File("C:\\javaKF\\eclipseProject\\workspaceJXNew\\SPRJ20221027_JX\\trunk\\WebContent\\WEB-INF\\KmssConfig\\sys\\doc\\data-dict\\SysDocBaseInfo.json");
        assert(file.exists() && file.isFile());
        File dir = new File("C:\\javaKF\\eclipseProject\\workspaceJXNew\\SPRJ20221027_JX\\trunk\\WebContent\\WEB-INF\\KmssConfig\\sys\\news");
        assert(dir.exists() && dir.isDirectory());
        assert(false == dir.isFile());

        assert(dir.listFiles().length == 16);  // .svn count as extra one

        dataDictCreateTool.scan(dir, false);
        assert(dataDictCreateTool.getDictFiles().size() == 0);
        System.out.println(dataDictCreateTool.getSpringBeans().size());
        dataDictCreateTool.scan(dir,true);
        assert(dataDictCreateTool.getDictFiles().size() == 6);
//test if struts is null
        File file_all = new File("C:\\javaKF\\eclipseProject\\workspaceJXNew\\SPRJ20221027_JX\\trunk\\WebContent\\WEB-INF\\KmssConfig");
        dataDictCreateTool.scan(file_all, true);
        assert(dataDictCreateTool.getStrutsPaths().size() ==0);

    }

    @Test
    public void testgetPropertyType(){

    }

}
