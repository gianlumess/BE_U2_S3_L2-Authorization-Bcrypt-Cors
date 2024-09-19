package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.controllers;

import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.entities.Dipendente;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.exceptions.BadRequestException;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads.NewDipendenteDTO;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {
    @Autowired
    private DipendenteService dipendenteService;

    //GET DELLA LISTA DI DIPENDENTI (http://localhost:3001/dipendenti)
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')") //solo gli admin possono vedere la lista dei dipendenti
    public Page<Dipendente> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "15") int size,
                                    @RequestParam(defaultValue = "id") String sortBy){

        return this.dipendenteService.findAll(page,size,sortBy);
    }

    //POST (http://localhost:3001/dipendenti)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dipendente save(@RequestBody @Validated NewDipendenteDTO body, BindingResult validation){
        // @Validated serve per 'attivare' le regole di validazione descritte nel DTO
        // BindingResult permette di capire se ci sono stati errori e quali

        if(validation.hasErrors()){
            String messages=validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: "+messages);
        }

        return this.dipendenteService.save(body);
    }

    //GET FIND BY ID (http://localhost:3001/dipendenti/{dipendenteId})
    @GetMapping("/{dipendenteId}")
    public Dipendente findById(@PathVariable UUID dipendenteId){
            return this.dipendenteService.findById(dipendenteId);
    }

    //PUT
    @PutMapping("/{dipendenteId}")
    @PreAuthorize("hasAuthority('ADMIN')") //solo gli admin possono modificare un utente
    public Dipendente findByIdAndUpdate(@PathVariable Dipendente dipendente, @RequestBody @Validated NewDipendenteDTO body){
        return this.dipendenteService.findByIdAndUpdate(dipendente.getId(),body);
    }

    //DELETE
    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')") //solo gli admin possono cancellare un utente
    public void findByIdAndDelete(@PathVariable UUID dipendenteId){
        this.dipendenteService.findByIdAndDelete(dipendenteId);
    }

    //UPLOAD PROFILE PICTURE DEL DIPENDENTE
    @PatchMapping("/{dipendenteId}/pic")
    public Dipendente uploadProfilePic(@PathVariable UUID dipendenteId, @RequestParam("pic")MultipartFile pic) throws IOException {
        return this.dipendenteService.uploadProfilePicture(dipendenteId,pic);
    }

    //ENDPOINTS ACCESSIBILI CON DATI ESCLUSIVAMENTE APPARTENENTI A CHI EFFETTUA LA RICHIESTA

    @GetMapping("/me")
    public Dipendente getMyProfile(@AuthenticationPrincipal Dipendente dipendente){
        // Tramite @AuthenticationPrincipal posso accedere ai dati dell'utente che sta effettuando la richiesta
        return dipendente;
    }

    @PutMapping("/me")
    public Dipendente updateMyProfile(@AuthenticationPrincipal Dipendente dipendente,@RequestBody @Validated NewDipendenteDTO body){
        return this.dipendenteService.findByIdAndUpdate(dipendente.getId(),body);
    }

    @DeleteMapping("/me")
    public void deleteMyProfile(@AuthenticationPrincipal Dipendente dipendente){
        this.dipendenteService.findByIdAndDelete(dipendente.getId());
    }
}
