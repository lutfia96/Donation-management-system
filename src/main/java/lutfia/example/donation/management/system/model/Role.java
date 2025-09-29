package lutfia.example.donation.management.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lutfia.example.donation.management.system.anotations.IsTrackable;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
// @IsTrackable
public class Role extends BaseModel {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "level_id")
    private Long levelId;

//    @ManyToOne( fetch = FetchType.LAZY)
//    @JoinColumn(name = "level_id", insertable = false, updatable = false)
//    @JsonIgnoreProperties({"roles", "adminHierarchies"})
//    private AdminHierarchyLevel level;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties({"roles", "adminHierarchy"})
    private Set<Users> users = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "role_authorities",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    @JsonIgnoreProperties({"menuItems", "roles"})
    @IsTrackable
    private Set<Authority> authorities = new HashSet<>();

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
        authority.getRoles().add(this);
    }

    public void removeAuthority(Authority authority) {
        authorities.remove(authority);
        authority.getRoles().remove(this);
    }
}
