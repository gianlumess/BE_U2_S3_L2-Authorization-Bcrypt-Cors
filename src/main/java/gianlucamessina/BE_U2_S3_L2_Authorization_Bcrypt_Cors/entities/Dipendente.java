package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "dipendenti")
@JsonIgnoreProperties({"password", "role", "authorities", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})
public class Dipendente implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    @Column(name = "foto_profilo")
    private String fotoProfilo;
    @Enumerated(EnumType.STRING)
    private Role role;

    public Dipendente(String username, String nome, String cognome, String email,String password, String fotoProfilo) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password=password;
        this.fotoProfilo = fotoProfilo;
        this.role=Role.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //deve restituire una lista di ruoli dell'utente
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }



}
