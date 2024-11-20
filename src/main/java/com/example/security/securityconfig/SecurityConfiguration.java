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
import org.springframework.security.config.http.SessionCreationPolicy;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Deshabilita CSRF para una API REST
                .cors().and() // Habilita CORS
                .authorizeHttpRequests()
                .requestMatchers("/login", "/register").permitAll() // Permite el acceso sin autenticación
                .requestMatchers("/v1/polices/**").hasAnyAuthority("ROLE_CLIENT", "ROLE_ADMIN") // Acceso restringido por roles
                .anyRequest().hasAuthority("ROLE_ADMIN") // Otras rutas requieren ROLE_ADMIN
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler) // Manejo de respuestas no autorizadas
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sin estado
                .and()
                .authenticationProvider(authenticationProvider()) // Proveedor de autenticación personalizado
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Filtro JWT

        return http.build();
    }


}
