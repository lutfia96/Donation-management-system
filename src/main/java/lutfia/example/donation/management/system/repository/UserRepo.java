package lutfia.example.donation.management.system.repository;


import lutfia.example.donation.management.system.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;
@EnableJpaRepositories
public interface UserRepo extends JpaRepository<Users, Long> {

   Optional <Users> findByEmail(String email);
}
