package com.app.storemanager.user.storemanager;

import com.app.storemanager.warehouse.WarehouseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("/mangers/")
public class StoreManagerController {
    @Autowired
    StoreManagerService storeManagerService;
    @Autowired
    WarehouseService warehouseService;


    @PreAuthorize("hasAnyRole('SuperAdmin')")
    @PostMapping(value = "createStoreManger/")
    @ApiOperation(value = "create StoreMange",
            notes = "add new StoreMange to DB",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added StoreMange"),
            @ApiResponse(code = 400, message = "Errors during input"),
    })
    public ResponseEntity<StoreMangerDto> addStoreManger(@RequestBody StoreMangerDto storeMangerDto) {
        try {
            StoreManager storeManager = storeManagerService.addStoreManger(StoreManager.from(storeMangerDto));
            return new ResponseEntity<>(StoreMangerDto.from(storeManager), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }

    @PreAuthorize("hasAnyRole('SuperAdmin')")
    @GetMapping(value = "getStoreMangerByEmail/email/{email}")
    @ApiOperation(value = "search for storeManger",
            notes = "get storeManger by  email",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "can not found this storeManager with this email")
    })
    public ResponseEntity<StoreMangerDto> getByEmail(@PathVariable final String email) {
        StoreManager storeManager = storeManagerService.findByEmail(email);
        if(storeManager !=null){
            return new ResponseEntity<>(StoreMangerDto.from(storeManager), HttpStatus.OK);
        }else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
    @PreAuthorize("hasAnyRole('SuperAdmin')")
    @PutMapping(value = "updateStoreManger/email/{email}")
    @ApiOperation(value = "Update storeManger",
            notes = "edit storeManger by email",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated storeManger"),
            @ApiResponse(code = 404, message = "Can not found storeManger with this email"),
    })
    public ResponseEntity<StoreMangerDto> editStoreManger(@PathVariable final String email, @RequestBody StoreMangerDto storeMangerDto) {
        try {
            StoreManager editStoreManger = storeManagerService.editStoreManger(email, StoreManager.from(storeMangerDto));
            return new ResponseEntity<>(StoreMangerDto.from(editStoreManger), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('SuperAdmin')")
    @DeleteMapping(value = "deleteStoreManger/email/{email}")
    @ApiOperation(value = "Delete storeManger",
            notes = " delete storeManger by email",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted storeManger"),
            @ApiResponse(code = 404, message = "Can not found storeManger with this email"),


    })
    public ResponseEntity<StoreMangerDto> deleteStoreManger(@PathVariable final String email) {
        try {
            StoreManager storeManager = storeManagerService.deleteStoreManger(email);
            return new ResponseEntity<>(StoreMangerDto.from(storeManager), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.OK);

        }
    }

    @PreAuthorize("hasAnyRole('SuperAdmin','Admin','StoreManager','StandardUser')")
    @PostMapping(value = "{email}/warehouses/{warehouseId}/add")
    @ApiOperation(value = "add warehouses by StoreManger",
            notes = "add warehouses by StoreManger",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully assign warehouse to manger"),
            @ApiResponse(code = 404, message = "Can not found storeManger or warehouse with the given user email and warehouseID"),


    })
    public ResponseEntity<StoreMangerDto> assignWarehouseToManger(@PathVariable final String email,
                                                  @PathVariable final Long warehouseId) {
        try {
            StoreManager storeManager = storeManagerService.assignWarehouseToManger(email, warehouseId);
            return new ResponseEntity<>(StoreMangerDto.from(storeManager), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
    }


    @PreAuthorize("hasAnyRole('SuperAdmin','StoreManager','StandardUser')")
    @DeleteMapping(value = "{email}/warehouses/{warehouseId}/remove")
    @ApiOperation(value = "remove warehouse from manager",
            notes = "remove warehouse from manager",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully removed manger from manging this warehouse"),
            @ApiResponse(code = 404, message = "Can not found storeManger or warehouse with the given user email and warehouseID"),

    })
    public ResponseEntity<StoreMangerDto> removeWarehouseFromManger(@PathVariable final String email,
                                                                @PathVariable final Long warehouseId) {
        try {
            StoreManager storeManager = storeManagerService.removeWarehouseFromManger(email, warehouseId);
            return new ResponseEntity<>(StoreMangerDto.from(storeManager), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
    }


}
