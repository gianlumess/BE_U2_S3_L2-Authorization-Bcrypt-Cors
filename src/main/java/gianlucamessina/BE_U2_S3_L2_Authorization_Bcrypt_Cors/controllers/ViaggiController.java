package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.controllers;

import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.entities.Viaggio;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.exceptions.BadRequestException;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads.NewViaggioDTO;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads.NewViaggioStatoDTO;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/viaggi")
public class ViaggiController {
    @Autowired
    private ViaggioService viaggioService;

    //GET DELLA LISTA DI VIAGGI (http://localhost:3001/viaggi)
    @GetMapping
    public Page<Viaggio> findAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "15") int size,
                                 @RequestParam(defaultValue = "id") String sortBy){

        return this.viaggioService.findAll(page,size,sortBy);
    }

    //POST (http://localhost:3001/viaggi)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio save(@RequestBody @Validated NewViaggioDTO body, BindingResult validation){
        if(validation.hasErrors()){
            String messages=validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: "+messages);
        }

        return this.viaggioService.save(body);
    }

    //GET FIND BY ID (http://localhost:3001/viaggi/{viaggiId})
    @GetMapping("/{viaggioId}")
    public Viaggio findById(@PathVariable UUID viaggioId){
        return this.viaggioService.findById(viaggioId);
    }

    //PUT
    @PutMapping("/{viaggioId}")
    public Viaggio findByIdAndUpdate(@PathVariable UUID viaggioId, @RequestBody @Validated NewViaggioDTO body){
        return this.viaggioService.findByIdAndUpdate(viaggioId,body);
    }

    //DELETE
    @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID viaggioId){
        this.viaggioService.findByIdAndDelete(viaggioId);
    }

    //MODIFICA STATO VIAGGIO
    @PatchMapping("/{viaggioId}")
    public Viaggio editStatus(@PathVariable UUID viaggioId,@RequestBody @Validated NewViaggioStatoDTO body){
        return this.viaggioService.editStatus(viaggioId,body);
    }
}
