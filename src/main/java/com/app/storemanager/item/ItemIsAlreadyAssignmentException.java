package com.app.storemanager.item;

import java.text.MessageFormat;

public class ItemIsAlreadyAssignmentException extends RuntimeException {
    public ItemIsAlreadyAssignmentException(final Long itemId,final Long warehouseId ){
        super(MessageFormat.format("item:{0}is already assign to warehouse {1}",itemId ,warehouseId));

    }
}
