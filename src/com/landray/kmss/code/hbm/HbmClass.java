package com.landray.kmss.code.hbm;

import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class HbmClass {

    private HbmId id;
    private String name;
    private String table;
    private List<Object> properties = new ArrayList<>();
    private List<Object> subclasses = new ArrayList<>();

    public void setId(HbmId id) {
        this.id = id;
        this.properties.add(id);
    }

    public void addOneToOneProperty(HbmOneToOne oneToOne) {
        this.properties.add(oneToOne);
    }

    public void addManyToOneProperty(HbmManyToOne manyToOne) {
        this.properties.add(manyToOne);
    }

    public void addProperty(HbmProperty property) {
        this.properties.add(property);
    }

    public void addBagProperty(HbmBag bag) {
        this.properties.add(bag);
    }

    public void addListProperty(HbmList list) {
        this.properties.add(list);
    }

    public void addSubclass(HbmSubclass subclass) {
        this.subclasses.add(subclass);
    }



}
