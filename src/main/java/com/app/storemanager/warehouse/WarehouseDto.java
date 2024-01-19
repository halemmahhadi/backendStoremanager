package com.app.storemanager.warehouse;


import com.app.storemanager.item.ItemDto;
import com.app.storemanager.user.storemanager.StoreManager;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class WarehouseDto {
    private Long wareHouseID;
    private String wareHouseName;
    private String address;
    private String owner;
    private String warehouseDescription;
    private String ownerMail;
    private LocalDateTime lastVisit;
    private List<ItemDto> itemsDto = new ArrayList<>();
    private List<StoreManager> storeMangerDto = new ArrayList<>();

    public static WarehouseDto from(Warehouse warehouse) {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setWareHouseID(warehouse.getWareHouseID());
        warehouseDto.setWareHouseName(warehouse.getWareHouseName());
        warehouseDto.setAddress(warehouse.getAddress());
        warehouseDto.setOwner(warehouse.getOwner());
        warehouseDto.setOwnerMail(warehouse.getOwnerMail());
        warehouseDto.setWarehouseDescription(warehouse.getWarehouseDescription());
        warehouseDto.setLastVisit(warehouse.getLastVisit());
        warehouseDto.setItemsDto(warehouse.getItems().stream().map(ItemDto::from).collect(Collectors.toList()));
        warehouseDto.setStoreMangerDto(warehouse.getStoreManagers().stream().collect(Collectors.toList()));
        return warehouseDto;

    }


}
