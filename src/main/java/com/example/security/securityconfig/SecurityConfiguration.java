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
@EnableMethodSecurity
public class SecurityConfiguration {
    //PENULTIMO: SE CONFIGURA LA SEGURIDAD PARA GESTIONAR LA AUTENTICACION Y AUTORIZACIÓN

    @Autowired
    UserDetailsService userDetailsService; // Servicio para cargar los detalles del usuario

    @Autowired
    AuthEntryPointJwt unauthorizedHandler; // Manejador para respuestas no autorizadas

    //URLS que estan permitidas sin autenticacion
    private static final String[] WHITE_LIST_URL = {"/signin", "/signup"};

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
                .csrf(AbstractHttpConfigurer::disable) // Deshabilita CSRF para una API REST
                .cors(AbstractHttpConfigurer::disable) // Deshabilita CORS (puedes personalizarlo)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/signin", "/signup").permitAll() // Permite el acceso sin autenticación
                        .requestMatchers("/v1/api/usuarios").hasAuthority("ROLE_USER") // Solo ROLE_USER puede acceder a /usuarios
                        .requestMatchers("/**").hasAuthority("ROLE_ADMIN") // Solo ROLE_ADMIN puede acceder a /polizas
                        .anyRequest().authenticated()) // Cualquier otra ruta requiere autenticación
                .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler)) // Maneja las respuestas no autorizadas
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS)) // Sin estado, ya que se utiliza JWT
                .authenticationProvider(authenticationProvider()) // Proveedor de autenticación personalizado
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Añade el filtro JWT antes de UsernamePasswordAuthentication

        return http.build();
    }
}
