package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "data_richiesta")
    private LocalDate dataRichiesta;
    private String volo;
    private String alloggio;
    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;
    @ManyToOne
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;

    public Prenotazione(LocalDate dataRichiesta, String volo, String alloggio, Dipendente dipendente, Viaggio viaggio) {
        this.dataRichiesta = dataRichiesta;
        this.volo = volo;
        this.alloggio = alloggio;
        this.dipendente = dipendente;
        this.viaggio = viaggio;
    }
}
