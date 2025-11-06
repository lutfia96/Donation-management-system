package lutfia.example.donation.management.system.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String password;
    private String email;
    //private Set<Role> roles = new HashSet<>();

}
