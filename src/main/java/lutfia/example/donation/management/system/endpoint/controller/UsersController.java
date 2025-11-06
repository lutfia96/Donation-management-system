package lutfia.example.donation.management.system.endpoint.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lutfia.example.donation.management.system.configs.JwtUtils;
import lutfia.example.donation.management.system.coreService.CustomApiResponse;
import lutfia.example.donation.management.system.dto.*;
import lutfia.example.donation.management.system.model.Users;
import lutfia.example.donation.management.system.service.CustomUserDetailsService;
import lutfia.example.donation.management.system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UsersController {
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;


    @PostMapping
    public ResponseEntity<Users> registerUser(@RequestBody UserRequest request){
        Users user = userService.registerUser(request);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse>login(@Valid  @RequestBody LoginRequest request, HttpServletResponse response){
        Authentication auth =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),request.getPassword()
                )
        );
        var token = jwtUtils.generateAccessToken(request.getEmail());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/{id}/assign-role")
    @Transactional
    public CustomApiResponse assignRole(@PathVariable Long id, @RequestBody UserRole role){

        userService.assignRoleToUser(id , role);

        return CustomApiResponse.ok("Role with id {" + id + "} has been assigned successfully");
    }
}
