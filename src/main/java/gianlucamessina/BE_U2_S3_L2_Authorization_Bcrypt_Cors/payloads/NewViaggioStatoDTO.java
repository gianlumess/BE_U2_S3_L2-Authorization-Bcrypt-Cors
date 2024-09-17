package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads;

import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.enums.StatoViaggio;
import jakarta.validation.constraints.NotNull;

public record NewViaggioStatoDTO(@NotNull(message = "devi passare un valore tra 'IN_PROGRAMMA' e 'COMPLETATO', non può essere vuoto!")
                                 StatoViaggio statoViaggio) {
}
