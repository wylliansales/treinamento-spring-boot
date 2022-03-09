package io.github.wyllian.service.impl;

import javax.transaction.Transactional;

import io.github.wyllian.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.wyllian.domain.entity.Usuario;
import io.github.wyllian.domain.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }
    
    public UserDetails autenticar( Usuario usuario) {
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean matches = encoder.matches(usuario.getSenha(), user.getPassword());
        if (matches) {
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  
        Usuario usuario = this.usuarioRepository.findByLogin(username)
                              .orElseThrow(() -> new UsernameNotFoundException("Usuário não contrato"));

        String[] roles = usuario.isAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"};
                                
        return User.builder()
                   .username(usuario.getLogin())
                   .password(usuario.getSenha())
                   .roles(roles)
                   .build();
    }
}
