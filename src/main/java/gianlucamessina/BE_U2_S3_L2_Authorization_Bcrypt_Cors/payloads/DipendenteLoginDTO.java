package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DipendenteLoginDTO(@NotBlank(message = "L'email è obbligatoria!")
                                 @Email(message = "formato email non valido!")
                                 String email,
                                 String password) {
}
