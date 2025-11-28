package com.inventario.security;
import com.inventario.model.Administrador;
import com.inventario.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdministradorRepository administradorRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws
            UsernameNotFoundException {
        Administrador administrador = administradorRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        return UserPrincipal.create(administrador);
    }

    public UserDetails loadUserById(Long id) {
        Administrador administrador = administradorRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + id));

        return UserPrincipal.create(administrador);
    }
}