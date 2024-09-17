package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.services;

import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.entities.Dipendente;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.entities.Prenotazione;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.entities.Viaggio;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.exceptions.BadRequestException;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.exceptions.NotFoundException;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads.NewPrenotazioneDTO;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.payloads.PrenotazioneRespDTO;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.repositories.PrenotazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioniRepository prenotazioniRepository;
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private ViaggioService viaggioService;

    //FIND ALL CON PAGINAZIONE
    public Page<Prenotazione> findAll(int page, int size, String sortBy){
        //controllo se viene richiesta una pagina superiore a quelle presenti effettivamente
        if(page>150)page=150;

        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy));

        return this.prenotazioniRepository.findAll(pageable);
    }

    //SAVE DI UNA PRENOTAZIONE
    public PrenotazioneRespDTO save(NewPrenotazioneDTO body){
        //controllo che dipendente e viaggio inseriti esistano
        Dipendente foundDipendente=this.dipendenteService.findById(UUID.fromString(body.dipendenteId()));
        Viaggio foundViaggio=this.viaggioService.findById(UUID.fromString(body.viaggioId()));

        //controllo se il dipendente ha già prenotato un viaggio per stessa data
        if(this.prenotazioniRepository.findByDipendenteAndViaggioData(foundDipendente,foundViaggio.getData()).isPresent()){
            throw new BadRequestException("Il dipendente selezionato ha già prenotato un viaggio per la data selezionata!");
        }

        Prenotazione prenotazione= new Prenotazione(LocalDate.now(),body.volo(),body.alloggio(),foundDipendente,foundViaggio);
        this.prenotazioniRepository.save(prenotazione);
        PrenotazioneRespDTO resp=new PrenotazioneRespDTO(prenotazione.getId(), prenotazione.getDataRichiesta(), body.volo(), body.alloggio(), body.dipendenteId(), body.viaggioId());
        return resp;
    }

    //FIND BY ID
    public Prenotazione findById(UUID prenotazioneId){
        return this.prenotazioniRepository.findById(prenotazioneId).orElseThrow(()->new NotFoundException(prenotazioneId));
    }
}
