package lutfia.example.donation.management.system.repository;


import lutfia.example.donation.management.system.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Long> {

}
