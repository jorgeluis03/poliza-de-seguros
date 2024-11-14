package com.example.security.controller;
import com.example.common.response.ApiResult;
import com.example.role.exception.RolNoEncontradoException;
import com.example.user.dto.UsuarioDTO;
import com.example.user.repository.UsuarioRepository;
import com.example.security.jwtconfig.JwtUtils;
import com.example.security.request.LoginRequest;
import com.example.security.response.JwtResponse;
import com.example.security.userservice.UserDetailsImpl;
import com.example.user.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin
public class JwtAuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UsuarioDTO signUpRequest, HttpServletRequest request) throws UnsupportedEncodingException, RolNoEncontradoException {
        //verifica si ya existe un usurio con el mismo username
        if(userRepository.existsByNombreUsuario(signUpRequest.getNombreUsuario())){
            return ResponseEntity.badRequest().body("Error: Username ya existe");
        }

        ApiResult<UsuarioDTO> apiResult = usuarioService.crearUsuario(signUpRequest);
        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/login")
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

}
