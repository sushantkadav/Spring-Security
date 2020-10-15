package in.avenues.springsecurity.otp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Serializable> {

    Optional<Otp> findOtpByUserId(Long userId);
    Optional<Otp> findOtpByOtpCodeAndIsExpiredFalse(String otp);
}
