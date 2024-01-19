package com.app.storemanager.warehouse;

import com.app.storemanager.item.Item;
import com.app.storemanager.item.ItemIsAlreadyAssignmentException;
import com.app.storemanager.item.ItemService;
import com.app.storemanager.user.baseuser.UserService;
import com.app.storemanager.user.storemanager.StoreManager;
import com.app.storemanager.user.storemanager.StoreManagerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@Service
public class WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private StoreManagerService storeManagerService;
    @Autowired
    private UserService userService;

    //add warehouse
    public Warehouse addWarehouse(String mail, Warehouse warehouse) {
        try{
            warehouse.setOwnerMail(mail);
            Warehouse warehouse1 = warehouseRepository.save(warehouse);
            Warehouse warehouseWithoutVisitDate = warehouseRepository.getOne(warehouse1.getWareHouseID());
            warehouseWithoutVisitDate.setLastVisit(LocalDateTime.now());

            return warehouseRepository.save(warehouseWithoutVisitDate);
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    // get all warehouses
    public List<Warehouse> getAllWarehouse() {
        return StreamSupport
                .stream(warehouseRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    // get Warehouse by id
    public Warehouse getWarehouseById(Long id) {
        try {
            Warehouse warehouseWithoutVisitDate = warehouseRepository.getOne(id);
            warehouseWithoutVisitDate.setLastVisit(LocalDateTime.now());
            return warehouseRepository.save(warehouseWithoutVisitDate);
        }
        catch (WarehouseNotFoundException e){
            new WarehouseNotFoundException(id);
        }
        return null;
    }

    public List<List> getHomeGraphData(String email){
        List<Integer> warehouseItemsQuantitys = new ArrayList<>();
        int warehouseitemsQty;

        try {
            if(userService.findByEmail(email).getRoles().contains("StandardUser")){
                List<Warehouse> allWarehouses = getAllWarehouse();
                List<Warehouse> assignedWarehouses = new ArrayList<>();
                for(int i = 0;i<allWarehouses.size();i++){
                    for(int y=0;y<allWarehouses.get(i).getStoreManagers().size();y++){
                        StoreManager storeManager = allWarehouses.get(i).getStoreManagers().get(y);
                        if(storeManager != null && storeManager.getEmail().equals(email)){
                            assignedWarehouses.add(allWarehouses.get(i));
                        }
                    }
                }

                for (Warehouse warehouse :assignedWarehouses) {
                     warehouseitemsQty = 0;
                    for (Item item:warehouse.getItems()) {
                        warehouseitemsQty+= item.getQuantity();
                    }
                    warehouseItemsQuantitys.add(warehouseitemsQty);
                }
                List<String> warehouseNames = assignedWarehouses.stream().map(warehouse-> warehouse.getWareHouseName()).collect(Collectors.toList());
                return new ArrayList<>(Arrays.asList(warehouseNames,warehouseItemsQuantitys));

            }

            List<Warehouse> warehouses = getAllOwnerEmailWarehouse(email);
            List<String> warehouseNames = warehouses.stream().map(warehouse-> warehouse.getWareHouseName()).collect(Collectors.toList());

            for (Warehouse warehouse :warehouses) {
                 warehouseitemsQty = 0;
                for (Item item:warehouse.getItems()) {
                    warehouseitemsQty+= item.getQuantity();
                }
                warehouseItemsQuantitys.add(warehouseitemsQty);
            }
            return new ArrayList<>(Arrays.asList(warehouseNames,warehouseItemsQuantitys));
        }
        catch (Exception e){
            System.out.println(e);
            return Collections.emptyList();
        }
    }

    public Warehouse getWarehouseByIdToCreateItems(Long id){
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        return warehouse.orElseThrow(() ->
                new WarehouseNotFoundException(id));
    }
    // delete warehouse by his id
    public Boolean deleteWarehouse(Long id){
        try {
            Warehouse warehouse=getWarehouseByIdToCreateItems(id);
            warehouseRepository.delete(warehouse);
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    public List<Warehouse>getAllOwnerEmailWarehouse(String mail){
        return warehouseRepository.findByOwnerMail(mail);
    }

    public List<Warehouse> getLastVisitedWarehouse(String mail){
        List<Warehouse> warehousesByMail = warehouseRepository.findByOwnerMail(mail);
        if (warehousesByMail.size() == 0){
            return Collections.EMPTY_LIST;
        }
        List<Warehouse> warehouses = warehouseRepository.selectLastVisitedWarehouse(warehousesByMail.get(0).getOwner());

        if (warehouses.size() == 0){
            return Collections.EMPTY_LIST;
        }
        return warehouses;
    }

    // edit warehouse
    @Transactional
    public Warehouse editWarehouse(Long id, Warehouse warehouse) {
        Warehouse warehouseToEdit = getWarehouseById(id);
        warehouseToEdit.setWareHouseName(warehouse.getWareHouseName());
        warehouseToEdit.setAddress(warehouse.getAddress());
//        warehouseToEdit.setOwnerMail(warehouse.getOwnerMail());
        warehouseToEdit.setOwner(warehouse.getOwner());
        return warehouseToEdit;
    }

    @Transactional
    public Warehouse addItemToWarehouse(Long warehouseID, Long itemID) {
        Warehouse warehouse = getWarehouseById(warehouseID);
        Item item = itemService.getItemById(itemID);
        if (Objects.nonNull(item.getWarehouse())) {
            throw new ItemIsAlreadyAssignmentException(itemID, item.getWarehouse().getWareHouseID());

        }
        warehouse.addItemToWarehouse(item);
        item.setWarehouse(warehouse);
        return warehouse;
    }

    public Warehouse removeItemFromWarehouse(Long warehouseId, Long itemId){
        Warehouse warehouse = getWarehouseById(warehouseId);
        Item item = itemService.getItemById(itemId);
        warehouse.removeItemFromWarehouse(item);
        return warehouse;
    }
    @Transactional
    public Warehouse editItem(Long warehouseId, Long itemId, Item item){
        Warehouse warehouse = getWarehouseById(warehouseId);
        Item itemID =itemService.getItemById(itemId);
        Item itemTo= itemService.editItem(itemID.getItemId(),item);
        itemTo.setItemName(item.getItemName());
        itemTo.setPrice(item.getPrice());
        itemTo.setQuantity(item.getQuantity());
        itemTo.setCreationDate(item.getCreationDate());
        return warehouse;
    }
    public Warehouse assignManagerToWarehouse(Long warehouseId, String email){
        Warehouse warehouse=getWarehouseById(warehouseId);
        StoreManager storeManager=storeManagerService.findByEmail(email);
        warehouse.assignManagerToWarehouse(storeManager);
        storeManagerService.assignWarehouseToManger(email,warehouseId);
        return  warehouse;
    }

    public Warehouse removeManagerFromWarehouse(Long warehouseId, String email){
        Warehouse warehouse=getWarehouseById(warehouseId);
        StoreManager storeManager=storeManagerService.findByEmail(email);
        warehouse.removeManagerFromWarehouse(storeManager);
        storeManagerService.removeWarehouseFromManger(email,warehouseId);
        return warehouse;
    }

    public void insertDummyWarehouse() {

        Warehouse warehouse1 = new Warehouse();
        warehouse1.setOwner("kemgang Dorian");
        warehouse1.setOwnerMail("kemgangd@yahoo.fr");
        warehouse1.setAddress("KönigsStr. 5 Augsburg 86198");
        warehouse1.setWarehouseDescription("Store all our fruits");
        warehouse1.setWareHouseName("Fruits store");

        warehouseRepository.save(warehouse1);

        Warehouse warehouse2 = new Warehouse();

        warehouse2.setOwner("kemgang Dorian");
        warehouse2.setAddress("FriedStr. 51 Muenchen 76198");
        warehouse2.setOwnerMail("kemgangd@yahoo.fr");
        warehouse2.setWarehouseDescription("Store all our Vegetables");
        warehouse2.setWareHouseName("Vegetables store");

        warehouseRepository.save(warehouse2);

        Warehouse warehouse3 = new Warehouse();
        warehouse3.setOwner("kemgang Dorian");
        warehouse3.setAddress("KönigsStr. 9 Augsburg 86198");
        warehouse3.setOwnerMail("kemgangd@yahoo.fr");
        warehouse3.setWarehouseDescription("Store all ou furniture");
        warehouse3.setWareHouseName("Furniture store");

        warehouseRepository.save(warehouse3);

    }


}
