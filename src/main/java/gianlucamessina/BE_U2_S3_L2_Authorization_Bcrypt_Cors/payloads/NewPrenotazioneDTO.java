package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewPrenotazioneDTO(String volo,
                                 String alloggio,
                                 @NotEmpty(message = "è obbligatorio inserire l'id del dipendente che vuole effettuare la prenotazione!")
                                 @Size(min = 36,message = "formato dell'ID del dipendente non corretto!")
                                 String dipendenteId,
                                 @NotEmpty(message = "è obbligatorio inserire il viaggio per il quale si vuole effettuare la prenotazione!")
                                 @Size(min = 36,message = "formato dell'ID del viaggio non corretto!")
                                 String viaggioId) {
}
