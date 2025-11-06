package lutfia.example.donation.management.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString(exclude = "roles")
@EqualsAndHashCode(exclude = "roles")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

    public Permission(String permissionName, String s) {
        this.name=permissionName;
    }
}
