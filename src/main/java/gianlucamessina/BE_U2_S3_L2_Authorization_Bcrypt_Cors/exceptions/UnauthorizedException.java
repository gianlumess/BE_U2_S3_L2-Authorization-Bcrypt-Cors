package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
