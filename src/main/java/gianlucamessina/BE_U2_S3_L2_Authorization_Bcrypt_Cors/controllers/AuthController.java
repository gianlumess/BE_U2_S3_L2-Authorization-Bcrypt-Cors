package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.controllers;

import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.entities.Dipendente;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.exceptions.BadRequestException;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads.DipendenteLoginDTO;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads.DipendenteLoginRespDTO;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads.NewDipendenteDTO;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.services.AuthService;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    DipendenteService dipendenteService;

    @PostMapping("/login")
    public DipendenteLoginRespDTO login(@RequestBody @Validated DipendenteLoginDTO payload){
        return new DipendenteLoginRespDTO(this.authService.controlloCredenzialiEdGenerazioneToken(payload));
    }

    //POST (http://localhost:3001/auth/register)
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente save(@RequestBody @Validated NewDipendenteDTO body, BindingResult validation){
        // @Validated serve per 'attivare' le regole di validazione descritte nel DTO
        // BindingResult permette di capire se ci sono stati errori e quali

        if(validation.hasErrors()){
            String messages=validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: "+messages);
        }

        return this.dipendenteService.save(body);
    }

}
