package io.github.wyllian.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.wyllian.domain.entity.Usuario;

@Service
public class JwtService {
    
    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario) {
        long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());

        HashMap<String, Object> claims =  new HashMap<>();
        claims.put("emaildousuario", "usuario@gmail.com");
        claims.put("roles", "ADMIN");

        return Jwts.builder()
                    .setSubject(usuario.getLogin())
                    .setExpiration(data)
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                    .compact();
    }
    
    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }
    
    public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime localDateTime = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(localDateTime);
        } catch (Exception e) {
            return false;
        }
    }
    
    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return (String) obterClaims(token).getSubject();
    }
}
