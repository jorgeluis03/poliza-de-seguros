package com.example.security.jwtconfig;
import com.example.security.userservice.UserDetailsImpl;
import com.example.security.userservice.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

// El filtro de autenticación se ejecuta una vez por cada solicitud HTTP
public class AuthTokenFilter extends OncePerRequestFilter {
    //4: INTERCEPTA LAS SOLICITUDES PARA VALIDAR EL TOKEN JWT Y AUTENTICAR AL USUARIO ANTES DE QUE SERVICIO PROCESE LA SOLICITUD

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    // Logger para registrar información y errores durante el proceso de autenticación
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    //Es el punto principal de la intercepcion de la solicitud
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            //Extraer el token de la cabecera de la solicitud
            String jwt = parseJwt(request);

            //Validar el token jwt
            if(jwt != null && jwtUtils.validateJwtToken(jwt)){
                //si el token es valido, obtener el nombre de usuario desde el token
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                //cargar los detalles del usuario (roles, permisos) desde la base de datos
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                //crea un objeto de autenticacion para el usuario basado en sus detalles
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                //asocia los detalles de la solicitud actual (IP, sesion) con la autenticacion
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecer la autenticación en el contexto de seguridad para la sesión actual
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }catch (Exception e){
            // Capturar cualquier excepción durante el proceso de autenticación y registrar un error
            logger.error("Cannot set user authentication: {}", e);
        }

        // Continuar con el filtro siguiente en la cadena de filtros
        filterChain.doFilter(request,response);
    }

    //Metodo privado para extraer el token JWT desde la cabecera "Authorization" de la solicitud HTTP
    private String parseJwt (HttpServletRequest request){
        //Obtener la cabecera "Authorization" de la solicitud
        String headerAuth = request.getHeader("Authorization");

        //Verificar que la cabecera no este vacia y comience con "Bearer "
        if (headerAuth != null && headerAuth.startsWith("Bearer ")){
            //Retorna el token JWT sin el prefijo "Bearer "
            return headerAuth.substring(7,headerAuth.length());
        }
        // Si no se encuentra un token válido, retornar null
        return null;
    }


}
