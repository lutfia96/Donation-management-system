package lutfia.example.donation.management.system.endpoint.controller;

import lombok.AllArgsConstructor;
import lutfia.example.donation.management.system.coreService.CustomApiResponse;
import lutfia.example.donation.management.system.dto.DonorRequest;
import lutfia.example.donation.management.system.dto.DonorResponse;
import lutfia.example.donation.management.system.endpoint.api.DonorsApi;
import lutfia.example.donation.management.system.model.Donors;
import lutfia.example.donation.management.system.service.DonorsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class DonorsController implements DonorsApi {
    private final DonorsService donorsService;

    @Override
    public ResponseEntity<Donors> addDonors(DonorRequest donors) {
       Donors donors1 = donorsService.addDonors(donors);
        return  ResponseEntity.ok(donors1) ;
    }

    @Override
    public ResponseEntity<Page<DonorResponse>> getAllDonors(Pageable pageable, Map<String, String> search) {
        Page<DonorResponse>donors = donorsService.getAllDonors(pageable, search);
        //return ResponseEntity.ok(donors);
        return ResponseEntity.status(HttpStatus.OK).body(donors);
    }

    @Override
    public ResponseEntity<DonorResponse> getDonors(Long id) {

        return ResponseEntity.ok(donorsService.findById(id));
    }

    @Override
    public CustomApiResponse deleteDonors(Long id) {
       boolean deleted = donorsService.deleteDonors(id);

        if (!deleted) {
            return CustomApiResponse.ok("Donor not found or could not be deleted");
        }
        return CustomApiResponse.ok("Deleted successfully");
    }


}
