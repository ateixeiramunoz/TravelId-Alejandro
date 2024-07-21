package com.eoi.grupo5.modelos;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombreUsuario", nullable = false, length = 45)
    private String nombreUsuario;

    @Column(name = "password", nullable = false, length = 150)
    private String password;

    @OneToOne(mappedBy = "usu",cascade = CascadeType.ALL)
    private DetallesUsuario detalles;

    @OneToMany(mappedBy = "usu", fetch = FetchType.EAGER)
    private Set<Reserva> reservas = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "rolesUsuario",
            joinColumns = @JoinColumn(name = "usuarioId"),
            inverseJoinColumns = @JoinColumn(name = "rolId")
    )
    private Set<Role> roles = new HashSet<>();

    @Basic(optional = false)
    private boolean active = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName().toUpperCase());
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    public Usuario(String nombreUsuario, String password, DetallesUsuario detalles) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.detalles = detalles;
    }
}
