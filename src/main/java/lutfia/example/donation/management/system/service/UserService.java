package lutfia.example.donation.management.system.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lutfia.example.donation.management.system.dto.UserRequest;
import lutfia.example.donation.management.system.dto.UserRole;
import lutfia.example.donation.management.system.model.Role;
import lutfia.example.donation.management.system.model.Users;
import lutfia.example.donation.management.system.repository.RoleRepo;
import lutfia.example.donation.management.system.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private  final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final ModelMapper modelMapper;
    @Transient
    private final EntityManager em;

    public Users registerUser (UserRequest request ){
        Users user = modelMapper.map(request, Users.class);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        //user.setRoles(request.getRoles());
        return userRepo.save(user);
    }

    @Transactional
    public void assignRoleToUser(Long id, UserRole role){
      Users user =  userRepo.findById(id)
              .orElseThrow(() -> new ValidationException("User with id {" + id + "} not found"));

       // Role role1 = roleRepo.findById(role.getRoleIds());
   for (Long roleId : role.getRoleIds()){
       Role role1 = em.getReference(Role.class, roleId);
       user.getRoles().add(role1);
   }

        userRepo.save(user);

    }
}

