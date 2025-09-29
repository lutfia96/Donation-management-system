package lutfia.example.donation.management.system.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lutfia.example.donation.management.system.dto.UserRequest;
import lutfia.example.donation.management.system.model.Users;
import lutfia.example.donation.management.system.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private  final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public Users registerUser (UserRequest request ){
        Users user = modelMapper.map(request, Users.class);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return userRepo.save(user);
    }
}

