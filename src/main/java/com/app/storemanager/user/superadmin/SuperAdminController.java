package com.app.storemanager.user.superadmin;

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
@RequestMapping("/super_admins/")
public class SuperAdminController {
    @Autowired
    SuperAdminService superAdminService;

    @PreAuthorize("hasRole('SuperAdmin')")
    @PostMapping(value = "createsuperadmin/")
    @ApiOperation(value = "create SuperAdmin",
            notes = "add new SuperAdmin to DB",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added SuperAdmin"),
            @ApiResponse(code = 400, message = "Errors during input"),
    })
    public ResponseEntity<SuperAdminDto> addSuperAdmin(SuperAdminDto superAdminDto) {
        try {
            SuperAdmin superAdmin = superAdminService.save(SuperAdmin.from(superAdminDto));
            return new ResponseEntity<>(SuperAdminDto.from(superAdmin), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }
    @PreAuthorize("hasRole('SuperAdmin')")
    @DeleteMapping(value = "delete_superadmin/email/{email}")
    @ApiOperation(value = "Delete superAdmin",
            notes = " delete admin by email",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted superAdmin"),
            @ApiResponse(code = 404, message = "can not found this superAdmin with this email"),
    })
    public ResponseEntity<SuperAdminDto> deleteAdmin(@PathVariable final String email) {
        try {
            SuperAdmin superAdmin = superAdminService.delete(email);
            return new ResponseEntity<>(SuperAdminDto.from(superAdmin), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
    }

    @PreAuthorize("hasRole('SuperAdmin')")
    @PostMapping(value = "{email}/admins/{adminEmail}/add")
    @ApiOperation(value = "add admin by superAdmin",
            notes = "add admin by superAdmin",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully assign admin to superAdmin"),
            @ApiResponse(code = 404, message = "can not found this superAdmin oder admin with the given emails"),

    })
    public ResponseEntity<SuperAdminDto> assignAdminToSuperAdmin(@PathVariable final String email,
                                                               @PathVariable final String adminEmail) {
        try {
            SuperAdmin superAdmin = superAdminService.assignAdminToSuperAdmin(email, adminEmail);
            return new ResponseEntity<>(SuperAdminDto.from(superAdmin), HttpStatus.OK);

        }catch ( Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('SuperAdmin')")
    @DeleteMapping(value = "{email}/admins/{adminEmail}/remove")
    @ApiOperation(value = "add admin by superAdmin",
            notes = "add admin by superAdmin",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully cancelled assignment admin from superAdmin"),
            @ApiResponse(code = 404, message = "can not found this superAdmin oder admin with the given emails"),
    })
    public ResponseEntity<SuperAdminDto> deleteAdminFromSuperAdmin(@PathVariable final String email,
                                                              @PathVariable final String adminEmail) {
        try {
            SuperAdmin superAdmin = superAdminService.deleteAdminFromSuperAdmin(email, adminEmail);
            return new ResponseEntity<>(SuperAdminDto.from(superAdmin), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }



}
