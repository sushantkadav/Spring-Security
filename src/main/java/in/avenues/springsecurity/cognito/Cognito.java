package in.avenues.springsecurity.cognito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cognito {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private String cognitoId;
    private Boolean isExpired = false;
    private Boolean isActive = true;
}
