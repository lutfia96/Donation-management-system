package lutfia.example.donation.management.system.repository;

import lutfia.example.donation.management.system.dto.DonorResponse;
import lutfia.example.donation.management.system.model.Donors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DonorsRepo extends JpaRepository<Donors, UUID> {

    Page<DonorResponse> findAll(Specification spec, Pageable pageable);

    Optional<Donors> findById(Long id);

    void deleteById(Long id);
}
