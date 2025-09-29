package lutfia.example.donation.management.system.controller;

import lombok.AllArgsConstructor;
import lutfia.example.donation.management.system.dto.UserRequest;
import lutfia.example.donation.management.system.model.Users;
import lutfia.example.donation.management.system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UsersController {
    private UserService userService;


    @PostMapping
    public ResponseEntity<Users> registerUser(UserRequest request){
        Users user = userService.registerUser(request);
        return ResponseEntity.ok().body(user);
    }
}
