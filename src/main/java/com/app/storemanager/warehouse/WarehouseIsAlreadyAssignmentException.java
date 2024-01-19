package com.app.storemanager.warehouse;

import java.text.MessageFormat;

public class WarehouseIsAlreadyAssignmentException extends RuntimeException{
    public WarehouseIsAlreadyAssignmentException(final String email, final Long warehouseId ){
        super(MessageFormat.format("warehouse:{0}is already assign to storeManger {1}",warehouseId ,email));

    }
}
