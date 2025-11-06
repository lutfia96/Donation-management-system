package lutfia.example.donation.management.system.endpoint.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lutfia.example.donation.management.system.coreService.CustomApiResponse;
import lutfia.example.donation.management.system.dto.RolePermission;
import lutfia.example.donation.management.system.dto.RoleRequest;
import lutfia.example.donation.management.system.endpoint.api.RoleApi;
import lutfia.example.donation.management.system.model.Role;
import lutfia.example.donation.management.system.service.RoleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class RoleController implements RoleApi {
    private final RoleService roleService;


    @GetMapping("/getAll")
    public CustomApiResponse getAll(Pageable page, @RequestParam() Map<String, String> search){
        return CustomApiResponse.ok(roleService.getAllRoles( PageRequest.of(
                        page.getPageNumber(),
                        page.getPageSize()),
                search));
    }


    @PostMapping("/{id}/assign-authorities")
    @Transactional
    public CustomApiResponse assignAuthorities(
            @Valid @RequestBody RolePermission dto, @PathVariable Long id) {
        roleService.assignAuthorities(id, dto);
        return CustomApiResponse.ok("Role updated successfully");
    }

    @Override
    public ResponseEntity<CustomApiResponse> addrole(RoleRequest role) {
        Role role1 = roleService.saveRole(role);
      return  ResponseEntity.status(HttpStatus.CREATED).body(CustomApiResponse.created("Role created succsessfully",role1));

    }

    @Override
    public CustomApiResponse getById(Long id) {
        return CustomApiResponse.ok("successful",roleService.findById(id));
    }

//    @Override
//    public CustomApiResponse assignAuthorities(Long id,  @Valid @RequestBody RolePermission dto) {
//        roleService.assignAuthorities(id, dto);
//        return CustomApiResponse.ok("Role updated successfully");
//    }
}
