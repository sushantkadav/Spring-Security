package in.avenues.springsecurity.otp;

import in.avenues.springsecurity.user.UserDTO;
import in.avenues.springsecurity.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class OtpService {

    private OtpRepository otpRepository;
    private ModelMapper modelMapper;
    private UserService userService;

    public OtpService(OtpRepository otpRepository, ModelMapper modelMapper, UserService userService) {
        this.otpRepository = otpRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public Boolean generateOtpCode(String username) {
        UserDTO userDTO = userService.getByUserName(username);
        Long userId = userDTO.getId();

        String code = String.valueOf(new Random().nextInt(9999) + 1000);

        Otp otp = new Otp();
        otp.setUserId(userId);
        otp.setOtpCode(code);

        Otp savedOtp = otpRepository.save(otp);
        return savedOtp != null ? true : false;
    }

    public Otp getByUsername(String username) {
        UserDTO userDTO = userService.getByUserName(username);
        Long userId = userDTO.getId();
        try {
            Optional<Otp> otpOptional = otpRepository.findOtpByUserId(userId);
            return otpOptional.isPresent() ? modelMapper.map(otpOptional.get(), Otp.class) : null;
        } catch (Exception ex) {
            System.out.println("No otp found for user");
            ex.printStackTrace();
        }
        return null;
    }

//    public Otp getByOtpCode(String otpCode) {
//        Optional<Otp> otpOptional = otpRepository.findOtpByOtpCodeAndIsExpiredFalse(otpCode);
//        if (otpOptional.isPresent()) {
//            return modelMapper.map(otpOptional.get(), Otp.class);
//        }
//        return null;
//    }


}
