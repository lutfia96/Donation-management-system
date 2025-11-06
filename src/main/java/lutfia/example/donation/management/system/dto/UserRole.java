package lutfia.example.donation.management.system.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRole {
    private List<Long> roleIds = new ArrayList<>();
}
