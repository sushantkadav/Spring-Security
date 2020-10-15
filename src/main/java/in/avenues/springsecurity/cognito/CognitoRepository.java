package in.avenues.springsecurity.cognito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface CognitoRepository extends JpaRepository<Cognito, Serializable> {

    Optional<Cognito> findCognitoByUserId(Long userId);
    Optional<Cognito> findCognitoByCognitoId(String cognitoId);
}
