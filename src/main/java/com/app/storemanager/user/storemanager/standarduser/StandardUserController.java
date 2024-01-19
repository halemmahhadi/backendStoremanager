package com.app.storemanager.user.storemanager.standarduser;

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
@RequestMapping("/standard_user/")
public class StandardUserController {
    @Autowired
    StandardUserService standardUserService;

    @PreAuthorize("hasAnyRole('Admin','SuperAdmin')")
    @PostMapping(value = "createStandardUser/")
    @ApiOperation(value = "create standardUser",
            notes = "add new standardUser to DB",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added standardUser"),
            @ApiResponse(code = 400, message = "Errors during input"),
    })
    public ResponseEntity<StandardUserDto> addStandardUser(StandardUserDto standardUserDto) {
        try {
            StandardUser standardUser = standardUserService.save(StandardUser.from(standardUserDto));
            return new ResponseEntity<>(StandardUserDto.from(standardUser), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('Admin','SuperAdmin')")
    @GetMapping(value = "getStandardUserByEmail/email/{email}")
    @ApiOperation(value = "search for standardUser",
            notes = "get standardUser by  email",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Can not found StandardUser with this email "),
    })
    public ResponseEntity<StandardUserDto> getByEmail(@PathVariable final String email) {
        StandardUser standardUser = standardUserService.findByEmail(email);
        if (standardUser != null){
            return new ResponseEntity<>(StandardUserDto.from(standardUser), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('Admin','SuperAdmin')")
    @DeleteMapping(value = "deleteStandardUser/email/{email}")
    @ApiOperation(value = "Delete standardUser",
            notes = " delete standardUser by email",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted standardUser"),
            @ApiResponse(code = 404, message = "can not found this standardUser with this email")

    })
    public ResponseEntity<StandardUserDto> deleteUserStandard(@PathVariable final String email) {
        try {
            StandardUser standardUser = standardUserService.delete(email);
            return new ResponseEntity<>(StandardUserDto.from(standardUser), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);


        }
    }

    @PreAuthorize("hasAnyRole('Admin','SuperAdmin')")
    @PutMapping(value = "updateStandardUser/email/{email}")
    @ApiOperation(value = "Update standardUser",
            notes = "edit standardUser by email",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated standardUser"),
            @ApiResponse(code = 404, message = "can not found this standardUser with this email")

    })
    public ResponseEntity<StandardUserDto> editStandardUser(@PathVariable final String email, StandardUserDto standardUserDto) {
        try {
            StandardUser editStandardUser = standardUserService.editStandardUser(email, StandardUser.from(standardUserDto));
            return new ResponseEntity<>(StandardUserDto.from(editStandardUser), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
    }

}
