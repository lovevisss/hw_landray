package com.landray.kmss.code.util;

import com.landray.kmss.code.spring.SpringBeans;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class XMLReaderUtil {
    public XMLReaderUtil() {
    }
    public static Object getInstance(File file, Class cls) throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        SAXParser parser = factory.newSAXParser();
        parser.getXMLReader().setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        /**
         * 使用 Commons Betwixt，你可以：
         *
         * 将 Java 对象转换为 XML，并保存到文件或输出流中。
         * 从 XML 数据中读取并将其转换为 Java 对象。
         */
        BeanReader xmlReader = new BeanReader(cls);


        return null;
    }
}
