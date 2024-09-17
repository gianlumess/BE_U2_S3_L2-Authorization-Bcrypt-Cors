package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.repositories;

import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.entities.Dipendente;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {

    Optional<Prenotazione> findByDipendenteAndViaggioData(Dipendente dipendente, LocalDate dataViaggio);
}
