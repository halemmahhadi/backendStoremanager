package com.app.storemanager.warehouse;


import com.app.storemanager.item.Item;
import com.app.storemanager.item.ItemDto;
import com.app.storemanager.item.ItemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
@CrossOrigin
@RequestMapping("/warehouses/")
public class WarehouseController {
    @Autowired
    private final WarehouseService warehouseService;
    @Autowired
    private final ItemService itemService;

    // add warehouse

    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @PostMapping("createWarehouse/" )
    @ApiOperation(value = "create Warhouse",
            notes = "add new warehouse to DB",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added Warehouse"),
    })
    public ResponseEntity<WarehouseDto> addWarehouse(@RequestBody WarehouseDto warehouseDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserMail = authentication.getName();

        Warehouse warehouse = warehouseService.addWarehouse(currentUserMail,Warehouse.from(warehouseDto));
            return new ResponseEntity<>(WarehouseDto.from(warehouse), HttpStatus.OK);

    }

    // get warehouse
    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @GetMapping("getWarehouse/")
    @ApiOperation(value = "view a list of available warehouse present in the store",
            notes = "get all warehouses",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully displayed all warehouses"),
            @ApiResponse(code = 404, message = "There is no warehouse to display")
    })
    public ResponseEntity<List<WarehouseDto>> getAllWarehouse() {
        try {
            List<Warehouse> warehouses = warehouseService.getAllWarehouse();
            List<WarehouseDto> warehouseDto = warehouses.stream().map(WarehouseDto::from).collect(Collectors.toList());
            return new ResponseEntity<>(warehouseDto, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @GetMapping("getWarehouse/owner/{owner}")
    @ApiOperation(value = "view a list of available warehouse present in the store owned by a user",
            notes = "get all warehouses of a user",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully displayed all warehouses"),
            @ApiResponse(code = 404, message = "There is no warehouse to display")
    })
    public ResponseEntity<List<WarehouseDto>> getAllWarehouse(@PathVariable String owner) {
        try {
            List<Warehouse> warehouses = warehouseService.getAllOwnerEmailWarehouse(owner);
            List<WarehouseDto> warehouseDto = warehouses.stream().map(WarehouseDto::from).collect(Collectors.toList());
            return new ResponseEntity<>(warehouseDto, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @GetMapping(value = "get_warehouse/id/{id}")
    @ApiOperation(value = "search for Warehouse",
            notes = "get Warehouse by  id",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = " There is no warehouse with this ID ")

    })
    public ResponseEntity<WarehouseDto> getWarehouseById(@PathVariable final Long id) {
        Warehouse warehouse = warehouseService.getWarehouseById(id);
        if(warehouse != null){
            return new ResponseEntity<>(WarehouseDto.from(warehouse), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @GetMapping(value = "getLastVisitedWarehouse/owner/{ownerName}")
    @ApiOperation(value = "Get the last visited warehouse",
            notes = "Get the last visited warehouse",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = " This user doesn't own an id")

    })
    public ResponseEntity<WarehouseDto> getWarehouseById(@PathVariable final String ownerName) {
        List<Warehouse> warehouse = warehouseService.getLastVisitedWarehouse(ownerName);
        if(! warehouse.isEmpty()){
            return new ResponseEntity<>(WarehouseDto.from(warehouse.get(0)), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @GetMapping(value = "getHomeGraphData/owner/{ownerName}")
    @ApiOperation(value = "Get the graph datas for the home page",
            notes = "Get the graph datas for the home page",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "The user has no warehouse")

    })
    public ResponseEntity<List> getHomeGraphData(@PathVariable final String ownerName) {
        List<List> graphData = warehouseService.getHomeGraphData(ownerName);
        if(! graphData.isEmpty()){
            return new ResponseEntity<>(graphData, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @PreAuthorize("hasAnyRole('Admin','SuperAdmin')")
    @DeleteMapping(value = "delete_warehouse/id/{id}")
    @ApiOperation(value = "Delete warehouse",
            notes = " delete warehouse by id",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted warehouse"),
            @ApiResponse(code = 404, message = "There is no warehouse with this ID to delete")

    })
    public ResponseEntity<Boolean> deleteWarehouse(@PathVariable final Long id) {
        try {
            return new ResponseEntity<>(warehouseService.deleteWarehouse(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

    }
    // edit warehouse
    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @PutMapping(value = "update_warehouse/id/{id}")
    @ApiOperation(value = "Update warehouse",
            notes = "edit warehouse by id",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated warehouse"),
            @ApiResponse(code = 404, message = "There is no warehouse with this ID to update")
    })
    public ResponseEntity<WarehouseDto> editWarehouse(@PathVariable final Long id, @RequestBody final WarehouseDto warehouseDto) {
        try {
            Warehouse editWarehouse = warehouseService.editWarehouse(id, Warehouse.from(warehouseDto));
            return new ResponseEntity<>(WarehouseDto.from(editWarehouse), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @PostMapping(value = "{warehouseId}/items/{itemId}/add")
    @ApiOperation(value = "add item to warehouse",
            notes = "add item to warehouse",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully add items to warehouse"),
            @ApiResponse(code = 404, message = "When there is no item or warehouse with the given IDs"),
    })
    public ResponseEntity<WarehouseDto> addItemToWarehouse(@PathVariable final Long warehouseId,
                                                           @PathVariable final Long itemId) {
        try {
            Warehouse warehouse = warehouseService.addItemToWarehouse(warehouseId, itemId);
            return new ResponseEntity<>(WarehouseDto.from(warehouse), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        }

    }

    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @DeleteMapping(value = "{warehouseId}/items/{itemId}/remove")
    @ApiOperation(value = "remove item to warehouse",
            notes = "remove item to warehouse",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully removed items from warehouse"),
            @ApiResponse(code = 404, message = "When there is no item or warehouse with the given IDs"),
    })
    public ResponseEntity<WarehouseDto> removeItemFromWarehouse(@PathVariable final Long warehouseId,
                                                                @PathVariable final Long itemId) {
        try {
            Warehouse warehouse = warehouseService.removeItemFromWarehouse(warehouseId, itemId);
            itemService.deleteItem(itemId);
            return new ResponseEntity<>(WarehouseDto.from(warehouse), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @PutMapping(value = "{warehouseId}/items/{itemId}/update")
    @ApiOperation(value = "Update warehouse",
            notes = "edit warehouse by id",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated warehouse"),
            @ApiResponse(code = 404, message = "There is no warehouse with this ID to update")
    })
    public ResponseEntity<WarehouseDto> editItem(@PathVariable final Long warehouseId, @PathVariable final Long itemId, @RequestBody final ItemDto itemDto) {
        try {
            Warehouse editWarehouse = warehouseService.editItem(warehouseId,itemId, Item.from(itemDto));
            return new ResponseEntity<>(WarehouseDto.from(editWarehouse), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('SuperAdmin','StoreManager','StandardUser','Admin')")
    @PostMapping(value = "{email}/storeManagers/{warehouseId}/add")
    @ApiOperation(value = "assign storeManager",
            notes = "assign storeManager to warehouse",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully assign manager to warehouse"),
            @ApiResponse(code = 404, message = "Can not found storeManger or warehouse with the given user email and warehouseID")
    })
    public ResponseEntity<WarehouseDto> assignManagerToWarehouse(@PathVariable final Long warehouseId,
                                                                     @PathVariable final String email) {
        try {
            Warehouse warehouse = warehouseService.assignManagerToWarehouse(warehouseId, email);
            return new ResponseEntity<>(WarehouseDto.from(warehouse), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
    }

    @PreAuthorize("hasAnyRole('SuperAdmin','StoreManager','StandardUser','Admin')")
    @DeleteMapping(value = "{warehouseId}/storeManagers/{email}/remove")
    @ApiOperation(value = "remove storeManager from warehouse",
            notes = "remove storeManager from warehouse",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully removed manger from manging this warehouse"),
            @ApiResponse(code = 404, message = "Can not found storeManger or warehouse with the given user email and warehouseID"),

    })
    public ResponseEntity<WarehouseDto> removeManagerFromWarehouse(@PathVariable final Long warehouseId,
                                                                    @PathVariable final String email) {
        try {
            Warehouse warehouse = warehouseService.removeManagerFromWarehouse(warehouseId, email);
            return new ResponseEntity<>(WarehouseDto.from(warehouse), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
    }
}
