package com.landray.kmss.code.dict;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DataDictCreateTool extends DataDictTool{

    public void testPrepare(String s) throws Exception {
        prepare(s);
    }

    public void testScan(File dir, boolean isConfig) throws ParserConfigurationException, IOException, SAXException {
        scan(dir,isConfig);
    }
}
