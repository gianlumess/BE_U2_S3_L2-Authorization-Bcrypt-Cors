package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.services;

import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.entities.Dipendente;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.exceptions.UnauthorizedException;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads.DipendenteLoginDTO;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private JWTTools jwtTools;
    @Autowired
    PasswordEncoder bCrypt;

    public String controlloCredenzialiEdGenerazioneToken(DipendenteLoginDTO body){
        //controllo tramite email se l'utente esiste
        Dipendente found=this.dipendenteService.findByEmail(body.email());
        if(bCrypt.matches(body.password(), found.getPassword())){
            return jwtTools.createToken(found);
        }else {
            throw  new UnauthorizedException("Credenziali sbagliate!");
        }
    }
}
