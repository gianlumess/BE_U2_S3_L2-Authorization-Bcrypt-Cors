package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.repositories;

import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ViaggiRepository extends JpaRepository<Viaggio, UUID> {

}
