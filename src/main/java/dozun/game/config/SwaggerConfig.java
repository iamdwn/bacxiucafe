//package dozun.game.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
////@EnableSwagger2
//@EnableWebMvc
//public class SwaggerConfig {
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .globalOperationParameters(getGlobalHeaders())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("dozun.game.controllers"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private List<Parameter> getGlobalHeaders() {
//        List<Parameter> globalParameters = new ArrayList<>();
//        Parameter authHeader = new ParameterBuilder()
//                .name("Authorization")
//                .description("Bearer token")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(true)
//                .build();
//        globalParameters.add(authHeader);
//        return globalParameters;
//    }
//}
//
//
