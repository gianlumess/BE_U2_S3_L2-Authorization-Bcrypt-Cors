package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity //per dichiarare regole di autorizzazione sui singoli endpoint
public class Config {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //disabilito dei comportamenti di default
        httpSecurity.formLogin(http->http.disable()); //tolgo il form di login perché questo andrà fatto con React
        httpSecurity.csrf(http->http.disable()); //tolgo la protezione da attacchi CSRF, non necessaria
        httpSecurity.sessionManagement(http->http.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //tolgo le sessioni perché con JWT non si utilizzano
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/**").permitAll()); //evito di ricevere 401 su ogni richiesta
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder getBCrypt(){
        return new BCryptPasswordEncoder(11);
    }
}
