package com.example.security.securityconfig;

import com.example.security.jwtconfig.AuthEntryPointJwt;
import com.example.security.jwtconfig.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilita la seguridad a nivel de metodo con anotaciones como @PreAuthorize, etc.
public class SecurityConfiguration {
    //PENULTIMO: SE CONFIGURA LA SEGURIDAD PARA GESTIONAR LA AUTENTICACION Y AUTORIZACIÓN

    @Autowired
    UserDetailsService userDetailsService; // Servicio para cargar los detalles del usuario

    @Autowired
    AuthEntryPointJwt unauthorizedHandler; // Manejador para respuestas no autorizadas

    //URLS que estan permitidas sin autenticacion
    private static final String[] WHITE_LIST_URL = {"/signin", "/signup", "v1/api/usuarios"};

    //Define un Bean para el filtro JWT, que intercepta las peticiones HTTP y verifica la valides del token
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Configura un proveedor de autenticacion que usa UserDetailsService y BCrypt para validar los usuarios y contrenas
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService); //Obtiene el userDetails desde el userDetailsService

        // Usa el codificador de contraseñas basado en BCrypt para validar las contraseñas
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    // Define un bean que usa BCryptPasswordEncoder para codificar las contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //Configura el AuthenticationManager, que maneja el proceso de autenticacion
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authconfig) throws Exception {
        return authconfig.getAuthenticationManager();
    }

    //Define la cedena de filtros de seguridad. Configura como se gestionn las peticiones HTTP en terminos de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //dehabilita la proteccion csrf ya que no es necesaria en una api rest
                .cors(AbstractHttpConfigurer::disable) //deshabilita el manejo de CORS (se puede personalizar)
                //Configura la autorizacion de solicitudes Http
                .authorizeHttpRequests(req -> req
                        .requestMatchers(WHITE_LIST_URL).permitAll() // Permite el acceso sin autenticación a las URLs definidas en WHITE_LIST_URL
                        .anyRequest().authenticated()) // Requiere autenticación para todas las demás solicitudes
                .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler)) // Configura el manejo de excepciones personalizado AuthEntryPoint
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS)) // // Establece la política de manejo de sesiones como "sin estado" (STATLESS), ya que se está utilizando JWT
                .authenticationProvider(authenticationProvider()) //// Establece el proveedor de autenticación personalizado
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // // Añade el filtro de autenticación JWT antes del filtro de autenticación de usuario y contraseña

        return http.build(); // Devuelve la configuración de seguridad construida
    }
}
