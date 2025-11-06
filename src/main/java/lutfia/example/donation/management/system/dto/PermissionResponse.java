package lutfia.example.donation.management.system.dto;

import lombok.Data;
import lutfia.example.donation.management.system.model.Permission;

@Data
public class PermissionResponse {
    private String name;
    public PermissionResponse(Permission permission) {
        //this.id = permission.getId();
        this.name = permission.getName();
    }
}
