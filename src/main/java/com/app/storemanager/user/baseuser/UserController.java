package com.app.storemanager.user.baseuser;

import com.app.storemanager.user.storemanager.standarduser.StandardUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.app.storemanager.user.storemanager.standarduser.StandardUser.convertUserToStandardUer;

@RestController
@CrossOrigin
@RequestMapping(value = "/users/")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    StandardUserService standardUserService;

    @PreAuthorize("hasAnyRole('Admin','SuperAdmin')")
    @PostMapping(value = "createStandardUser/")
    @ApiOperation(value = "create a standard user",
            notes = "add new user to DB",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added User"),
            @ApiResponse(code = 400, message = "Errors during input"),
    })
    public ResponseEntity<UserDto> createStandardUser(@RequestBody UserDto userDto) {
        try {
            User user = standardUserService.save(convertUserToStandardUer(User.from(userDto)));
            return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }

    @PreAuthorize("hasAnyRole('Admin','SuperAdmin','StoreManager','StandardUser')")
    @GetMapping("getUsers/")
    @ApiOperation(value = "view a list of available users present in the store",
            notes = "get all users",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "There is no user to display"),
    })
    public ResponseEntity<List<UserDto>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDto> usersDto = users.stream().map(UserDto::from).collect(Collectors.toList());
            return new ResponseEntity<>(usersDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
    }

    @PreAuthorize("hasAnyRole('Admin','SuperAdmin','StoreManager','StandardUser')")
    @GetMapping(value = "getUserByEmail/email/{email}")
    @ApiOperation(value = "search for user",
            notes = "get user by  email",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found User With This Email"),
    })
    public ResponseEntity<UserDto> getByEmail(@PathVariable final String email) {
        try {
            User user = userService.findByEmail(email);
            return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('Admin','SuperAdmin')")
    @DeleteMapping(value = "delete_user/email/{email}")
    @ApiOperation(value = "Delete user",
            notes = " delete user by email",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted User"),
            @ApiResponse(code = 404, message = "Not Found User With This Email"),
    })
    public ResponseEntity<UserDto> deleteUser(@PathVariable final String email) {
        try {
            User user = userService.deleteUser(email);
            return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PreAuthorize("hasAnyRole('Admin','SuperAdmin','StandardUser')")
    @PutMapping(value = "update_user/email/{email}")
    @ApiOperation(value = "Update user",
            notes = "edit user by id",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated User"),
            @ApiResponse(code = 404, message = "Not Found User With This Email"),
    })
    public ResponseEntity<UserDto> editUser(@PathVariable final String email, @RequestBody UserDto userDto) {
        try {
            User editUser = userService.editUser(email, userDto);
            return new ResponseEntity<>(UserDto.from(editUser), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping(value = "{email}/image/{id}/add")
    public ResponseEntity<UserDto> addImage(@PathVariable final String email, @PathVariable final Long id) {
        User user = userService.addImage(email, id);
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
    }


}
