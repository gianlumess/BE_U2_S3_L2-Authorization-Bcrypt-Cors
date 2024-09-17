package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.services;

import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.entities.Viaggio;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.enums.StatoViaggio;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.exceptions.BadRequestException;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.exceptions.NotFoundException;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads.NewViaggioDTO;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads.NewViaggioStatoDTO;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ViaggioService {
    @Autowired
    private ViaggiRepository viaggiRepository;

    //FIND ALL CON PAGINAZIONE
    public Page<Viaggio> findAll(int page,int size,String sortBy){
        //controllo se viene richiesta una pagina superiore a quelle presenti effettivamente
        if(page>150)page=150;

        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy));

        return this.viaggiRepository.findAll(pageable);
    }

    //SAVE DI UN VIAGGIO
    public Viaggio save(NewViaggioDTO body){
        //Controlla che la data inserita non sia già passata
        if(body.data().isBefore(LocalDate.now())){
            throw  new BadRequestException("Non è possibile creare un viaggio per una data già passata!");
        }
        Viaggio viaggio=new Viaggio(body.destinazione(), body.data(), StatoViaggio.IN_PROGRAMMA);
        return this.viaggiRepository.save(viaggio);
    }

    //FIND BY ID
    public Viaggio findById(UUID viaggioId){
        return this.viaggiRepository.findById(viaggioId).orElseThrow(()->new NotFoundException(viaggioId));
    }

    //FIND BY AND UPDATE
    public Viaggio findByIdAndUpdate(UUID viaggioId,NewViaggioDTO body){
        Viaggio found=this.findById(viaggioId);

        if(body.data().isBefore(LocalDate.now())){
            throw  new BadRequestException("Non è possibile creare un viaggio per una data già passata!");
        }
            found.setDestinazione(body.destinazione());
            found.setData(body.data());

            return this.viaggiRepository.save(found);
    }

    //FIND BY ID AND DELETE
    public void findByIdAndDelete(UUID viaggioId){
        Viaggio found=this.findById(viaggioId);
        this.viaggiRepository.delete(found);
    }

    //MODIFICA STATO VIAGGIO
    public Viaggio editStatus(UUID viaggioId, NewViaggioStatoDTO body){
        Viaggio found=this.findById(viaggioId);

        found.setStatoViaggio(body.statoViaggio());
        return this.viaggiRepository.save(found);
    }
}
