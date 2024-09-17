package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads;

import java.time.LocalDateTime;

public record ErrorsResponseDTO(String message, LocalDateTime timeStamp) {
}
