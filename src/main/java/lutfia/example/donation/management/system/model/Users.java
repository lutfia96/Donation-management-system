package lutfia.example.donation.management.system.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String name;
    // @Email
    @Column(name = "email", unique = true)
    private String email;
    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @JsonIgnoreProperties({"users", "authorities"})
    private Set<Role> roles = new HashSet<>();

//    public void addRole(Role role) {
//        roles.add(role);
//        role.getUsers().add(this);
//    }

//    public void removeRole(Role role) {
//        roles.remove(role);
//        role.getUsers().remove(this);
//    }

}

