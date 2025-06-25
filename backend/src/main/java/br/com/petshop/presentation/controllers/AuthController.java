package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.AuthRequestDTO;
import br.com.petshop.application.dto.AuthResponseDTO;
import br.com.petshop.application.dto.UsuarioDTO;
import br.com.petshop.domain.entities.Perfil;
import br.com.petshop.domain.entities.Usuario;
import br.com.petshop.domain.repositories.PerfilRepository;
import br.com.petshop.domain.repositories.UsuarioRepository;
import br.com.petshop.infrastructure.security.JwtTokenProvider;
import br.com.petshop.infrastructure.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new AuthResponseDTO(
                jwt,
                userDetails.getUsername(),
                userDetails.getNome(),
                roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UsuarioDTO signUpRequest) {
        if (usuarioRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Erro: Username já está em uso!");
        }

        if (usuarioRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Erro: Email já está em uso!");
        }

        // Criar nova conta de usuário
        Usuario usuario = new Usuario();
        usuario.setUsername(signUpRequest.getUsername());
        usuario.setNome(signUpRequest.getNome());
        usuario.setEmail(signUpRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(signUpRequest.getUsername())); // Senha padrão é o próprio username

        Set<String> strRoles = signUpRequest.getPerfis();
        Set<Perfil> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Perfil userRole = perfilRepository.findByNome("USER")
                    .orElseThrow(() -> new RuntimeException("Erro: Perfil USER não encontrado."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Perfil userRole = perfilRepository.findByNome(role)
                        .orElseThrow(() -> new RuntimeException("Erro: Perfil " + role + " não encontrado."));
                roles.add(userRole);
            });
        }

        usuario.setPerfis(roles);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }
}
