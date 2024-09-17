package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
