package elliot.util;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@TestConfiguration
public class RestDocsConfig {
    @Bean
    public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcBuilderCustomizer() {
        return configurer -> {
            configurer.operationPreprocessors()
                    .withRequestDefaults(prettyPrint()) // Request 본문 이쁘게
                    .withResponseDefaults(prettyPrint());
            configurer.uris().withHost("test");// Response 본문 이쁘게
        };
    }
}