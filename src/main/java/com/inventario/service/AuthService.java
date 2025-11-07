package com.inventario.service;
import com.inventario.dto.authResponse.AuthResponseDTO;
import com.inventario.dto.LoginDTO;
import com.inventario.exception.UnauthorizedException;
import com.inventario.mapper.AdministradorMapper;
import com.inventario.model.Administrador;
import com.inventario.repository.AdministradorRepository;
import com.inventario.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final AdministradorRepository administradorRepository;
    private final AdministradorMapper mapper;

    public AuthResponseDTO login(LoginDTO loginDTO) {
        // Buscar administrador por correo y supermercado
        Administrador administrador = administradorRepository.findByCorreoAndPassword(loginDTO.getCorreo(), loginDTO.getPassword())
                .orElseThrow(() -> new UnauthorizedException("Credenciales inválidas"));

        // Verificar que esté activo
        if (!administrador.getActivo()) {
            throw new UnauthorizedException("Cuenta desactivada");
        }

        // Autenticar
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getCorreo(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar token
        String jwt = tokenProvider.generateToken(authentication);

        return AuthResponseDTO.builder()
                .token(jwt)
                .administrador(mapper.toDTO(administrador))
                .build();
    }
}