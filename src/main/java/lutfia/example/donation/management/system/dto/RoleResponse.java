package lutfia.example.donation.management.system.dto;

import lombok.Data;
import lutfia.example.donation.management.system.model.Permission;
import lutfia.example.donation.management.system.model.Role;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class RoleResponse {
    //private Long roleId;
    private String name;
    private Set<Permission> permissions;

    public RoleResponse(Role role) {
        this.name = role.getName();
        this.permissions = role.getPermissions();
    }

    public RoleResponse withPermission(Set<Permission> authorities) {
//        setPermissions(authorities.stream().map(PermissionResponse::new)
//               .collect(Collectors.toList()));
        return this;
    }
}
