package com.landray.kmss.sys.config.dict.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public enum XmlJsonDictType {
    SIMPLE("simpleProperty", "simple"),
    ATTACHMENT("attachmentProperty", "att"),
    COMPLEX("complexProperty", "complex" ),

    MODEL("modelProperty", "model"),
    LIST("listProperty", "list");

    private String name;
    private String jsonName;

    XmlJsonDictType(String name, String jsonName) {
        this.name = name;
        this.jsonName = jsonName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJsonName() {
        return jsonName;
    }

    public void setJsonName(String jsonName) {
        this.jsonName = jsonName;
    }
}
