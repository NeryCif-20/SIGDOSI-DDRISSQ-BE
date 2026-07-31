package com.ddrissq.sigdosi.configuration.jwt;

import com.ddrissq.sigdosi.configuration.security.SecurityProperties;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@EnableConfigurationProperties(value = SecurityProperties.class)
@Configuration
public class JwtConfiguration {

    @Bean
    public JwtDecoder jwtDecoder(SecurityProperties properties) throws IOException {
        Resource publicKey = properties.getAccessToken().getPublicKey();
        return NimbusJwtDecoder.withPublicKey(readPublicKey(publicKey)).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(SecurityProperties properties) throws IOException {
        Resource publicKey = properties.getAccessToken().getPublicKey();
        Resource privateKey = properties.getAccessToken().getPrivateKey();
        RSAKey rsaKey = new RSAKey.Builder(readPublicKey(publicKey))
                .privateKey(readPrivateKey(privateKey))
                .build();
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(rsaKey)));
    }

    @Bean
    public JwtAuthenticationConverter authenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("permissions");
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }

    private RSAPublicKey readPublicKey(Resource publicKey) throws IOException {
        try (InputStream stream = publicKey.getInputStream()) {
            return RsaKeyConverters.x509().convert(stream);
        }
    }

    private RSAPrivateKey readPrivateKey(Resource privateKey) throws IOException {
        try (InputStream stream = privateKey.getInputStream()) {
            return RsaKeyConverters.pkcs8().convert(stream);
        }
    }

}
