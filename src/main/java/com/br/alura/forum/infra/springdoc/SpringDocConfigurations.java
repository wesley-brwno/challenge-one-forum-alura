package com.br.alura.forum.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Forum Alura API")
                        .description("""
                                O fórum da Alura é uma API de discussão online onde usuáros podem compartilhar dúvidas, respostas e experiências.<br><br>
                                Este sistema oferece dois perfis de usuário: ADMIN e USER.<br>
                                O perfil ADMIN tem acesso a todas as funcionalidades do sistema, enquanto o perfil USER tem acesso limitado.<br><br>
                                Para fazer login como administrador, use as seguintes credenciais:<br>
                                <ul>
                                    <li>email: admin@email.com</li>
                                    <li>senha: admin1234</li>
                                </ul>
                                Para fazer login como um usuário, use as seguintes credenciais:<br>
                                <ul>
                                    <li>email: user@email.com</li>
                                    <li>senha: user1234</li>
                                </ul>
                                """)
                );
    }
}
