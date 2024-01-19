package com.app.storemanager.user.storemanager;

import com.app.storemanager.user.baseuser.User;
import com.app.storemanager.warehouse.Warehouse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)

public class StoreManager extends User {
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "manger_warehause",
            joinColumns = @JoinColumn(name = "manager_email"),
            inverseJoinColumns = @JoinColumn(name = "warehouse_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {
            "manager_email", "warehouse_id" }))
    @JsonIgnore
    private List<Warehouse> warehouses;

    public static StoreManager from(StoreMangerDto storeMangerDto) {
        StoreManager storeManager = new StoreManager();
        storeManager.setEmail(storeMangerDto.getEmail());
        storeManager.setFirstName(storeMangerDto.getFirstName());
        storeManager.setLastName(storeMangerDto.getLastName());
        storeManager.setPhoneNumber(storeMangerDto.getPhoneNumber());
        storeManager.setPassword(storeMangerDto.getPassword());
        storeManager.setBirthday(storeMangerDto.getBirthday());
        storeManager.setAddress(storeMangerDto.getAddress());
        storeManager.setCreationDate(storeMangerDto.getCreationDate());
        storeManager.setAccountValidTill(storeMangerDto.getAccountValidTill());
        storeManager.setRoles(storeMangerDto.getRoles());
        return storeManager;
    }

    public void assignWarehouseToManger(Warehouse warehouse) {
        warehouses.add(warehouse);
    }

    public void removeWarehouseFromManger(Warehouse warehouse) {
        warehouses.remove(warehouse);
    }


}
