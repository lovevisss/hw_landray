package zufedfc;

import com.landray.kmss.code.spring.SpringBeans;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class XMLReaderTest {
    private static Logger logger = LoggerFactory.getLogger(SpringBeans.class);
    @Test
    public void testSpringBeansXML() throws Exception {
        String path ="D:\\idea-landray\\SPRJ20221027_JX\\trunk\\WebContent\\WEB-INF\\KmssConfig\\third\\pda\\spring.xml";
        SpringBeans beans =SpringBeans.getInstance(new File(path));
        System.out.println(beans.toString());
        logger.info(beans.toString());
    }
}
