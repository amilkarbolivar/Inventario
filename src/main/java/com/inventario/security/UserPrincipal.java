package com.inventario.security;
import com.inventario.model.Administrador;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
public class UserPrincipal implements UserDetails {
    private Long id;
    private String nombre;
    private String correo;
    private String password;
    private String rol;
    private Boolean activo;
    private Long supermercadoId;

    public UserPrincipal(Long id, String nombre, String correo, String password, String rol, Boolean activo, Long supermercadoId) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
        this.activo = activo;
        this.supermercadoId = supermercadoId;
    }

    public static UserPrincipal create(Administrador administrador) {
        return new UserPrincipal(
                administrador.getId(),
                administrador.getNombre(),
                administrador.getCorreo(),
                administrador.getPassword(),
                administrador.getRol(),
                administrador.getActivo(),
                administrador.getSupermercado().getId()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority( rol));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }

    // Getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getRol() { return rol; }
    public Long getSupermercadoId() { return supermercadoId; }
}