package lutfia.example.donation.management.system.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authorities")
public class Authority extends BaseModel {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "resource")
    private String resource;

    @Column(name = "action")
    private String action;

    @JsonIgnore
    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles = new HashSet<>();

//    @JsonIgnore
//    @ManyToMany(mappedBy = "authorities")
//    private Set<MenuItem> menuItems = new HashSet<>();
}

