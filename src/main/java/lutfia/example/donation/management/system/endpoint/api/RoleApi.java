package lutfia.example.donation.management.system.endpoint.api;

import lutfia.example.donation.management.system.coreService.CustomApiResponse;
import lutfia.example.donation.management.system.dto.RoleRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/role")
public interface RoleApi {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CustomApiResponse> addrole(@RequestBody RoleRequest role);

    @GetMapping("/ById/{id}")
    CustomApiResponse getById(@PathVariable Long id);

//    @PostMapping("/{id}/assign-authorities")
//    @Transactional
//    CustomApiResponse assignAuthorities(@PathVariable Long id,  @Valid @RequestBody RolePermission dto);
}
