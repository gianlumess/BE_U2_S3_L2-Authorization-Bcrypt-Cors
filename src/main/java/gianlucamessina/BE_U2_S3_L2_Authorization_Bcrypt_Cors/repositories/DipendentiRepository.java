package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.repositories;

import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface DipendentiRepository extends JpaRepository<Dipendente, UUID> {

        Optional<Dipendente> findByUsername(String username);
        Optional<Dipendente> findByEmail(String email);
}
