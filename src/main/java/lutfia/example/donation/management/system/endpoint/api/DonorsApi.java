package lutfia.example.donation.management.system.endpoint.api;

import lutfia.example.donation.management.system.coreService.CustomApiResponse;
import lutfia.example.donation.management.system.dto.DonorRequest;
import lutfia.example.donation.management.system.dto.DonorResponse;
import lutfia.example.donation.management.system.model.Donors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RequestMapping("/api/v1/donors")
public interface DonorsApi {

    @PostMapping
     ResponseEntity<Donors> addDonors(DonorRequest donors);

    @GetMapping("/getAll")
     ResponseEntity<Page<DonorResponse>> getAllDonors(Pageable pageable, Map<String, String> search);

    @GetMapping("/{id}/getby")
    ResponseEntity<DonorResponse> getDonors(@PathVariable Long id);

    @DeleteMapping("/{id}/deletebyId")
    CustomApiResponse deleteDonors(@PathVariable Long id);
}
