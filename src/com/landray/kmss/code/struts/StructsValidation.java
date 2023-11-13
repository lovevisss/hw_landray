package com.landray.kmss.code.struts;

import com.landray.kmss.code.util.XMLReaderUtil;
import lombok.Data;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Data
public class StructsValidation {
    public static StructsValidation getInstance(File file) throws ParserConfigurationException, IOException, SAXException {
        return (StructsValidation) XMLReaderUtil.getInstance(file, StructsValidation.class);
    }

    private List<ValidateForm> validateForms = new ArrayList<ValidateForm>();
    public void addValidateForm(ValidateForm formBean) {
        this.validateForms.add(formBean);
    }

}
