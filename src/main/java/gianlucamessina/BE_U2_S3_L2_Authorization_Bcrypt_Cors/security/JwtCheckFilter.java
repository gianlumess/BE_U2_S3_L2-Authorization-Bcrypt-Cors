package gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.security;

import gianlucamessina.BE_U2_S3_L2_Authorization_Bcrypt_Cors.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtCheckFilter extends OncePerRequestFilter {
    @Autowired
    JWTTools jwtTools;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //metodo che varrà richiamato ad ogni richiesta essendo un filtro(tranne quelle che si vogliono escludere)

        //1. prima di tutto verifico che nella richiesta ci sia l'Authorization Header, altrimenti lancia 401
        String authHeader=request.getHeader("Authorization");
        // "Authorization": "Bearer eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MjY0ODE1MDMsImV4cCI6MTcyNzA4NjMwMywic3ViIjoiOTFkMTg2MGItZjE2Yy00MTYwLWIyYTYtODU2NWY0MzY5MTBiIn0.l58gBS6yJnRom0gYNRECl3W_e1B0TmdNkOivPncYP0fO3LIC2QXwvgft71jNYhfJ"
        if(authHeader==null || !authHeader.startsWith("Bearer ")) throw new UnauthorizedException("Inserisci correttamente il token");

        //2. se l'Authorization Header è presente, estraggo il token
        String token=authHeader.substring(7);
        System.out.println("TOKEN : "+ token);

        //3. verifico se il token è stato manipolato
        jwtTools.verifyToken(token);

        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        //serve per disabilitare questo filtro per alcune richieste, su determinati endpoint o controller,
        //in questo caso il filtro non dovrà essere chiamato per tutte le richieste di login e register
        return new AntPathMatcher().match("/auth/**",request.getServletPath());
    }
}
