package lutfia.example.donation.management.system.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lutfia.example.donation.management.system.configs.JwtUtils;
import lutfia.example.donation.management.system.dto.LoginRequest;
import lutfia.example.donation.management.system.dto.LoginResponse;
import lutfia.example.donation.management.system.dto.UserRequest;
import lutfia.example.donation.management.system.model.Users;
import lutfia.example.donation.management.system.service.CustomUserDetailsService;
import lutfia.example.donation.management.system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
