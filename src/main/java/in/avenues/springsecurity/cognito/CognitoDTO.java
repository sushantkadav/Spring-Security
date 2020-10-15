package in.avenues.springsecurity.cognito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CognitoDTO {
    private Long id;
    private Long userId;
    private String cognitoId;
    private Boolean isActive;
}
