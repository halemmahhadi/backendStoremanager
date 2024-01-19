package com.app.storemanager.item;

import java.text.MessageFormat;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id){
        super(MessageFormat.format("could not find item with this id {0}",id));

    }}

