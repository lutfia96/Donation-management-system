package lutfia.example.donation.management.system.repository;

import lutfia.example.donation.management.system.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role ,Long> {
 Page<Role> findAll(Specification<Role> specification, Pageable pageable);
 Optional<Role> findById(Long id);



    //Optional<Role> findByUuid(UUID uuid);
}
