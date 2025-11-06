package lutfia.example.donation.management.system.repository;

import lutfia.example.donation.management.system.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepo extends JpaRepository<Permission ,Long> {
    Permission findByName(String name);
}
