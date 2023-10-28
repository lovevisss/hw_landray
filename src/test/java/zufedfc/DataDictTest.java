package zufedfc;

import com.landray.kmss.code.dict.DataDictCreateTool;
import org.junit.Test;

public class DataDictTest{

   @Test
    public void testPrepare() throws Exception {
       DataDictCreateTool dataDictCreateTool = new DataDictCreateTool();
       dataDictCreateTool.testPrepare("sys/news");
       System.out.println("hello");
    }
}
