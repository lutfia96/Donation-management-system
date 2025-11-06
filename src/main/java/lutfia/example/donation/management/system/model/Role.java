package lutfia.example.donation.management.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "permissions")
@EqualsAndHashCode(exclude = "permissions")
public class Role {                   //extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;

    @Column(name = "name", unique = true) //nullable = false)
    private String name;

    @JsonIgnore
    @Basic(optional = false)
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    //    @CreatedBy
//    @JsonIgnore
//    @Basic(optional = false)
//    @Column(name = "created_by",nullable = true)
//    private Users createdBy;
//
    @LastModifiedDate
    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
//
//    @LastModifiedBy
//    @JsonIgnore
//    @Column(name = "updated_by",nullable = true)
//    private Users updatedBy;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

//    public void setPermissions(Set<Long> permissionIds) {
//    }


    public void addPermission(Permission permission) {
        this.permissions.add(permission);
        permission.getRoles().add(this);

    }

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<Users> users = new HashSet<>();
}
