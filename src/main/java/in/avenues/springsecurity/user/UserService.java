package in.avenues.springsecurity.user;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Boolean save(UserDTO payload) {
        User user = new User();
        modelMapper.map(payload, user);
        User savedUser = userRepository.save(user);
        return savedUser != null ? true : false;
    }

    public UserDTO getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.isPresent() ? modelMapper.map(userOptional.get(), UserDTO.class) : null;
    }

    public UserDTO getByUserName(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        return userOptional.isPresent() ? modelMapper.map(userOptional.get(), UserDTO.class) : null;
    }
}
