package com.app.storemanager.item;

import com.app.storemanager.warehouse.Warehouse;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemName;
    private int quantity;
    private double price;
    private LocalDate creationDate;
    @ManyToOne
    @JoinColumn(name = "wareHouseID")
    @JsonManagedReference
    private Warehouse warehouse;

    public Item(String itemName, int quantity, double price, LocalDate creationDate, Warehouse warehouse) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.creationDate = creationDate;
        this.warehouse = warehouse;
    }

    public static Item from(ItemDto itemDto) {
        Item item = new Item();
        item.setItemName(itemDto.getItemName());
        item.setPrice(itemDto.getPrice());
        item.setQuantity(itemDto.getQuantity());
        item.setCreationDate(itemDto.getCreationDate());
        item.setWarehouse(itemDto.getWarehouse());
        return item;

    }

}
