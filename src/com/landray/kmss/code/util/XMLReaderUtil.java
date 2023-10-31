package com.landray.kmss.code.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.landray.kmss.code.spring.SpringBeans;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;

public class XMLReaderUtil {
    public XMLReaderUtil() {
    }
    public static Object getInstance(File file, Class cls) throws ParserConfigurationException, SAXException, IOException {
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
//        BeanReader xmlReader = new BeanReader(cls);
//        xmlReader.registerBeanClass(cls);
//        return xmlReader.parse(file);
//        JAXBContext context = JAXBContext.newInstance(cls);
//        Unmarshaller unmarshaller = context.createUnmarshaller();
        XmlMapper xmlMapper = new XmlMapper();

        return xmlMapper.readValue(file, cls);
    }
}
