package com.app.storemanager.user.storemanager;
import com.app.storemanager.warehouse.Warehouse;

import com.app.storemanager.warehouse.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class StoreManagerService {
    @Autowired
    StoreManagerRepository storeManagerRepository;
    @Autowired
    WarehouseService warehouseService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public StoreManager findByEmail(String email) {
        Optional<StoreManager> storeManager = storeManagerRepository.findByEmail(email);
        return storeManager.orElse(null);
    }

    public StoreManager addStoreManger(StoreManager storeManager){
        storeManager.setPassword(passwordEncoder.encode(storeManager.getPassword()));
         return (StoreManager) storeManagerRepository.save(storeManager);
    }
    public StoreManager getStoreMangerRef(Long id) {
        return (StoreManager) storeManagerRepository.getOne(id);
    }

    public List<StoreManager> getAllStoreManager(){
        return storeManagerRepository.findAll();
    }
    public StoreManager deleteStoreManger(String email){
        StoreManager storeManager=findByEmail(email);
        storeManagerRepository.delete(storeManager);
        return storeManager;
    }
    @Transactional
    public StoreManager editStoreManger(String email, StoreManager storeManager){
        StoreManager storeManagerToEdit=findByEmail(email);
        storeManagerToEdit.setCreationDate(storeManager.getCreationDate());
        storeManagerToEdit.setAddress(storeManager.getAddress());
        storeManagerToEdit.setPhoneNumber(storeManager.getPhoneNumber());
        storeManagerToEdit.setAccountValidTill(storeManager.getAccountValidTill());
        storeManagerToEdit.setFirstName(storeManager.getFirstName());
        storeManagerToEdit.setLastName(storeManager.getLastName());
        storeManagerToEdit.setPhoneNumber(storeManager.getPhoneNumber());
        storeManagerToEdit.setBirthday(storeManager.getBirthday());
        storeManagerToEdit.setPassword(passwordEncoder.encode(storeManager.getPassword()));
        return  storeManagerToEdit;

    }

    public StoreManager assignWarehouseToManger(String email, Long warehouseId){
        StoreManager storeManager=findByEmail(email);
        Warehouse warehouse=warehouseService.getWarehouseById(warehouseId);
       storeManager.assignWarehouseToManger(warehouse);
       storeManagerRepository.save(storeManager);
        return  storeManager;
    }
    @Transactional
    public StoreManager removeWarehouseFromManger(String  email, Long warehouseID){
        StoreManager storeManager=findByEmail(email);
        Warehouse warehouse=warehouseService.getWarehouseById(warehouseID);
        storeManager.removeWarehouseFromManger(warehouse);
        return storeManager;
    }




}
