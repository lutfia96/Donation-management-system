package lutfia.example.donation.management.system.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lutfia.example.donation.management.system.coreService.SimpleSearchService;
import lutfia.example.donation.management.system.dto.DonorRequest;
import lutfia.example.donation.management.system.dto.DonorResponse;
import lutfia.example.donation.management.system.model.Donors;
import lutfia.example.donation.management.system.model.Users;
import lutfia.example.donation.management.system.repository.DonorsRepo;
import lutfia.example.donation.management.system.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class DonorsService extends SimpleSearchService {
    private final DonorsRepo donorsRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public DonorResponse findById(Long id) {

        Donors donors = donorsRepo.findById(id)

                .orElseThrow(() -> new ValidationException("User with id {" + id + "} not found"));


        return modelMapper.map(donors, DonorResponse.class);

    }


    public Donors addDonors(DonorRequest donors) {

        Users user =userRepo.findById(donors.getUserId())

                .orElseThrow(() -> new ValidationException("User with id {" + donors.getUserId() + "} not found"));
//Donors donors2 = new ArrayList<Donors>();

Donors donors1 = modelMapper.map(donors, Donors.class);

donors1.setId(null);
    donors1.setUser(user);
    donors1.setName(donors.getName());
    donors1.setAddress(donors.getAddress());
    donors1.setEmail(donors.getEmail());
    donors1.setContact(donors.getContact());
    donors1.setCreatedAt(OffsetDateTime.now());
    donors1.setCreatedBy(donors.getUserId().toString());
  return  donorsRepo.save(donors1);
    }


    public Page<DonorResponse> getAllDonors(Pageable pageable, Map<String, String> search) {

        Specification spec = this.createSpecification(Donors.class, search);

        Page<DonorResponse> donors = donorsRepo.findAll(spec,pageable);

        return donors;
    }

@Transactional
    public boolean deleteDonors(Long id) {
Donors donors = donorsRepo.findById(id)

        .orElseThrow(() -> new ValidationException("User with id {" + id + "} not found"));
if (donors.getId() == null) {
    return false;
}
        donorsRepo.deleteById(id);
        return true;
    }

}
