package in.avenues.springsecurity.otp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Otp {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private String otpCode;
    private Boolean isExpired = false;

}
