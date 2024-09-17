package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewDipendenteDTO(@NotEmpty(message = "L'username è obbligatorio!")
                               @Size(min = 4,max = 30,message = "L'username deve essere compreso tra i 4 e i 30 caratteri!")
                               String username,
                               @NotEmpty(message = "Il nome è obbligatorio!")
                               @Size(min = 3,max = 40,message = "Il nome deve essere compreso tra i 3 e i 40 caratteri!")
                               String nome,
                               @NotEmpty(message = "Il cognome è obbligatorio!")
                               @Size(min = 3,max = 40,message = "Il cognome deve essere compreso tra i 3 e i 40 caratteri!")
                               String cognome,
                               @NotEmpty(message = "L'email è obbligatoria!")
                               @Email(message = "L'email inserita non è valida!")
                               String email,
                               @NotEmpty(message = "La password è obbligatoria!")
                               String password) {
}
