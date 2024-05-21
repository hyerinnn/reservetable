package my.reservetable.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearer-key",
                        new io.swagger.v3.oas.models.security.SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))

                .info(apiInfo());
    }

    private Info apiInfo(){
        return new Info()
                .title("Reserve Table Swagger")
                .description("식당 예약 프로젝트용 스웨거")
                .version("1.0.0");
    }
}
