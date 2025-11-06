package lutfia.example.donation.management.system.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RolePermission {
    private List<Long> permissionIds = new ArrayList<>();
}
