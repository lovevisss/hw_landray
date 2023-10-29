package com.landray.kmss.code.hbm;

import lombok.Data;

@Data
public class HbmId implements NamingProperty {
    private HbmGenerator generator;

    @Override
    public String getName() {
        return "fdId";
    }

}
