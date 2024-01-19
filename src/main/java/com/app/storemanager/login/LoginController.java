package com.app.storemanager.login;

import com.app.storemanager.common.Common;
import com.app.storemanager.login.authentification.AuthenticationRequest;
import com.app.storemanager.login.authentification.AuthenticationResponse;
import com.app.storemanager.login.password.PasswordResetResponse;
import com.app.storemanager.login.password.PasswordResetTokenService;
import com.app.storemanager.mail.MailBuilder;
import com.app.storemanager.user.baseuser.User;
import com.app.storemanager.user.baseuser.UserService;
import com.app.storemanager.user.storemanager.admin.Admin;
import com.app.storemanager.user.storemanager.admin.AdminService;
import com.app.storemanager.user.storemanager.standarduser.StandardUser;
import com.app.storemanager.user.storemanager.standarduser.StandardUserService;
import com.app.storemanager.user.superadmin.SuperAdminService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static com.app.storemanager.common.Common.getAppUrl;


@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    AdminService adminService;
    @Autowired
    StandardUserService standardUserService;
    @Autowired
    SuperAdminService superAdminService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Qualifier("myUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @Autowired
    private MailBuilder mailBuilder;

    @PostMapping(value = "/login/")
    @ApiOperation(value = "Log a user in the application",
            notes = "This api is called in the frontend on the login screen to check the user's credentials (Authentication)",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "When the logging is Successful"),
            @ApiResponse(code = 403, message = "When the account is expired"),
            @ApiResponse(code = 400, message = "When the credentials are wrong, either the password or the user name or both")
    })
    @ApiParam(value = "email", example = "dorian@yahoo.fr")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(authenticationRequest.getEmail());

            return ResponseEntity.status(200).body(new AuthenticationResponse(jwtUtil.generateToken(userDetails, new Common().getIpAddress(request)), "loggingSuccessful"
                    , userService.findByEmail(authenticationRequest.getEmail())));

        } catch (CredentialsExpiredException e) {
            return ResponseEntity.status(403).body(new AuthenticationResponse("Account Expired"));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).body(new AuthenticationResponse("Wrong Credentials"));
        }
    }

    @GetMapping(value = "/logout/")
    @ApiOperation(value = "Logout a user from the Server",
            notes = "This api is called in the frontend when the logout button is clicked",
            response = ResponseEntity.class)
    public void logout(String token) {
        jwtUtil.logout(token);
    }

    // Reset password
    @PostMapping("/password_forgotten/")
    @ApiOperation(value = "Reset the password for a user",
            notes = "For resetting the password, this is the first api to call after that an Email will be send to the user.",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "When the reset password was send"),
            @ApiResponse(code = 400, message = "When the email doesnt exist in the application"),
    })
    public ResponseEntity<PasswordResetResponse> passwordForgotten(final HttpServletRequest request, @RequestBody String userEmail) throws MessagingException {
        final User user = userService.findByEmail(userEmail);
        if (user != null) {
            final String token = UUID.randomUUID().toString();
            passwordResetTokenService.createPasswordResetTokenForUser(user, token);

            mailBuilder.constructAndSendResetVerificationTokenEmail(getAppUrl(request), token, user);
            return ResponseEntity.status(200).body(new PasswordResetResponse("Reset Password Email Send"));
        }
        return ResponseEntity.status(400).body(new PasswordResetResponse("This Email Is Doesn't exist"));

    }

    // New Password
    @PostMapping("/reset_password/")
    @ApiOperation(value = "This Api is send to the user per email",
            notes = "After the user has enter a new password on the GUI this APi is called to save it in the Database.",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "When the password was successfully reseted"),
    })
    public ResponseEntity<PasswordResetResponse> resetPassword(final HttpServletRequest request, @RequestParam("token") final String existingToken,
                                                               @RequestBody String newPassword) {

        String userEmail = passwordResetTokenService.getPasswordResetToken(existingToken).getUser().getEmail();
        User user = userService.getUserRef(userEmail);
        user.setPassword(newPassword);
        userService.addUser(user);
        return ResponseEntity.status(200).body(new PasswordResetResponse("Password was succ changed in the Database"));
    }

    public void createTestUser() {

        Admin admin = Admin.builder()
                .birthday(LocalDate.of(1967, Month.JANUARY, 17))
                .creationDate(LocalDate.now())
                .accountValidTill(LocalDate.of(2021, Month.DECEMBER, 17))
                .email("kemgangd@yahoo.fr")
                .firstName("dorian")
                .address("FriederStr.10, 4254")
                .lastName("kemgang")
                .password("test")
                .phoneNumber("01798982333")
                .build();

        adminService.save(admin);

        StandardUser standardUser2 = StandardUser.builder()
                .birthday(LocalDate.of(1967, Month.JANUARY, 17))
                .creationDate(LocalDate.now())
                .accountValidTill(LocalDate.of(2021, Month.DECEMBER, 17))
                .email("kahyen.lim1@hs-augsburg.de")
                .firstName("lim")
                .lastName("kahyen")
                .address("BohnStr. 10, 6499")
                .password("test")
                .phoneNumber("01798982333")
                .build();

        standardUserService.save(standardUser2);

        StandardUser standardUser = StandardUser.builder()
                .birthday(LocalDate.of(1967, Month.JANUARY, 17))
                .accountValidTill(LocalDate.of(2021, Month.DECEMBER, 17))
                .creationDate(LocalDate.now())
                .email("paul@gmail.com")
                .firstName("schmid")
                .address("WolfStr. 54, 8699")
                .lastName("musterName")
                .password("test")
                .phoneNumber("01798582333")
                .build();
        standardUserService.save(standardUser);

        StandardUser standardUser1 = StandardUser.builder()
                .birthday(LocalDate.of(1967, Month.JANUARY, 17))
                .accountValidTill(LocalDate.of(2021, Month.DECEMBER, 17))
                .creationDate(LocalDate.now())
                .email("thomas@gmail.fr")
                .firstName("thomas")
                .lastName("mueller")
                .address("PrinzStr. 51, 3699")
                .password("test")
                .phoneNumber("01798482333")
                .build();
        standardUserService.save(standardUser1);

    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/testRoles/")
    @ApiOperation(value = "Log a user in the application",
            notes = "This api is called in the frontend on the login screen to check the user's credentials (Authentication)",
            response = ResponseEntity.class)
    public String testRole() {
        return "This user has access to this API and has an Admin Role";
    }

}

