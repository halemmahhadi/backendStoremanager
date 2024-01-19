package com.app.storemanager.user.storemanager.admin;


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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admins/")
public class AdminController {

    @Autowired
    AdminService adminService;


    @PreAuthorize("hasAnyRole('SuperAdmin')")
    @PostMapping(value = "createAdmin/")
    @ApiOperation(value = "create admin",
            notes = "add new admin to DB",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added admin"),
            @ApiResponse(code = 400, message = "Errors during input"),
    })
    public ResponseEntity<AdminDto> addAdmin(AdminDto adminDto) {
        try {
            Admin admin = adminService.save(Admin.from(adminDto));
            return new ResponseEntity<>(AdminDto.from(admin), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }

    @PreAuthorize("hasAnyRole('SuperAdmin')")
    @GetMapping(value = "get_adminByEmail/email/{email}")
    @ApiOperation(value = "search for admin",
            notes = "get admin by  email",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "can not found this admin with this email")
    })
    public ResponseEntity<AdminDto> getByEmail(@PathVariable final String email) {
        Admin admin = adminService.findByEmail(email);
        if (admin !=null){
            return new ResponseEntity<>(AdminDto.from(admin), HttpStatus.OK);

        }else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @PreAuthorize("hasAnyRole('SuperAdmin')")
    @DeleteMapping(value = "delete_admin/email/{email}")
    @ApiOperation(value = "Delete admin",
            notes = " delete admin by email",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted admin"),
            @ApiResponse(code = 404, message = "can not found this admin with this email")
    })
    public ResponseEntity<AdminDto> deleteAdmin(@PathVariable final String email) {
        try {
            Admin admin = adminService.deleteAdmin(email);
            return new ResponseEntity<>(AdminDto.from(admin), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
    }

    @PreAuthorize("hasAnyRole('SuperAdmin')")
    @PutMapping(value = "update_admin/email/{email}")
    @ApiOperation(value = "Update admin",
            notes = "edit admin by email",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated admin"),
            @ApiResponse(code = 404, message = "can not found this admin with this email")
    })
    public ResponseEntity<AdminDto> editAdmin(@PathVariable final String email, AdminDto adminDto) {
        try {
            Admin editAdmin = adminService.editAdmin(email, Admin.from(adminDto));
            return new ResponseEntity<>(AdminDto.from(editAdmin), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.OK);

        }
    }
    @PreAuthorize("hasAnyRole('SuperAdmin','Admin')")
    @PostMapping(value = "{email}/warehouses/{warehouseId}/add")
    @ApiOperation(value = "add warehouse to admin",
            notes = "add warehouse to admin",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully assign warehouse to manger"),
            @ApiResponse(code = 404, message = "can not found this admin or warehouse with the given email and warehouseId")

    })
    public ResponseEntity<AdminDto> assignWarehouseToAdmin(@PathVariable final String email,
                                                         @PathVariable final Long warehouseId) {
        try {
            Admin admin = adminService.assignWarehouseToAdmin(email, warehouseId);
            return new ResponseEntity<>(AdminDto.from(admin), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
    }
    @PreAuthorize("hasAnyRole('SuperAdmin','Admin')")
    @DeleteMapping(value = "{email}/warehouses/{warehouseId}/remove")
    @ApiOperation(value = "add warehouse to admin",
            notes = "add warehouse to admin",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully removed warehouse from manger"),
            @ApiResponse(code = 404, message = "can not found this admin or warehouse with the given email and warehouseId")

    })
    public ResponseEntity<AdminDto> removeWarehouseFromAdmin(@PathVariable final String email,
                                                           @PathVariable final Long warehouseId) {
        try {
            Admin admin = adminService.removeWarehouseFromAdmin(email, warehouseId);
            return new ResponseEntity<>(AdminDto.from(admin), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
    }
    @PostMapping(value = "{email}/image/{id}/add")
    public ResponseEntity<AdminDto> addImage(@PathVariable final String email, @PathVariable final Long id){
        Admin admin=adminService.addImage(email,id);
        return new ResponseEntity<>(AdminDto.from(admin),HttpStatus.OK);
    }
}
