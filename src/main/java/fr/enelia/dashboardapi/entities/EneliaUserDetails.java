package fr.enelia.dashboardapi.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EneliaUserDetails implements UserDetails {

    private Utilisateur utilisateur;
    private Collection<? extends GrantedAuthority> authorities;

    public EneliaUserDetails(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        for (Role role : this.utilisateur.getRoles()) {
            auth.add(new SimpleGrantedAuthority(role.getNom()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return utilisateur.getPassword();
    }

    @Override
    public String getUsername() {
        return utilisateur.getUsername();
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
        return true;
    }
}
