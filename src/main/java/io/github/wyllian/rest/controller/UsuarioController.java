package io.github.wyllian.rest.controller;

import io.github.wyllian.exception.SenhaInvalidaException;
import io.github.wyllian.rest.dto.CredenciaisDTO;
import io.github.wyllian.rest.dto.TokenDTO;
import io.github.wyllian.security.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.wyllian.domain.entity.Usuario;
import io.github.wyllian.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioServiceImpl usuarioServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody Usuario usuario) {
        String senhaEncode = this.passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaEncode);
        return this.usuarioServiceImpl.salvar(usuario);
    }
    
    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais) {
        try {

            Usuario usuario = Usuario
                .builder()
                .login(credenciais.getLogin())
                .senha(credenciais.getSenha())
                .build();

            UserDetails autenticar = usuarioServiceImpl.autenticar(
                usuario
            );

            String token = jwtService.gerarToken(usuario);

            return new TokenDTO(usuario.getLogin(), token);

        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());            
        } 
    }
}
