package com.landray.kmss.util;

import com.landray.kmss.common.model.IBaseTreeModel;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;

public class ObjectUtil {
    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName) {
        if (StringUtil.isNull(propertyName)) {
            return null;
        } else {
            String[] properties = propertyName.split("\\.");
            label43:
            for (int i = 0; i < properties.length; ++i) {
                if (StringUtil.isNull(properties[i])) {
                    return null;
                }
                PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(clazz);
                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                    if (propertyDescriptor.getName().equals(properties[i])) {
                        if (i == properties.length - 1) {
                            return propertyDescriptor;
                        }
                        if (propertyDescriptor.getPropertyType() == null) {
                            return null;
                        }
                        Class<?> subClazz = propertyDescriptor.getPropertyType();
                        if (!subClazz.isAssignableFrom(IBaseTreeModel.class)) {
                            clazz = subClazz;
                        }
                        continue label43;
                    }
                }
                return null;
            }
        }
        return null;
    }
}
