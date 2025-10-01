package lutfia.example.donation.management.system.dto;

import lombok.Data;
import lutfia.example.donation.management.system.model.Role;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserRequest {
    private String name;
    private String password;
    private String email;
    private Set<Role> roles = new HashSet<>();

}
