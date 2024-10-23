package com.example.security.controller;

import com.example.dto.UsuarioDTO;
import com.example.entity.Usuario;
import com.example.repository.UsuarioRepository;
import com.example.security.jwtconfig.JwtUtils;
import com.example.security.request.LoginRequest;
import com.example.security.response.JwtResponse;
import com.example.security.userservice.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class JwtAuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UsuarioRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UsuarioDTO signUpRequest, HttpServletRequest request) throws UnsupportedEncodingException {
        //verifica si ya existe un usurio con el mismo username
        if(userRepository.existsByNombreUsuario(signUpRequest.getNombreUsuario())){
            // Si el nombre de usuario ya está tomado, devuelve un mensaje de error
            return ResponseEntity.badRequest().body("Error: Username ya existe");
        }

        //crea un objeto Usuario con la info recibida en la solicitud
        Usuario user = new Usuario();
        user.setNombreUsuario(signUpRequest.getNombreUsuario());
        user.setContrasena(encoder.encode(signUpRequest.getContrasena()));
        user.setCorreo(signUpRequest.getCorreo());

        // Guarda el usuario en la base de datos
        userRepository.save(user);

        // Devuelve una respuesta exitosa con el objeto Usuario registrado
        return ResponseEntity.ok(user);

    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser (@RequestBody LoginRequest loginRequest) throws UnsupportedEncodingException {
        //autentica al usuario usando en username y contrasena proporcionada
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        //Establece la autenticacion en el contexto de seguridad de la app
        SecurityContextHolder .getContext().setAuthentication(authentication);
        //Obtiene los detalles de usuario autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Genera un token JWT para el usuario autenticado
        String tokenJwt = jwtUtils.generateJwtToken(authentication);

        // Devuelve una respuesta exitosa con el token JWT y el nombre de usuario
        return ResponseEntity.ok(new JwtResponse(tokenJwt, userDetails.getUsername()));

    }

    @RequestMapping("/user-dashboard")
    @PreAuthorize("isAuthenticated()")
    public String dashboard() {
        return "My Dashboard";
    }

}
