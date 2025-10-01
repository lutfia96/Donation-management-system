package lutfia.example.donation.management.system.service;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.AllArgsConstructor;
import lutfia.example.donation.management.system.model.Users;
import lutfia.example.donation.management.system.repository.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
        return new User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getId().toString()))
                        .collect(Collectors.toList())
        );
    }
}
