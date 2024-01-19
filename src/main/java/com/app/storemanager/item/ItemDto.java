package com.app.storemanager.item;

import com.app.storemanager.warehouse.Warehouse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;

@Data

public class ItemDto {
    private Long itemId;
    private String itemName;
    private double price;
    private int quantity;
    private LocalDate creationDate;
    @JsonIgnore
    private Warehouse warehouse;
    public static ItemDto from(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemId(item.getItemId());
        itemDto.setItemName(item.getItemName());
        itemDto.setPrice(item.getPrice());
        itemDto.setQuantity(item.getQuantity());
        itemDto.setCreationDate(item.getCreationDate());
        itemDto.setWarehouse(item.getWarehouse());
        return itemDto;

    }

}
