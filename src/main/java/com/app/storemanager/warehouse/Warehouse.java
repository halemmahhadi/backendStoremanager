package com.app.storemanager.warehouse;

import com.app.storemanager.item.Item;
import com.app.storemanager.user.storemanager.StoreManager;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "seq", initialValue = 1, allocationSize = 200)
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long wareHouseID;
    private String wareHouseName;
    private String address;
    private String owner;
    private String warehouseDescription;
    private String ownerMail;
    @ManyToMany(mappedBy = "warehouses")
    @JsonIgnore
    private List<StoreManager> storeManagers = new ArrayList<>();
    @OneToMany(mappedBy = "warehouse",  cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Item> items = new ArrayList<>();
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime lastVisit;


    public static Warehouse from(WarehouseDto warehouseDto) {
        Warehouse warehouse = new Warehouse();
        warehouse.setWareHouseName(warehouseDto.getWareHouseName());
        warehouse.setAddress(warehouseDto.getAddress());
        warehouse.setOwner(warehouseDto.getOwner());
        warehouse.setWarehouseDescription(warehouseDto.getWarehouseDescription());
        return warehouse;

    }
    public void addItemToWarehouse(Item item){items.add(item);}

    public void removeItemFromWarehouse(Item item){ items.remove(item); }


    public void assignManagerToWarehouse(StoreManager storeManager) {
        storeManagers.add(storeManager);
    }

    public void removeManagerFromWarehouse(StoreManager storeManager) {
        storeManagers.remove(storeManager);
    }




}
