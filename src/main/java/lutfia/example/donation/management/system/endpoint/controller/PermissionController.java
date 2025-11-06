package lutfia.example.donation.management.system.endpoint.controller;


import lombok.AllArgsConstructor;
import lutfia.example.donation.management.system.coreService.CustomApiResponse;
import lutfia.example.donation.management.system.model.Permission;
import lutfia.example.donation.management.system.repository.PermissionRepo;
import lutfia.example.donation.management.system.service.PermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/permission")
@AllArgsConstructor
public class PermissionController {
   private final PermissionService permissionService;

   @GetMapping("/getAll")
   public CustomApiResponse getPermissions(Pageable page, @RequestParam() Map<String, String> search) {

      Page<Permission> permissions = permissionService.getAllPermissions(page,search);

      return CustomApiResponse.ok(permissions);
   }
}
