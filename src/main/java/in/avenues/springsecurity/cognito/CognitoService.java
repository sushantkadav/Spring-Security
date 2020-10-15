package in.avenues.springsecurity.cognito;

import in.avenues.springsecurity.user.UserDTO;
import in.avenues.springsecurity.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CognitoService {

    private CognitoRepository cognitoRepository;
    private ModelMapper modelMapper;
    private UserService userService;

    public CognitoService(CognitoRepository cognitoRepository, ModelMapper modelMapper, UserService userService) {
        this.cognitoRepository = cognitoRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public Boolean generateCognitoId(String username) {
        UserDTO userDTO = userService.getByUserName(username);
        Long userId = userDTO.getId();

        String cognitoId = UUID.randomUUID().toString();

        Cognito cognito = new Cognito();
        cognito.setUserId(userId);
        cognito.setCognitoId(cognitoId);

        Cognito savedCognito = cognitoRepository.save(cognito);
        return savedCognito != null ? true : false;
    }

    public CognitoDTO getCognitoByUsername(String username) {
        UserDTO userDTO = userService.getByUserName(username);
        Long userId = userDTO.getId();

        Optional<Cognito> cognitoOptional = cognitoRepository.findCognitoByUserId(userId);
        return cognitoOptional.isPresent() ? modelMapper.map(cognitoOptional.get(), CognitoDTO.class) : null;
    }

    private CognitoDTO getByCognitoId(String cognitoId) {
        Optional<Cognito> cognitoOptional = cognitoRepository.findCognitoByCognitoId(cognitoId);
        if (cognitoOptional.isPresent()) {
            return modelMapper.map(cognitoOptional, CognitoDTO.class);
        }
        return null;
    }


}
