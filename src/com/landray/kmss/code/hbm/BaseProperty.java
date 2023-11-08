package com.landray.kmss.code.hbm;

import lombok.Data;

@Data
public class BaseProperty implements NamingProperty{
    private String name;
    private String column;
    private String notNull;
    private String type;
    private String unique;
}
