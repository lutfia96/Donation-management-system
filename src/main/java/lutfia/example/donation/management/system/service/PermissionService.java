package lutfia.example.donation.management.system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lutfia.example.donation.management.system.coreService.SimpleSearchService;
import lutfia.example.donation.management.system.model.Permission;
import lutfia.example.donation.management.system.model.Role;
import lutfia.example.donation.management.system.repository.PermissionRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionService extends SimpleSearchService {
    private final PermissionRepo permissionRepo;

    public Page<Permission> getAllPermissions(Pageable page, Map<String, String> search) {

        Specification spec = this.<Role>createSpecification(Permission.class, search);

      Page<Permission> permissions = permissionRepo.findAll(page);

      return permissions;
    }
}
