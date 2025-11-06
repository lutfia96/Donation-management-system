package lutfia.example.donation.management.system.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lutfia.example.donation.management.system.coreService.SimpleSearchService;
import lutfia.example.donation.management.system.dto.RolePermission;
import lutfia.example.donation.management.system.dto.RoleRequest;
import lutfia.example.donation.management.system.dto.RoleResponse;
import lutfia.example.donation.management.system.model.Permission;
import lutfia.example.donation.management.system.model.Role;
import lutfia.example.donation.management.system.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class RoleService extends SimpleSearchService{
    private final RoleRepo roleRepo;
    private final ModelMapper modelMapper;
    @Transient
    private final EntityManager em;


//    public Page getAllRoles(Pageable page, Map<String, String> search){
//        log.info("Loading paginated roles with page {} and search {} ", page, search);
//        return roleRepo.findAll(createSpecification(Role.class, search), page).map(RoleResponse::new);
//    }
    public Page<RoleResponse> getAllRoles(Pageable page, Map<String, String> search){
        log.info("Loading paginated roles with page {} and search {} ", page, search);

        Specification spec = this.<Role>createSpecification(Role.class, search);

        Page<Role> rolesPage = roleRepo.findAll(spec, page);

      //  return rolesPage;

        return rolesPage.map(RoleResponse::new);
    }

    public Role saveRole(RoleRequest request){
        Role role = modelMapper.map(request , Role.class);
        role.setName(request.getName());

        log.info("RoleRequest name: {}", role.getName());
        System.out.println("Request name = " + request.getName());
        //role.setPermissions();
        return roleRepo.save(role);
    }

    public RoleResponse findById(Long id){
        log.info("finding role with uuid {} ", id);
        return roleRepo.findById(id).map(r-> new RoleResponse(r).withPermission(r.getPermissions()))
                .orElseThrow(()->new ValidationException("Role with id {" + id + "} not found"));
    }
@Transactional
    public void assignAuthorities(Long id, RolePermission dto) {
        Role role =
                roleRepo
                        .findById(id)
                        .orElseThrow(() -> new ValidationException("Role with uuid {" + id + "} not found"));
  //This line wipes out all existing permissions from the role before you start adding new ones.
        //role.setPermissions(new HashSet<>());

        for (Long authorityId : dto.getPermissionIds()) {
            Permission permission = em.getReference(Permission.class, authorityId);
            role.addPermission(permission);
        }
        log.info(role + " 3333has been assigned to role with uuid " + id);
        roleRepo.save(role);
        log.info(role + " has been assigned to role with uuid88888888" + id);
    //return  roleRepo.save(role);
}


}
