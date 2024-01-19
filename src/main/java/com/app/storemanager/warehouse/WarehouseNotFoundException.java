package com.app.storemanager.warehouse;

import java.text.MessageFormat;

public class WarehouseNotFoundException extends RuntimeException {
    public WarehouseNotFoundException(Long id){
        super(MessageFormat.format("could not find warehouse with this id {0}",id));
    }
}
